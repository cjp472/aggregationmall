package com.yunwa.aggregationmall.utils;

import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.pojo.pdd.po.PddOptId;
import com.yunwa.aggregationmall.provider.pdd.GoodsAPI;
import com.yunwa.aggregationmall.provider.pdd.GoodsOptAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created on 2019/10/12.
 *
 * @author yueyang
 */
@Component
public class OptUtils {
    @Autowired
    private GoodsOptAPI goodsOptAPI;

    public PddOptId getGoodsOptInfo(Long optId){
            PddOptId pddOptId = goodsOptAPI.getOptInfo(optId);
            if (pddOptId != null){
               Integer total_page_count = (pddOptId.getTotal_goods_count()/100)%2 == 0 ? pddOptId.getTotal_goods_count()/100 :
                       pddOptId.getTotal_goods_count()/100 + 1;
                pddOptId.setTotal_page_count(total_page_count);
            }
        return pddOptId;
    }
}
