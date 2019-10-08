package com.yunwa.aggregationmall.service.pdd.impl;

import com.yunwa.aggregationmall.dao.pdd.PromotionUrlMapper;
import com.yunwa.aggregationmall.pojo.pdd.po.PromotionUrl;
import com.yunwa.aggregationmall.service.pdd.PromotionUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionUrlServiceImpl implements PromotionUrlService {
    @Autowired
    private PromotionUrlMapper promotionUrlMapper;

    @Override
    public PromotionUrl getPromotionUrl(long goods_id) {
        return promotionUrlMapper.getPromotionUrl(goods_id);
    }
}
