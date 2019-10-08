package com.yunwa.aggregationmall.dao.pdd;


import com.yunwa.aggregationmall.pojo.pdd.po.PddOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface PddOrderMapper {
    int deleteByPrimaryKey(String orderSn);

    int insert(PddOrder record);

    int insertSelective(PddOrder record);

    PddOrder selectByPrimaryKey(String orderSn);

    int updateByPrimaryKeySelective(PddOrder record);

    int updateByPrimaryKey(PddOrder record);

    int selectByOrderSn(String order_sn);

    int insertOrderData(PddOrder pddOrder);
}