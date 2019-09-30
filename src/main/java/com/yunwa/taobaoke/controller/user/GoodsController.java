package com.yunwa.taobaoke.controller.user;

import com.github.pagehelper.PageInfo;
import com.yunwa.taobaoke.pojo.po.PddGoods;
import com.yunwa.taobaoke.pojo.po.PromotionUrl;
import com.yunwa.taobaoke.pojo.vo.PddGoodsDocumentVo;
import com.yunwa.taobaoke.service.PddGoodsService;
import com.yunwa.taobaoke.service.PromotionUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController("user")
@RequestMapping("/user")
public class GoodsController {

    @Autowired
    private PddGoodsService pddGoodsService;

    @Autowired
    private PromotionUrlService promotionUrlService;

    /**
     * 根据商商品标签ID查询拼多多商品信息，插入到数据库
     *
     * @param opt_id 商品标签类目ID
     * @param page_size 每页商品数量
     * @param page  默认值1，商品分页数
     * @param p_id  商品推广位id
     * @return
     *//*
    @PostMapping(value = "/searchPddGoods")
    public Map<Object, Object> getGoodsInfo(@RequestParam(value = "opt_id") Long opt_id,
                               @RequestParam(value = "page_size", required = false) Integer page_size,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam("p_id") String p_id){
        HashMap<Object, Object> map = new HashMap<>();
        if ( pddGoodsService.goodsSearch(opt_id, page_size, p_id, page) != null){
            map.put("code", "200");
        }
        map.put("code", "500");
        return map;
    }

    //插入1000条数据
    @RequestMapping("insert")
    public String insert(Long opt_id, Integer page_size, String p_id){
        for (int i=0; i<10; i++){
            this.getGoodsInfo(opt_id, page_size, (i+1), p_id);
        }

        return "ok";
    }

    *//**
     * 删除拼多多商品
     *//*
    @PostMapping(value = "/delPddGoods")
    public Map<Object, Object> delPddGoods(){
        HashMap<Object, Object> map = new HashMap<>();
        if(pddGoodsService.delPddGoods()){
            map.put("code", "200");
        }
        map.put("code", "500");
        return map;
    }*/

    /**
     * 首页查询商品
     * @param pageNum   当前页
     * @param opt_name    商品标签ID
     * @param sort_type     排序方式
     * @param keyword   关键字
     * @return  商品信息集合
     */
    @RequestMapping("/getGoodsList")
    public PageInfo<PddGoods> getGoodsList(@RequestParam(value = "pageNum", required = false) int pageNum,
                                             @RequestParam(value = "opt_name", required = false) String opt_name,
                                             @RequestParam(value = "sort_type", required = false) String sort_type,
                                             @RequestParam(value = "keyword", required = false) String keyword){
        HashMap<String, Object> map = new HashMap<>();
        if (opt_name != null){
            map.put("opt_name", opt_name);
        }
        if (sort_type != null){
            map.put("sort_type", sort_type);
        }
        if (keyword != null){
            map.put("keyword", keyword);
        }
        System.out.println(map.toString());
        return pddGoodsService.getGoodsList(pageNum, map);
    }

    /**
     * 获取拼多多商品详情
     * @param goods_id  商品id
     * @return  商品详细信息
     */
    @RequestMapping("/showGoodsDetil")
    public PddGoods showGoodsDetil(long goods_id){
        return pddGoodsService.getGoodsDetil(goods_id);
    }

    /**
     * 查询商品推广链接对象
     * @param goods_id  商品id
     * @return  链接对象
     */
    @RequestMapping("/getPromotionUrl")
    public PromotionUrl  getPromotionUrl(long goods_id){
        return promotionUrlService.getPromotionUrl(goods_id);
    }

    /**
     * 获取商品文案
     * @param goods_id  商品id
     * @return 文案对象
     */
    @RequestMapping("/getGoodsDocument")
    public PddGoodsDocumentVo getGoodsDocument(long goods_id){
        System.out.println(goods_id);
        return pddGoodsService.getGoodsDocument(goods_id);
    }
}
