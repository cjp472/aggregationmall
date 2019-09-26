package com.yunwa.taobaoke.dao;

import com.yunwa.taobaoke.pojo.PId;
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