package com.yunwa.aggregationmall.pojo.tb.po;

import java.io.Serializable;
import java.util.Date;

public class TbOrder implements Serializable {
    private Integer id;         //主键

    private String userId;      //用户id

    private String tkPaidTime;  //订单在淘宝拍下付款的时间

    private String payPrice;    //买家确认收货的付款金额（不包含运费金额）

    private String pubShareFee; //结算预估收入=结算金额*提成。

    private Long tkOrderRole;   //二方：佣金收益的第一归属者； 三方：从其他淘宝客佣金中进行分成的推广者

    private String tkEarningTime;   //订单确认收货后且商家完成佣金支付的时间

    private Long adzoneId;      //推广位id

    private String pubSharePreFee;      //付款预估收入=付款金额*提成。

    private String alipayTotalPrice;    //买家拍下付款的金额（不包含运费金额）

    private String tradeParentId;       //买家在淘宝后台显示的订单编号

    private String tkCreateTime;        //订单创建的时间

    private Long tkStatus;              //3：订单结算，12：订单付款， 13：订单失效，14：订单成功

    private String totalCommissionRate; //佣金比率

    private String totalCommissionFee;  //佣金金额=结算金额*佣金比率

    private Double realTotalCommission; //用户的佣金金额

    private Integer promotionStatus;    //返佣状态

    private Date createTime;            //记录创建时间

    private Date modifyTime;            //记录修改时间

    public Integer getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(Integer promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTkPaidTime() {
        return tkPaidTime;
    }

    public void setTkPaidTime(String tkPaidTime) {
        this.tkPaidTime = tkPaidTime == null ? null : tkPaidTime.trim();
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice == null ? null : payPrice.trim();
    }

    public String getPubShareFee() {
        return pubShareFee;
    }

    public void setPubShareFee(String pubShareFee) {
        this.pubShareFee = pubShareFee == null ? null : pubShareFee.trim();
    }

    public Long getTkOrderRole() {
        return tkOrderRole;
    }

    public void setTkOrderRole(Long tkOrderRole) {
        this.tkOrderRole = tkOrderRole;
    }

    public String getTkEarningTime() {
        return tkEarningTime;
    }

    public void setTkEarningTime(String tkEarningTime) {
        this.tkEarningTime = tkEarningTime == null ? null : tkEarningTime.trim();
    }

    public Long getAdzoneId() {
        return adzoneId;
    }

    public void setAdzoneId(Long adzoneId) {
        this.adzoneId = adzoneId;
    }

    public String getPubSharePreFee() {
        return pubSharePreFee;
    }

    public void setPubSharePreFee(String pubSharePreFee) {
        this.pubSharePreFee = pubSharePreFee == null ? null : pubSharePreFee.trim();
    }

    public String getAlipayTotalPrice() {
        return alipayTotalPrice;
    }

    public void setAlipayTotalPrice(String alipayTotalPrice) {
        this.alipayTotalPrice = alipayTotalPrice == null ? null : alipayTotalPrice.trim();
    }

    public String getTradeParentId() {
        return tradeParentId;
    }

    public void setTradeParentId(String tradeParentId) {
        this.tradeParentId = tradeParentId == null ? null : tradeParentId.trim();
    }

    public String getTkCreateTime() {
        return tkCreateTime;
    }

    public void setTkCreateTime(String tkCreateTime) {
        this.tkCreateTime = tkCreateTime == null ? null : tkCreateTime.trim();
    }

    public Long getTkStatus() {
        return tkStatus;
    }

    public void setTkStatus(Long tkStatus) {
        this.tkStatus = tkStatus;
    }

    public String getTotalCommissionRate() {
        return totalCommissionRate;
    }

    public void setTotalCommissionRate(String totalCommissionRate) {
        this.totalCommissionRate = totalCommissionRate == null ? null : totalCommissionRate.trim();
    }

    public String getTotalCommissionFee() {
        return totalCommissionFee;
    }

    public void setTotalCommissionFee(String totalCommissionFee) {
        this.totalCommissionFee = totalCommissionFee == null ? null : totalCommissionFee.trim();
    }

    public Double getRealTotalCommission() {
        return realTotalCommission;
    }

    public void setRealTotalCommission(Double realTotalCommission) {
        this.realTotalCommission = realTotalCommission;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}