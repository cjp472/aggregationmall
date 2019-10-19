package com.yunwa.aggregationmall.controller.pdd;

import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.pojo.pdd.dto.OrderDto;
import com.yunwa.aggregationmall.pojo.pdd.po.PromotionUrl;
import com.yunwa.aggregationmall.provider.pdd.OrderAPI;
import com.yunwa.aggregationmall.service.pdd.*;
import com.yunwa.aggregationmall.utils.ScheduledUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private ScheduledUtils scheduledUtils;
    @Autowired
    private PidService adminService;

    /**
     * 根据商商品标签ID查询拼多多商品信息，插入到数据库
     * @return
     */
    @PostMapping(value = "/searchPddGoods")
    public String getGoodsInfo(){
        scheduledUtils.getGoodsData();
        return "ok";
        //return pddGoodsService.goodsSearch();
    }
    /*@PostMapping(value = "/searchPddGoods")
    public String getGoodsInfo(){
        return pddGoodsService.goodsSearch();
    }*/

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

    /**
     * 获取推广位 jd
     * @param number  要生成的推广位数量，默认为10，范围为：1~100
     * @param p_id_name_list    pid名字数组
     * @return  pid 集合
     */
    @RequestMapping("/getPid")
    public Object getPid(@RequestParam("number") long number,
                         @RequestParam(value = "p_id_name_list", required = false) List<String> p_id_name_list){
        adminService.getPid(number, p_id_name_list);
        return "ok";
    }


}
