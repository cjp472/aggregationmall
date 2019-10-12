package com.yunwa.aggregationmall.service.pdd.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunwa.aggregationmall.dao.pdd.NotCpsOrderMapper;
import com.yunwa.aggregationmall.dao.pdd.PddOrderMapper;
import com.yunwa.aggregationmall.pojo.pdd.dto.OrderDto;
import com.yunwa.aggregationmall.pojo.pdd.po.PddOrder;
import com.yunwa.aggregationmall.pojo.pdd.vo.PddPromotionAmountVO;
import com.yunwa.aggregationmall.provider.pdd.OrderAPI;
import com.yunwa.aggregationmall.service.pdd.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private PddOrderMapper pddOrderMapper;

    @Autowired
    private OrderAPI orderAPI;

    @Autowired
    private NotCpsOrderMapper notCpsOrderMapper;

    /**
     * 订单绑定
     * @param order_sn  订单号
     * @param user_id   用户id
     * @return
     */
    @Override
    public String orderBind(String order_sn, String user_id) {
        HashMap<String, Object> map = new HashMap<>();
        //在not_cps_order表中查询此订单
        if (notCpsOrderMapper.select(order_sn) > 0){
            //记录数大于0说明为非Cps订单
            map.put("status", "failed");
        }else {
            //在订单表查询有无此订单信息
            PddOrder order = pddOrderMapper.selectByOrderSn(order_sn);
            if(order != null) {
                //不为空说明已绑定
                map.put("status", "success");
            }else {
                //为空就判断订单号是属于哪一个平台
                if (order_sn.indexOf("-") != -1){
                    //说明是拼多多的订单号，那么调用pddAPI查询此订单信息
                    OrderDto orderDto = orderAPI.getOrderDetail(order_sn);
                    if (orderDto == null){
                        //为空说明为非CPS订单，那么存入not_cps_order表
                        notCpsOrderMapper.insertOrderSn(order_sn);
                        map.put("status", "failed");
                    }else {
                        //不为空说明是CPS订单，将Dto封装成po对象，然后存入pdd_order表
                        PddOrder pddOrder = new PddOrder();
                        BeanUtils.copyProperties(orderDto, pddOrder);
                        //获得总佣金
                        Long promotion_amount = pddOrder.getPromotion_amount();
                        //设置用户的佣金
                        pddOrder.setReal_promotion_amount(Math.round(promotion_amount * 0.5));
                        //设置用户id
                        pddOrder.setUser_id(user_id);
                        pddOrderMapper.insertOrderData(pddOrder);
                        map.put("status", "success");
                    }
                }else if (order_sn.length() == 18){
                    //说明是淘宝的订单号，那么调用淘宝API查询此订单信息

                }else {
                    //说明是京东的订单号，那么调用京东API查询此订单信息

                }

            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * 用户提现
     * @param user_id   用户id
     * @return  返佣对象
     */
    @Override
    public String getMoney(String user_id) {
        HashMap<String, Object> map = new HashMap<>();
        //获该用户所有的拼多多订单号
        List<String> list = pddOrderMapper.getAllOrderSn(user_id);
        //遍历订单号，查询订单详情，更新订单表
        PddPromotionAmountVO pddPromotionAmount = new PddPromotionAmountVO();
        if (list != null){
            OrderDto orderDto = null;
            for (String order_sn : list){
                try {
                    orderDto = orderAPI.getOrderDetail(order_sn);
                }catch (Exception e){
                    e.printStackTrace();
                    map.put("msg", "500");
                }

                //封装成订单对象
                PddOrder pddOrder = new PddOrder();
                BeanUtils.copyProperties(orderDto, pddOrder);
                //更新订单表
                pddOrderMapper.updateOrder(pddOrder);
            }
            //查询拼多多预计返佣及进行中的订单数封装到VO对象
            pddPromotionAmount = pddOrderMapper.selectPddPromotionAmount(user_id);
            //查询出该用户订单状态为3且返佣状态为1的订单的real_promotion_amount之和(单位 元)-》这是提现中佣金
            Long totalRealPromotionAmount = pddOrderMapper.getTotalRealPromotionAmount(user_id);
            //设置本次返佣
            if (totalRealPromotionAmount == null){
                pddPromotionAmount.setReal_promotion(0L);
            }else {
                pddPromotionAmount.setReal_promotion(totalRealPromotionAmount);
            }
            //查询并设置被冻结佣金
            Long frozenPromotion = pddOrderMapper.getFrozenPromotion(user_id);
            pddPromotionAmount.setFrozen_promotion(frozenPromotion);
            //计算并设置剩余返佣
            Long surplusPromotion = pddPromotionAmount.getPredict_promotion() - pddPromotionAmount.getReal_promotion() - pddPromotionAmount.getFrozen_promotion();
            pddPromotionAmount.setSurplus_promotion(surplusPromotion);
            //将改用户订单状态为3的订单的promotion_status字段状态改为0 ->表示已经返佣
            pddOrderMapper.changePromotionStatus(user_id);
            //将该用户订单状态为2的订单插入到finish_order表
            //pddOrderMapper.moveToFinishedOrder(user_id);
            //删除原表中的数据
            //pddOrderMapper.deleteFinishedOrder(user_id);
            /*if (totalRealPromotionAmount == null){
                return BigDecimal.valueOf(0);
            }*/

            //获该用户所有的淘宝订单号
            //操作。。。

            //获该用户所有的京东订单号
            //操作。。。

            map.put("data", pddPromotionAmount);
            map.put("msg", "200");
        }
        //return JSONObject.toJSONString(pddPromotionAmount);
        map.put("data", pddPromotionAmount);
        map.put("msg", "415");
        return JSON.toJSONString(map);
    }
}
