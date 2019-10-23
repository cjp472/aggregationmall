package com.yunwa.aggregationmall.utils;

import com.yunwa.aggregationmall.service.pdd.PddGoodsService;
import com.yunwa.aggregationmall.service.pdd.PromotionUrlService;
import com.yunwa.aggregationmall.service.tb.TbGoodsService;
import com.yunwa.aggregationmall.service.tb.TbOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    @Autowired
    private TbOrderService tbOrderService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //设置日期格式
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //定时爬取拼多多商品数据
    @Scheduled(cron="0 0 4 * * ?")
    public void getGoodsData(){
        //获取拼多多商品数据
        pddGoodsService.goodsSearch();
        //获取拼多多商品推广链接
        promotionUrlService.insertGoodsUrl();
        //删除优惠券过期的商品
        pddGoodsService.deleteOverdueGoods();
        logger.info("定时任务开始执行--->"+df.format(new Date()));
    }

    //定时爬取淘宝商品数据
    @Scheduled(cron="0 0 3 * * ?")
    public void getTbGoodsData(){
        //获取淘宝商品数据
        tbGoodsService.getTbGoodsInfo();
        //删除优惠券过期的商品
        tbGoodsService.deleteOverdueGoods();
        logger.info("定时任务开始执行--->"+df.format(new Date()));
    }

    //定时获取淘宝用户订单数据
//    @Scheduled(cron = "0 */3 * * * ?")
//    public void getOrderData(){
//        tbOrderService.tbOrderSearch();
//        logger.info("定时任务开始执行--->"+df.format(new Date()));
//    }

    //定时更新淘宝订单状态数据（每月15号和25号凌晨四点）
    @Scheduled(cron = "0 0 04 15,25 * *")
    public void updateOrderStatus(){
        tbOrderService.updateOrderStatus();
        logger.info("定时任务开始执行--->"+df.format(new Date()));
    }

}
