package com.yunwa.aggregationmall.dao.tb;

import com.yunwa.aggregationmall.pojo.tb.po.TbOpt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbOptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbOpt tbOpt);

    int insertSelective(TbOpt record);

    TbOpt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbOpt record);

    int updateByPrimaryKey(TbOpt record);

    //根据分类名查询分类信息
    TbOpt selectByOptName(String optName);

    //更新分类的当前页字段
    int updateCurrentPage(@Param("optName") String optName, @Param("num") Long num);
}