package com.yunwa.aggregationmall.dao.pdd;


import com.yunwa.aggregationmall.pojo.pdd.po.NotCpsOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface NotCpsOrderMapper {
    int deleteByPrimaryKey(String orderSn);

    int insert(NotCpsOrder record);

    int insertSelective(NotCpsOrder record);

    int insertOrderSn(String order_sn);
}