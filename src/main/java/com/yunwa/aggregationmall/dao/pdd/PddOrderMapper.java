package com.yunwa.aggregationmall.dao.pdd;


import com.yunwa.aggregationmall.pojo.pdd.po.PddOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PddOrderMapper {
    int deleteByPrimaryKey(String orderSn);

    int insert(PddOrder record);

    int insertSelective(PddOrder record);

    PddOrder selectByPrimaryKey(String orderSn);

    int updateByPrimaryKeySelective(PddOrder record);

    int updateByPrimaryKey(PddOrder record);

    PddOrder selectByOrderSn(String order_sn);

    int insertOrderData(PddOrder pddOrder);

    //获取该用户所有的订单号
    List<String> getAllOrderSn(Long user_id);

    //更新订单表
    int updateOrder(PddOrder pddOrder);

    //需返还的佣金之和
    Long getTotalPromotionAmount(Long user_id);

    //将已完成的订单移入finished_order
    int moveToFinishedOrder(Long user_id);

    //删除原表中的数据
    int deleteFinishedOrder(Long user_id);
}