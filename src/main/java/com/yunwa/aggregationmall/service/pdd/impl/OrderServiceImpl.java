package com.yunwa.aggregationmall.service.pdd.impl;

import com.yunwa.aggregationmall.common.RespBean;
import com.yunwa.aggregationmall.dao.pdd.NotCpsOrderMapper;
import com.yunwa.aggregationmall.dao.pdd.PddOrderMapper;
import com.yunwa.aggregationmall.dao.pdd.PddPromotionRateMapper;
import com.yunwa.aggregationmall.pojo.CommissionDTO;
import com.yunwa.aggregationmall.pojo.pdd.dto.OrderDto;
import com.yunwa.aggregationmall.pojo.pdd.po.PddOrder;
import com.yunwa.aggregationmall.provider.pdd.OrderAPI;
import com.yunwa.aggregationmall.service.pdd.OrderService;
import com.yunwa.aggregationmall.service.tb.TbOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private PddOrderMapper pddOrderMapper;
    @Autowired
    private OrderAPI orderAPI;
    @Autowired
    private NotCpsOrderMapper notCpsOrderMapper;
    @Autowired
    private PddPromotionRateMapper pddPromotionRateMapper;
    @Autowired
    private TbOrderService tbOrderService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 订单绑定
     * @param order_sn  订单号
     * @param user_id   用户id
     * @return
     */
    @Override
    public RespBean orderBind(String order_sn, String user_id) {
        //在订单表查询有无此订单信息
        PddOrder order = pddOrderMapper.selectByOrderSn(order_sn);
        if(order != null) {     //不为空说明已绑定
            return RespBean.ok("订单绑定成功！");
        }else {
            //调用接口查询订单信息
            OrderDto orderDto = orderAPI.getOrderDetail(order_sn);
            if (orderDto == null){
                //为空说明为非CPS订单，那么存入not_cps_order表
                notCpsOrderMapper.insertOrderSn(order_sn);
                return RespBean.error("订单绑定失败！无此订单信息。");
            }else {     //不为空说明是CPS订单，将Dto封装成po对象，然后存入pdd_order表
                PddOrder pddOrder = new PddOrder();
                BeanUtils.copyProperties(orderDto, pddOrder);
                //获得总佣金
                Long promotion_amount = pddOrder.getPromotion_amount();
                //查询出佣金比例
                Double rate = pddPromotionRateMapper.selectLastRate();
                //设置用户的佣金
                pddOrder.setReal_promotion_amount(Math.round(promotion_amount * rate));
                //设置用户id
                pddOrder.setUser_id(user_id);
                pddOrderMapper.insertOrderData(pddOrder);
                return RespBean.ok("订单绑定成功！");
            }
        }
    }

    /**
     * 用户提现
     * @param user_id   用户id
     * @return  返佣对象
     */
    @Override
    public CommissionDTO getMoney(String user_id) {
        //获该用户所有的拼多多订单号
        List<String> list = pddOrderMapper.getAllOrderSn(user_id);
        //遍历订单号，查询订单详情，更新订单表
        //PddPromotionAmountVO pddPromotionAmount;

        if (!CollectionUtils.isEmpty(list)){
            OrderDto orderDto = null;
            CommissionDTO commissionDTO = null;
            for (String order_sn : list){
                try {
                    orderDto = orderAPI.getOrderDetail(order_sn);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info("获取订单信息失败，订单号为：{}", order_sn);
                    continue;
                }
                //获取订单的状态
                Integer order_status = orderDto.getOrder_status();
                if(order_status == 4 || order_status == 8 || order_status == 10){
                    //删除这几个状态的订单
                    pddOrderMapper.deleteByOrderSn(order_sn);
                } else {
                    //封装成订单对象
                    PddOrder pddOrder = new PddOrder();
                    BeanUtils.copyProperties(orderDto, pddOrder);
                    //更新订单表
                    pddOrderMapper.updateOrder(pddOrder);
                }
            }
            //查询拼多多预计返佣及进行中的订单数封装到VO对象
            commissionDTO = pddOrderMapper.selectPddPromotionAmount(user_id);
            //将单位总佣金的单位转换为元
            Double totalPromotion = commissionDTO.getTotalPromotion() * 0.01;
            commissionDTO.setTotalPromotion(totalPromotion);
            //查询出该用户订单状态为5且返佣状态为1的订单的real_promotion_amount之和(单位 元)-》这是提现中佣金
            Long realPromotion = pddOrderMapper.getTotalRealPromotionAmount(user_id) == null ? 0L : pddOrderMapper.getTotalRealPromotionAmount(user_id);
            //转换单位
            Double _realPromotion = realPromotion * 0.01;
            //设置本次返佣
            commissionDTO.setRealPromotion(_realPromotion);
            /*if (realPromotion == null){
                commissionDTO.setRealPromotion(0d);
                //pddPromotionAmount.setReal_promotion(0L);
            }else {
                commissionDTO.setRealPromotion(Double.valueOf(realPromotion));
                //pddPromotionAmount.setReal_promotion(totalRealPromotionAmount);
            }*/
            //查询并设置被冻结佣金
            Long frozenPromotion = pddOrderMapper.getFrozenPromotion(user_id) == null ? 0L : pddOrderMapper.getFrozenPromotion(user_id);
            //转换单位
            Double _frozenPromotion = frozenPromotion * 0.01;
            commissionDTO.setFrozenPromotion(_frozenPromotion);
            //设置未收货佣金
            Double surplusPromotion = totalPromotion - _realPromotion - _frozenPromotion;
            commissionDTO.setSurplusPromotion(surplusPromotion);
            /*if (frozenPromotion == null){
                commissionDTO.setFrozenPromotion(0d);
                //pddPromotionAmount.setFrozen_promotion(0L);
            }else {
                commissionDTO.setFrozenPromotion(Double.valueOf(frozenPromotion));
                //pddPromotionAmount.setFrozen_promotion(frozenPromotion);
            }*/
            return commissionDTO;
        }

        return null;
    }

    /**
     * 该用户删除已返现订单
     * @param user_id   用户id
     * @return  boolean
     */
    private void delOrder(String user_id) {
        //在订单表中删除该用户已返现的订单
        pddOrderMapper.deleteFinishedOrder(user_id);
    }
}
