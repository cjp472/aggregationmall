package com.yunwa.aggregationmall.dao.pdd;


import com.yunwa.aggregationmall.pojo.pdd.po.PddPromotionRate;
import org.springframework.stereotype.Repository;

@Repository
public interface PddPromotionRateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PddPromotionRate record);

    int insertSelective(PddPromotionRate record);

    PddPromotionRate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PddPromotionRate record);

    int updateByPrimaryKey(PddPromotionRate record);

    Double selectLastRate();
}