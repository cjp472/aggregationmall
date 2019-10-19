package com.yunwa.aggregationmall.controller.tb;

import com.github.pagehelper.PageInfo;
import com.yunwa.aggregationmall.pojo.pdd.po.PddGoods;
import com.yunwa.aggregationmall.pojo.tb.po.TbGoodsWithBLOBs;
import com.yunwa.aggregationmall.service.tb.TbGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created on 2019/10/17.
 *
 * @author yueyang
 */

@RestController
@RequestMapping(value = "/user")
public class UserGoodsController {
    @Autowired
    private TbGoodsService tbGoodsService;

    /**
     * 首页商品查询
     * @param pageNum   当前页
     * @param categoryId  分类Id
     * @param sortType 排序方式
     * @param keyword   关键词
     * @return
     */
    @GetMapping(value = "/getTbGoodsList")
    public PageInfo<TbGoodsWithBLOBs> getGoodsList(@RequestParam(value = "pageNum", required = false) int pageNum,
                                           @RequestParam(value = "categoryId", required = false) String categoryId,
                                           @RequestParam(value = "sortType", required = false) String sortType,
                                           @RequestParam(value = "keyword", required = false) String keyword){
        HashMap<String, Object> map = new HashMap<>();
        if (categoryId != null && categoryId != ""){
            map.put("categoryId", categoryId);
        }
        if (sortType != null && sortType != ""){
            map.put("sortType", sortType);
        }
        if (keyword != null && keyword != ""){
            map.put("keyword", keyword);
        }
        return tbGoodsService.getGoodsList(pageNum, map);
    }

    /**
     * 获取淘宝商品详情
     * @param itemId 商品id
     * @return 商品实体
     */
    @GetMapping(value = "/getTbGoodsDetail")
    public TbGoodsWithBLOBs getTbGoodsDetail(Long itemId){
        return tbGoodsService.getTbGoodsDetail(itemId);
    }

}
