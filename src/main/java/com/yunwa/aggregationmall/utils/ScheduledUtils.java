package com.yunwa.aggregationmall.utils;

import com.yunwa.aggregationmall.service.pdd.PddGoodsService;
import com.yunwa.aggregationmall.service.pdd.PromotionUrlService;
import com.yunwa.aggregationmall.service.tb.TbGoodsService;
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
    @Autowired
    private TbGoodsService tbGoodsService;

    @Scheduled(cron="0 0 4 * * ?")
    public void getGoodsData(){
        //获取拼多多商品数据
        pddGoodsService.goodsSearch();
        //获取拼多多商品推广链接
        promotionUrlService.insertGoodsUrl();
        //获取淘宝商品数据
        //tbGoodsService.getTbGoodsInfo();
    }
}
