package com.yunwa.aggregationmall.controller.pdd;

import com.yunwa.aggregationmall.service.pdd.PddGoodsService;
import com.yunwa.aggregationmall.service.pdd.PromotionUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class AdminGoodsController {

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
     */
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
    @RequestMapping("/insert")
    public String insert(Long opt_id, Integer page_size, String p_id){
        for (int i=0; i<10; i++){
            this.getGoodsInfo(opt_id, page_size, (i+1), p_id);
        }

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

}
