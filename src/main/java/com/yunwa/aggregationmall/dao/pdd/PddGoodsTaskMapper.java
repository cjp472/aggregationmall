package com.yunwa.aggregationmall.dao.pdd;

import com.yunwa.aggregationmall.pojo.pdd.po.PddGoodsTask;
import org.springframework.stereotype.Repository;

@Repository
public interface PddGoodsTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PddGoodsTask record);

    int insertSelective(PddGoodsTask record);

    PddGoodsTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PddGoodsTask record);

    int updateByPrimaryKey(PddGoodsTask record);

    //查询商品任务表的最后一条记录
    PddGoodsTask selectLastTask();
}