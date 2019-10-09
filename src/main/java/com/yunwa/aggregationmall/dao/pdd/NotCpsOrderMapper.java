package com.yunwa.aggregationmall.dao.pdd;


import com.yunwa.aggregationmall.pojo.pdd.po.NotCpsOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface NotCpsOrderMapper {

    int insert(NotCpsOrder record);

    int insertOrderSn(String order_sn);

    int select(String order_sn);
}