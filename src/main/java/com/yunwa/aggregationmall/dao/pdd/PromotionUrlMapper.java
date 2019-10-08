package com.yunwa.aggregationmall.dao.pdd;

import com.yunwa.aggregationmall.pojo.pdd.po.PromotionUrl;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionUrlMapper {

    //插入链接数据
    int insertUrl(PromotionUrl promotionUrl);

    //获取链接对象
    PromotionUrl getPromotionUrl(long goods_id);

    int deleteByPrimaryKey(Long goodsId);

    int insert(PromotionUrl record);

    int insertSelective(PromotionUrl record);

    PromotionUrl selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(PromotionUrl record);

    int updateByPrimaryKey(PromotionUrl record);


}
