package com.yunwa.taobaoke.service;

import com.github.pagehelper.PageInfo;
import com.yunwa.taobaoke.pojo.PddGoods;

import java.util.HashMap;

public interface PddGoodsService {
    public Object goodsSearch(String keyword, Long opt_id, Integer page_size);

    boolean delPddGoods();

    PageInfo<PddGoods> getGoodsList(int pageNum, HashMap<String, Object> map);

    PddGoods showGoodsDetil(long goods_id);
}
