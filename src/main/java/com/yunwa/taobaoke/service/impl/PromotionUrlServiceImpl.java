package com.yunwa.taobaoke.service.impl;

import com.yunwa.taobaoke.dao.PromotionUrlMapper;
import com.yunwa.taobaoke.pojo.po.PromotionUrl;
import com.yunwa.taobaoke.service.PromotionUrlService;
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
