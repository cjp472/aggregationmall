package com.yunwa.aggregationmall.dao.pdd;

import com.yunwa.aggregationmall.pojo.pdd.po.PId;
import org.springframework.stereotype.Repository;

@Repository
public interface PIdMapper {

    //插入Pid
    void insertPid(PId pId);

    int deleteByPrimaryKey(String pId);

    int insert(PId record);

    int insertSelective(PId record);

    PId selectByPrimaryKey(String pId);

    int updateByPrimaryKeySelective(PId record);

    int updateByPrimaryKey(PId record);
}