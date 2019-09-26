package com.yunwa.taobaoke.pojo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 商品对象
 */
public class PddGoods implements Serializable {
    private long goods_id;          //商品id
    private String goods_name;      //商品名称
    private String min_group_price;   //最小拼团价（单位为分）,作为显示的单价
    private String goods_desc;      //商品描述
    private String goods_thumbnail_url;     //商品缩略图
    private String goods_image_url;         //商品主图
    private String goods_gallery_urls;     //商品轮播图
    private String mall_name;       //店铺名字
    private String category_name;   //商品类目名
    private boolean has_coupon;     //商品是否有优惠券
    private String coupon_discount;   //优惠券面额，单位为分
    private String promotion_rate;    //佣金比例，千分比
    private String sales_tip;       //已售卖件数
    private String desc_txt;        //描述分
    private String serv_txt;        //服务分
    private String lgst_txt;        //物流分

    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getMin_group_price() {
        return min_group_price;
    }

    public void setMin_group_price(String min_group_price) {
        this.min_group_price = min_group_price;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getGoods_thumbnail_url() {
        return goods_thumbnail_url;
    }

    public void setGoods_thumbnail_url(String goods_thumbnail_url) {
        this.goods_thumbnail_url = goods_thumbnail_url;
    }

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }

    public String getMall_name() {
        return mall_name;
    }

    public void setMall_name(String mall_name) {
        this.mall_name = mall_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean isHas_coupon() {
        return has_coupon;
    }

    public void setHas_coupon(boolean has_coupon) {
        this.has_coupon = has_coupon;
    }

    public String getCoupon_discount() {
        return coupon_discount;
    }

    public void setCoupon_discount(String coupon_discount) {
        this.coupon_discount = coupon_discount;
    }

    public String getPromotion_rate() {
        return promotion_rate;
    }

    public void setPromotion_rate(String promotion_rate) {
        this.promotion_rate = promotion_rate;
    }

    public String getSales_tip() {
        return sales_tip;
    }

    public void setSales_tip(String sales_tip) {
        this.sales_tip = sales_tip;
    }

    public String getDesc_txt() {
        return desc_txt;
    }

    public void setDesc_txt(String desc_txt) {
        this.desc_txt = desc_txt;
    }

    public String getServ_txt() {
        return serv_txt;
    }

    public void setServ_txt(String serv_txt) {
        this.serv_txt = serv_txt;
    }

    public String getGoods_gallery_urls() {
        return goods_gallery_urls;
    }

    public void setGoods_gallery_urls(String goods_gallery_urls) {
        this.goods_gallery_urls = goods_gallery_urls;
    }

    public String getLgst_txt() {
        return lgst_txt;

    }

    public void setLgst_txt(String lgst_txt) {
        this.lgst_txt = lgst_txt;
    }

    @Override
    public String toString() {
        return "PddGoods{" +
                "goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", min_group_price='" + min_group_price + '\'' +
                ", goods_desc='" + goods_desc + '\'' +
                ", goods_thumbnail_url='" + goods_thumbnail_url + '\'' +
                ", goods_image_url='" + goods_image_url + '\'' +
                ", goods_gallery_urls='" + goods_gallery_urls + '\'' +
                ", mall_name='" + mall_name + '\'' +
                ", has_coupon=" + has_coupon +
                ", coupon_discount='" + coupon_discount + '\'' +
                ", promotion_rate='" + promotion_rate + '\'' +
                ", sales_tip='" + sales_tip + '\'' +
                ", desc_txt='" + desc_txt + '\'' +
                ", serv_txt='" + serv_txt + '\'' +
                ", lgst_txt='" + lgst_txt + '\'' +
                '}';
    }
}
