package com.yunwa.aggregationmall.service.pdd;


import com.github.pagehelper.PageInfo;
import com.yunwa.aggregationmall.pojo.pdd.dto.PddGoodsDto;
import com.yunwa.aggregationmall.pojo.pdd.po.PddGoods;
import com.yunwa.aggregationmall.pojo.pdd.vo.PddGoodsDocumentVO;

import java.util.HashMap;
import java.util.List;

public interface PddGoodsService {
    String goodsSearch();

    String getGoodsPicUrls(PddGoodsDto pddGoodsDto);

    //void getUrls(String p_id, List<PddGoods> list);

    boolean delPddGoods();

    PageInfo<PddGoods> getGoodsList(int pageNum, HashMap<String, Object> map);

    PddGoods getGoodsDetil(long goods_id);

    PddGoodsDocumentVO getGoodsDocument(long goods_id);

}
