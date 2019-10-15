package com.yunwa.aggregationmall.dao.pdd;

import com.yunwa.aggregationmall.pojo.pdd.po.PddOptId;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PddOptIdMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PddOptId pddOptId);

    int insertSelective(PddOptId record);

    PddOptId selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PddOptId record);

    int updateByPrimaryKey(PddOptId record);

    //获取进行之中的类目信息
    PddOptId selectIsGoing();

    //重置当前页和进行中
    int update(Long optId);

    Integer getId(Long optId);

    int updateStatus(Integer id);

    PddOptId selectByOptId(Long optId);

    //更新当前页字段
    int updateCurrentPage(@Param("optId")Long optId, @Param("current_page")Integer current_page);
}