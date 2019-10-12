package com.yunwa.aggregationmall.controller.pdd;

import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.pojo.pdd.dto.OrderDto;
import com.yunwa.aggregationmall.pojo.pdd.po.PromotionUrl;
import com.yunwa.aggregationmall.provider.pdd.OrderAPI;
import com.yunwa.aggregationmall.service.pdd.OptService;
import com.yunwa.aggregationmall.service.pdd.OrderService;
import com.yunwa.aggregationmall.service.pdd.PddGoodsService;
import com.yunwa.aggregationmall.service.pdd.PromotionUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class AdminController {

    @Autowired
    private PddGoodsService pddGoodsService;

    @Autowired
    private OptService optService;

    @Autowired
    private PromotionUrlService promotionUrlService;

    /**
     * 根据商商品标签ID查询拼多多商品信息，插入到数据库
     * @return
     */
    @PostMapping(value = "/searchPddGoods")
    public String getGoodsInfo(){
        return pddGoodsService.goodsSearch();
    }

    /**
     * 插入商品推广链接
     * @return
     */
    @RequestMapping(value = "/insertGoodsUrl")
    public String insertGoodsUrl(){
        promotionUrlService.insertGoodsUrl();
        return "ok";
    }

    /**
     * 删除拼多多商品
     */
    @PostMapping(value = "/delPddGoods")
    public Map<Object, Object> delPddGoods(){
        HashMap<Object, Object> map = new HashMap<>();
        if(pddGoodsService.delPddGoods()){
            map.put("code", "200");
        }
        map.put("code", "500");
        return map;
    }

    /**
     * 获取商品类目信息
     * @return
     */
    @RequestMapping(value = "/getOptId")
    public String getOptId(){
        optService.getOptInfo();
        return "ok";
    }


}
