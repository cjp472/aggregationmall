package com.yunwa.aggregationmall.pojo.pdd.vo;

//拼多多返佣对象
public class PddPromotionAmountVO {
    private Integer order_count;        //订单数
    private Long predict_promotion;     //可提现佣金
    private Long real_promotion;        //提现中佣金
    private Long surplus_promotion;     //未收货佣金
    private Long frozen_promotion;      //被冻结佣金

    public Long getFrozen_promotion() {
        return frozen_promotion;
    }

    public void setFrozen_promotion(Long frozen_promotion) {
        this.frozen_promotion = frozen_promotion;
    }

    public Integer getOrder_count() {
        return order_count;
    }

    public void setOrder_count(Integer order_count) {
        this.order_count = order_count;
    }

    public Long getPredict_promotion() {
        return predict_promotion;
    }

    public void setPredict_promotion(Long predict_promotion) {
        this.predict_promotion = predict_promotion;
    }

    public Long getReal_promotion() {
        return real_promotion;
    }

    public void setReal_promotion(Long real_promotion) {
        this.real_promotion = real_promotion;
    }

    public Long getSurplus_promotion() {
        return surplus_promotion;
    }

    public void setSurplus_promotion(Long surplus_promotion) {
        this.surplus_promotion = surplus_promotion;
    }
}
