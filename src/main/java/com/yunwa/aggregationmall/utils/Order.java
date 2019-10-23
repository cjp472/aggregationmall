package com.yunwa.aggregationmall.utils;

import com.yunwa.aggregationmall.common.RespBean;
import com.yunwa.aggregationmall.dao.pdd.NotCpsOrderMapper;
import com.yunwa.aggregationmall.pojo.CommissionDTO;
import com.yunwa.aggregationmall.service.pdd.OrderService;
import com.yunwa.aggregationmall.service.tb.TbOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created on 2019/10/23.
 * @author yueyang
 */
@Component
public class Order {
    @Autowired
    private NotCpsOrderMapper notCpsOrderMapper;
    @Autowired
    private TbOrderService tbOrderService;
    @Autowired
    private OrderService orderService;

    /**
     * 订单绑定
     * @param tradeId 订单号
     * @param userId 用户id
     * @return
     */
    public RespBean orderBind(String tradeId, String userId){
        //先在not_cps_order表中查询此订单
        if (notCpsOrderMapper.select(tradeId) > 0){
            //记录数大于0说明为非Cps订单
            return RespBean.error("订单绑定失败！无此订单信息。");
        }else {
            //为空就判断订单号是属于哪一个平台
            if (tradeId.indexOf("-") != -1){    //拼多多
                return orderService.orderBind(tradeId, userId);
            }else if (tradeId.length() == 18){  //淘宝
                return tbOrderService.tbOrderBind(tradeId, userId);
            }else {     //京东
                //说明是京东的订单号，那么调用京东API查询此订单信息
                return null;
            }
        }
    }

    /**
     * 提现
     * @param userId 用户id
     * @return
     */
    public RespBean getMoney(String userId) {
        Integer orderCount = 0;          //订单数
        Double totalPromotion = 0.0;       //可提现佣金（总金额）
        Double realPromotion = 0.0;        //提现中佣金（本次返现金额，审核通过）
        Double surplusPromotion = 0.0;     //未收货佣金
        Double frozenPromotion = 0.0;      //被冻结佣金（已确认收货，审核中）

        ArrayList<CommissionDTO> list = new ArrayList<>();
        //淘宝的佣金
        CommissionDTO c1 = tbOrderService.tbGetMoney(userId);
        if (c1 != null){
            list.add(c1);
        }
        //拼多多的佣金
        c1 = orderService.getMoney(userId);
        if (c1 != null){
            list.add(c1);
        }
        //京东的佣金
        //。。。

        for (CommissionDTO commissionDTO : list){
            orderCount += commissionDTO.getOrderCount();
            totalPromotion += commissionDTO.getTotalPromotion();
            realPromotion += commissionDTO.getRealPromotion();
            surplusPromotion += commissionDTO.getSurplusPromotion();
            frozenPromotion += commissionDTO.getFrozenPromotion();

        }

        CommissionDTO commissionDTO = new CommissionDTO();
        commissionDTO.setOrderCount(orderCount);
        commissionDTO.setTotalPromotion(totalPromotion);
        commissionDTO.setRealPromotion(realPromotion);
        commissionDTO.setSurplusPromotion(surplusPromotion);
        commissionDTO.setFrozenPromotion(frozenPromotion);
        return RespBean.ok("提现成功！", commissionDTO);
    }
}
