package com.yunwa.aggregationmall.dao.tb;

import com.yunwa.aggregationmall.pojo.tb.po.TbOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbOrder record);

    int insertSelective(TbOrder record);

    TbOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbOrder record);

    int updateByPrimaryKey(TbOrder record);

    TbOrder selectByTradeId(String tradeId);

    //更新订单信息（添加用户id）
    int updateBytradeParentId(@Param("tradeId") String tradeId, @Param("userId") String userId);

    //获取该用户所有订单
    List<TbOrder> getAllOrdersByUser(String userId);

    //更新订单状态
    int updateTkStatus(@Param("tradeParentId") String tradeParentId, @Param("tkStatus") Long tkStatus);

    //获取该用户所有满足条件的佣金和
    Double getAllRealPromotion(String userId);

    //获取该用户所有满足条件的订单总数
    int getOrderCount(String userId);

    //查询该用户本次可提现的佣金
    Double getRealPromotion(String userId);

    //查询未收货佣金
    Double getSurplusPromotion(String userId);

    //查询所有订单中，最早的一条记录付款的时间
    String getAllOrders();

    //将返佣状态改为0
    int changePromotionStatus(String userId);

    //将已返佣的订单插入历史表
    int insertIntoFinished(String userId);

    //删除已返佣的订单
    int delete(String userId);
}