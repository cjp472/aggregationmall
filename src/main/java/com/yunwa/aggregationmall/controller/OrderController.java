package com.yunwa.aggregationmall.controller;

import com.yunwa.aggregationmall.utils.Order;
import com.yunwa.aggregationmall.common.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2019/10/23.
 *
 * @author yueyang
 */
@RestController
@RequestMapping(value = "/user")
public class OrderController {
    @Autowired
    private Order order;

    /**
     * 订单绑定
     * @param tradeId 订单号
     * @param userId 用户id
     * @return
     */
    @PostMapping(value = "/orderBind")
    public RespBean orderBind(String tradeId, String userId){
        return order.orderBind(tradeId, userId);
    }

    /**
     * 提现
     * @param userId 用户id
     * @return
     */
    @PostMapping(value = "/getMoney")
    public RespBean getMoney(String userId){
        return order.getMoney(userId);
    }

}
