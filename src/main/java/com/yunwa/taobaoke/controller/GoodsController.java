package com.yunwa.taobaoke.controller;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import com.yunwa.taobaoke.pojo.PddGoods;
import com.yunwa.taobaoke.service.PddGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GoodsController {

    @Autowired
    private PddGoodsService pddGoodsService;

    /**
     * 根据关键字或商品类目id查询拼多多商品信息，插入到数据库
     * @param keyword 商品关键字
     * @param opt_id 商品标签类目ID
     * @param page_size 每页商品数量
     * @return
     */
    @PostMapping(value = "/searchPddGoods")
    public Map<Object, Object> getGoodsInfo(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "opt_id", required = false) Long opt_id,
                               @RequestParam(value = "page_size", required = false) Integer page_size){
        HashMap<Object, Object> map = new HashMap<>();
        if ( pddGoodsService.goodsSearch(keyword, opt_id, page_size) != null){
            map.put("code", "200");
        }
        map.put("code", "500");
        return map;
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
     * 首页查询商品
     * @param pageNum   当前页
     * @param category_name     分类名
     * @param sort_type     排序方式
     * @param keyword   关键字
     * @return  商品信息集合
     */
    @RequestMapping("/getGoodsList")
    public PageInfo<PddGoods> getGoodsList(@RequestParam(value = "pageNum", required = false) int pageNum,
                                           @RequestParam(value = "category_name", required = false) String category_name,
                                           @RequestParam(value = "sort_type", required = false) String sort_type,
                                           @RequestParam(value = "keyword", required = false) String keyword){
        HashMap<String, Object> map = new HashMap<>();
        if (category_name != null){
            map.put("category_name", category_name);
        }
        if (sort_type != null){
            map.put("sort_type", sort_type);
        }
        if (keyword != null){
            map.put("keyword", keyword);
        }
        return pddGoodsService.getGoodsList(pageNum, map);
    }

    /**
     * 获取拼多多商品详情
     * @param goods_id  商品id
     * @return  商品详细信息
     */
    @RequestMapping("/showGoodsDetil")
    public PddGoods showGoodsDetil(long goods_id){
        return pddGoodsService.showGoodsDetil(goods_id);
    }
}
