package com.yunwa.aggregationmall.pojo;

import java.io.Serializable;

/**
 * Created on 2019/10/23.
 *  佣金传输对象
 * @author yueyang
 */
public class CommissionDTO implements Serializable {
    private Integer orderCount;          //订单数
    private Double totalPromotion;       //可提现佣金（总金额）
    private Double realPromotion;        //提现中佣金（本次返现金额，审核通过）
    private Double surplusPromotion;     //未收货佣金
    private Double frozenPromotion;      //被冻结佣金（已确认收货，审核中）

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Double getTotalPromotion() {
        return totalPromotion;
    }

    public void setTotalPromotion(Double totalPromotion) {
        this.totalPromotion = totalPromotion;
    }

    public Double getRealPromotion() {
        return realPromotion;
    }

    public void setRealPromotion(Double realPromotion) {
        this.realPromotion = realPromotion;
    }

    public Double getSurplusPromotion() {
        return surplusPromotion;
    }

    public void setSurplusPromotion(Double surplusPromotion) {
        this.surplusPromotion = surplusPromotion;
    }

    public Double getFrozenPromotion() {
        return frozenPromotion;
    }

    public void setFrozenPromotion(Double frozenPromotion) {
        this.frozenPromotion = frozenPromotion;
    }
}
