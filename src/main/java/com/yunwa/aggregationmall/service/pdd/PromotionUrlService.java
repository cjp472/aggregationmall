package com.yunwa.aggregationmall.service.pdd;

import com.yunwa.aggregationmall.pojo.pdd.po.PromotionUrl;

public interface PromotionUrlService {

    //获取链接对象
    PromotionUrl getPromotionUrl(long goods_id);
}
