package com.yunwa.aggregationmall.controller.tb;

import com.github.pagehelper.PageInfo;
import com.yunwa.aggregationmall.pojo.pdd.po.PddGoods;
import com.yunwa.aggregationmall.pojo.tb.po.TbGoodsWithBLOBs;
import com.yunwa.aggregationmall.service.tb.TbGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * Created on 2019/10/17.
 *
 * @author yueyang
 */
public class UserGoodsController {
    @Autowired
    private TbGoodsService tbGoodsService;

    /**
     * 首页商品查询
     * @param pageNum   当前页
     * @param categoryName  分类名
     * @param sortType 排序方式
     * @param keyword   关键词
     * @return
     */
    @GetMapping(value = "/getGoodsList")
    public PageInfo<TbGoodsWithBLOBs> getGoodsList(@RequestParam(value = "pageNum", required = false) int pageNum,
                                           @RequestParam(value = "categoryName", required = false) String categoryName,
                                           @RequestParam(value = "sortType", required = false) String sortType,
                                           @RequestParam(value = "keyword", required = false) String keyword){
        HashMap<String, Object> map = new HashMap<>();
        if (categoryName != null){
            map.put("categoryName", categoryName);
        }
        if (sortType != null){
            map.put("sortType", sortType);
        }
        if (keyword != null){
            map.put("keyword", keyword);
        }
        return tbGoodsService.getGoodsList(pageNum, map);
    }
}
