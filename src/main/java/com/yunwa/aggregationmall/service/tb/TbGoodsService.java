package com.yunwa.aggregationmall.service.tb;

import com.github.pagehelper.PageInfo;
import com.yunwa.aggregationmall.pojo.tb.po.TbGoodsWithBLOBs;

import java.util.HashMap;

public interface TbGoodsService {
    void tbGoodsSearch();

    void getTbGoodsInfo();

    PageInfo<TbGoodsWithBLOBs> getGoodsList(int pageNum, HashMap<String, Object> map);
}
