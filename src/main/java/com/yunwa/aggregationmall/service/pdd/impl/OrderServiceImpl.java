package com.yunwa.aggregationmall.service.pdd.impl;

import com.yunwa.aggregationmall.dao.pdd.NotCpsOrderMapper;
import com.yunwa.aggregationmall.dao.pdd.PddOrderMapper;
import com.yunwa.aggregationmall.pojo.pdd.dto.OrderDto;
import com.yunwa.aggregationmall.pojo.pdd.po.PddOrder;
import com.yunwa.aggregationmall.provider.pdd.OrderAPI;
import com.yunwa.aggregationmall.service.pdd.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public HashMap<String, Object> orderBind(String order_sn, Long user_id) {
        HashMap<String, Object> map = new HashMap<>();
        //在not_cps_order表中查询此订单
        if (notCpsOrderMapper.select(order_sn) > 0){
            //记录数大于0说明为非Cps订单
            map.put("status", "notCpsOrder");
        }else {
            //在订单表查询有无此订单信息
            PddOrder order = pddOrderMapper.selectByOrderSn(order_sn);
            if(order != null) {
                //不为空说明已绑定
                map.put("status", "exist");
            }else {
                //调用pddAPI查询此订单信息
                OrderDto orderDto = orderAPI.getOrderDetail(order_sn);
                if (orderDto == null){
                    //为空说明为非CPS订单，那么存入not_cps_order表
                    notCpsOrderMapper.insertOrderSn(order_sn);
                    map.put("status", "notCpsOrder");
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
                    map.put("status", "cpsOrder");
                }
            }
        }
        return map;
    }

    /**
     * 用户提现
     * @param user_id   用户id
     * @return  返佣金额
     */
    @Override
    public Long getMoney(Long user_id) {
        //获该用户所有的订单号
        List<String> list = pddOrderMapper.getAllOrderSn(user_id);
        //遍历订单号，查询订单详情，更新订单表
        for (String order_sn : list){
            OrderDto orderDto = orderAPI.getOrderDetail(order_sn);
            //封装成订单对象
            PddOrder pddOrder = new PddOrder();
            BeanUtils.copyProperties(orderDto, pddOrder);
            //更新订单表
            pddOrderMapper.updateOrder(pddOrder);
        }
        //查询出该用户订单状态为2的订单的real_promotion_amount之和(单位 元)
        Long totalPromotionAmount = pddOrderMapper.getTotalPromotionAmount(user_id) * 100;
        //将该用户订单状态为2的订单插入到finish_order表
        pddOrderMapper.moveToFinishedOrder(user_id);
        //删除原表中的数据
        pddOrderMapper.deleteFinishedOrder(user_id);
        return totalPromotionAmount;
    }
}
