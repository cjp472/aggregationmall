package com.yunwa.taobaoke.pojo.po;

/**
 * 商品链接对象
 */
public class PromotionUrl {
    private Long goods_id;      //商品id

    private String mobile_url;  //唤醒拼多多app的推广长链接

    private String short_url;   //推广短链接

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }
}
