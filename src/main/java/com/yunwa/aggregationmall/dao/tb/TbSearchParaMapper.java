package com.yunwa.aggregationmall.dao.tb;

import com.yunwa.aggregationmall.pojo.tb.po.TbSearchPara;
import org.springframework.stereotype.Repository;

@Repository
public interface TbSearchParaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbSearchPara record);

    int insertSelective(TbSearchPara record);

    TbSearchPara selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbSearchPara record);

    int updateByPrimaryKey(TbSearchPara record);

    //获取最后一条记录
    TbSearchPara getLastRecord();
}