package com.yunwa.aggregationmall.pojo.pdd.po;

public class NotCpsOrder {
    private String orderSn;     //订单号

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }
}