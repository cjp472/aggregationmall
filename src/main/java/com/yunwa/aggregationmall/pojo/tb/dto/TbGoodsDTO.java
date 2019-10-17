package com.yunwa.aggregationmall.pojo.tb.dto;

import java.util.Date;

public class TbGoodsDTO {
    private Integer id;     //主键

    private Long itemId;    //商品id

    private String shortTitle;  //商品短标题

    private String couponStartTime; //优惠券信息-优惠券开始时间

    private String couponEndTime;   //优惠券信息-优惠券结束时间

    private String title;           //商品标题

    private String pictUrl;         //商品主图

    private String zkFinalPrice;    //折扣价（元）

    private String itemUrl;         //宝贝地址

    private String commissionRate;  //商品信息-佣金比率。1550表示15.5%

    private Long volume;            //商品信息-30天销量

    private String shopTitle;       //店铺名称

    private String url;             //宝贝推广链接

    private String couponAmount;    //优惠券（元）

    private String couponStartFee;  //优惠券信息-优惠券起用门槛，满X元可用。如：满299元减20元

    private String tCommand;        //淘口令

    private String realPrice;       //券后价（元）

    private Integer userType;       //店铺信息-卖家类型。0表示集市，1表示天猫

    private Date createTime;        //创建时间

    private Integer totalPromotion; //能获取的总佣金，单位（分）

    private String item_description;    //宝贝描述(推荐理由)

    private String small_images;        //商品轮播图

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getSmall_images() {
        return small_images;
    }

    public void setSmall_images(String small_images) {
        this.small_images = small_images;
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

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice == null ? null : realPrice.trim();
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

    public Integer getTotalPromotion() {
        return totalPromotion;
    }

    public void setTotalPromotion(Integer totalPromotion) {
        this.totalPromotion = totalPromotion;
    }
}