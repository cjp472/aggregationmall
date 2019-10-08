package com.yunwa.aggregationmall.pojo.pdd.vo;

import com.yunwa.aggregationmall.pojo.pdd.po.PddGoods;
import com.yunwa.aggregationmall.pojo.pdd.po.PromotionUrl;

/**
 * 封装的商品文案对象
 */
public class PddGoodsDocumentVo extends PddGoods {
    private PromotionUrl promotion_url;      //推广链接对象

    public PromotionUrl getPromotion_url() {
        return promotion_url;
    }

    public void setPromotion_url(PromotionUrl promotion_url) {
        this.promotion_url = promotion_url;
    }
}
