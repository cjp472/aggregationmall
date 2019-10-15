package com.yunwa.aggregationmall.service.pdd.impl;

import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.dao.pdd.PddGoodsMapper;
import com.yunwa.aggregationmall.dao.pdd.PromotionUrlMapper;
import com.yunwa.aggregationmall.pojo.pdd.po.PromotionUrl;
import com.yunwa.aggregationmall.provider.pdd.PromotionUrlAPI;
import com.yunwa.aggregationmall.service.pdd.PromotionUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PromotionUrlServiceImpl implements PromotionUrlService {
    @Autowired
    private PromotionUrlMapper promotionUrlMapper;

    @Autowired
    private PddGoodsMapper pddGoodsMapper;

    @Autowired
    private PromotionUrlAPI promotionUrlAPI;

    @Override
    public PromotionUrl getPromotionUrl(long goods_id) {
        return promotionUrlMapper.getPromotionUrl(goods_id);
    }

    @Override
    public void insertGoodsUrl() {
        //获取所有没有生成推广链接的商品的id
        List<Long> list = pddGoodsMapper.getAllGoodsId();
        //查询到商品的推广链并插入到数据库
        for (Long goodsId : list){
            List<PromotionUrl> promotion_url_list = promotionUrlAPI.getPromotionURL(PddConstantValues.P_ID, goodsId);
            //遍历插入到数据库
            if (promotion_url_list != null){
                for (int i=0; i<promotion_url_list.size(); i++){
                    //设置商品id
                    promotion_url_list.get(i).setGoods_id(goodsId);
                    //执行插入操作
                    promotionUrlMapper.insertUrl(promotion_url_list.get(i));
                    //将goods表的has_url字段设为1
                    pddGoodsMapper.changeUrlStatus(goodsId);
                }
            }
        }

    }
}
