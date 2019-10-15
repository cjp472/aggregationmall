package com.yunwa.aggregationmall.utils;

import com.yunwa.aggregationmall.service.pdd.PddGoodsService;
import com.yunwa.aggregationmall.service.pdd.PromotionUrlService;
import com.yunwa.aggregationmall.service.pdd.impl.PddGoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 2019/10/14.
 * @Description 定时任务，用于爬取拼多多商品数据
 * @author yueyang
 */
@Transactional
@Component
public class ScheduledUtils {
    @Autowired
    private PddGoodsService pddGoodsService;

    @Autowired
    private PromotionUrlService promotionUrlService;

    @Scheduled(cron="0 0 4 * * ?")
    public void getGoodsData(){
        //获取商品数据
        pddGoodsService.goodsSearch();
        //获取商品推广链接
        promotionUrlService.insertGoodsUrl();
    }
}
