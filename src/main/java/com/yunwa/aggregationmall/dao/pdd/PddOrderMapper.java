package com.yunwa.aggregationmall.dao.pdd;


import com.yunwa.aggregationmall.pojo.pdd.po.PddOrder;
import com.yunwa.aggregationmall.pojo.pdd.vo.PddPromotionAmountVO;
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
    List<String> getAllOrderSn(String user_id);

    //更新订单表
    int updateOrder(PddOrder pddOrder);

    //需返还的佣金之和
    Long getTotalRealPromotionAmount(String user_id);

    //将已完成的订单移入finished_order
    int moveToFinishedOrder(String user_id);

    //删除原表中的数据
    int deleteFinishedOrder(String user_id);

    //更改返佣状态
    int changePromotionStatus(String user_id);

    //查询返佣
    PddPromotionAmountVO selectPddPromotionAmount(String user_id);

    //查询被冻结佣金
    Long getFrozenPromotion(String user_id);
}