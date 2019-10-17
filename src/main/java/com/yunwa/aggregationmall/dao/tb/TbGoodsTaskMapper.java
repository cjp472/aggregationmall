package com.yunwa.aggregationmall.dao.tb;

import com.yunwa.aggregationmall.pojo.tb.po.TbGoodsTask;
import com.yunwa.aggregationmall.pojo.tb.po.TbSearchPara;
import org.springframework.stereotype.Repository;

@Repository
public interface TbGoodsTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbGoodsTask record);

    int insertSelective(TbGoodsTask record);

    TbGoodsTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbGoodsTask record);

    int updateByPrimaryKey(TbGoodsTask record);

    //获取任务表的最后一条记录
    TbGoodsTask getLastRecord();
}