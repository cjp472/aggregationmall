package com.yunwa.aggregationmall.pojo.tb.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class TbGoods {
    private Integer id;     //主键

    @JSONField(name = "item_id")
    private Long itemId;    //商品id

    @JSONField(name = "short_title")
    private String shortTitle;      //商品短标题

    @JSONField(name = "coupon_start_time")
    private String couponStartTime;     //优惠券信息-优惠券开始时间

    @JSONField(name = "coupon_end_time")
    private String couponEndTime;       //优惠券信息-优惠券结束时间

    @JSONField(name = "title")
    private String title;               //商品标题

    @JSONField(name = "pict_url")
    private String pictUrl;             //商品主图

    @JSONField(name = "zk_final_price")
    private String zkFinalPrice;        //折扣价（元）

    @JSONField(name = "item_url")
    private String itemUrl;             //宝贝地址

    @JSONField(name = "commission_rate")
    private String commissionRate;      //商品信息-佣金比率。1550表示15.5%

    @JSONField(name = "volume")
    private Long volume;                //商品信息-30天销量

    @JSONField(name = "shop_title")
    private String shopTitle;           //店铺名称

    @JSONField(name = "url")
    private String url;                 //宝贝推广链接

    @JSONField(name = "coupon_amount")
    private String couponAmount;        //优惠券（元）

    @JSONField(name = "coupon_start_fee")
    private String couponStartFee;      //优惠券信息-优惠券起用门槛，满X元可用。如：满299元减20元

    private String tCommand;            //淘口令

    private Double realPrice;           //券后价

    @JSONField(name = "user_type")
    private Integer userType;           //店铺信息-卖家类型。0表示集市，1表示天猫

    private Date createTime;            //创建时间

    private Double totalPromotion;      //佣金

    @JSONField(name = "coupon_share_url")
    private String couponShareUrl;      //链接-宝贝+券二合一页面链接

    @JSONField(name = "level_one_category_name")
    private String categoryName;    //商品信息-一级类目名称,如：女装

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCouponShareUrl() {
        return couponShareUrl;
    }

    public void setCouponShareUrl(String couponShareUrl) {
        this.couponShareUrl = couponShareUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle == null ? null : shortTitle.trim();
    }

    public String getCouponStartTime() {
        return couponStartTime;
    }

    public void setCouponStartTime(String couponStartTime) {
        this.couponStartTime = couponStartTime == null ? null : couponStartTime.trim();
    }

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime == null ? null : couponEndTime.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPictUrl() {
        return pictUrl;
    }

    public void setPictUrl(String pictUrl) {
        this.pictUrl = pictUrl == null ? null : pictUrl.trim();
    }

    public String getZkFinalPrice() {
        return zkFinalPrice;
    }

    public void setZkFinalPrice(String zkFinalPrice) {
        this.zkFinalPrice = zkFinalPrice == null ? null : zkFinalPrice.trim();
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl == null ? null : itemUrl.trim();
    }

    public String getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate == null ? null : commissionRate.trim();
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle == null ? null : shopTitle.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount == null ? null : couponAmount.trim();
    }

    public String getCouponStartFee() {
        return couponStartFee;
    }

    public void setCouponStartFee(String couponStartFee) {
        this.couponStartFee = couponStartFee == null ? null : couponStartFee.trim();
    }

    public String gettCommand() {
        return tCommand;
    }

    public void settCommand(String tCommand) {
        this.tCommand = tCommand == null ? null : tCommand.trim();
    }

    public Double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Double realPrice) {
        this.realPrice = realPrice;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getTotalPromotion() {
        return totalPromotion;
    }

    public void setTotalPromotion(Double totalPromotion) {
        this.totalPromotion = totalPromotion;
    }
}