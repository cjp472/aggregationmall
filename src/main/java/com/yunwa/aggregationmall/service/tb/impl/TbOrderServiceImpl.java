package com.yunwa.aggregationmall.service.tb.impl;

import com.yunwa.aggregationmall.common.RespBean;
import com.yunwa.aggregationmall.dao.pdd.PddPromotionRateMapper;
import com.yunwa.aggregationmall.dao.tb.TbOrderMapper;
import com.yunwa.aggregationmall.pojo.tb.dto.TbPromotionDTO;
import com.yunwa.aggregationmall.pojo.tb.po.TbOrder;
import com.yunwa.aggregationmall.provider.tb.OrderSearchAPI;
import com.yunwa.aggregationmall.service.tb.TbOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created on 2019/10/21.
 *
 * @author yueyang
 */
@Service
public class TbOrderServiceImpl implements TbOrderService {
    @Autowired
    private OrderSearchAPI orderSearchAPI;
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private PddPromotionRateMapper pddPromotionRateMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private Long pageNo = 1L;   //页码

    /**
     * 订单查询，查询最新的订单信息
     */
    @Override
    public void tbOrderSearch() {
        Map<String, Object> orderDetail = orderSearchAPI.getOrderDetail(1L);
        //pageNo++;
        //订单数据
        List<TbOrder> tbOrders = (List<TbOrder>) orderDetail.get("tbOrders");
        //是否有下一页
        //Boolean hasNext = (Boolean) orderDetail.get("hasNext");
        if (tbOrders != null){
            //遍历集合将订单数据插入带数据库
            for (TbOrder tbOrder : tbOrders){
                //获取佣金金额
                String totalCommissionFee = tbOrder.getTotalCommissionFee();
                //获取返佣比例
                Double rate = pddPromotionRateMapper.selectLastRate();
                //设置用户佣金
                Double realPromotion = Double.parseDouble(totalCommissionFee) * rate;
                tbOrder.setRealTotalCommission(realPromotion);
                int row = tbOrderMapper.insert(tbOrder);
                if (row == 0){
                    logger.info("订单数据插入失败，订单号{}", tbOrder.getTradeParentId());
                }
            }
            //如果有下一页
            /*if (hasNext){
                this.tbOrderSearch();
            }*/
        }

    }

    /**
     * 订单绑定
     * @param tradeId 订单号
     * @param userId 用户id
     * @return
     */
    @Override
    public RespBean tbOrderBind(String tradeId, String userId) {
        //查询最新的订单
        //this.tbOrderSearch();
        //先查询订单表里有无该订单
        TbOrder tbOrder = tbOrderMapper.selectByTradeId(tradeId);
        if (tbOrder != null){
            //更新订单(添加userId)
            tbOrderMapper.updateBytradeParentId(tradeId, userId);
            return RespBean.ok("订单绑定成功！");
            /*int row = tbOrderMapper.updateBytradeParentId(tradeId, userId);
            if (row > 0){
                return RespBean.ok("订单绑定成功！");
            }
            return RespBean.error("订单绑定失败！", tbOrder);*/
        }else {
            //调用淘宝接口查询订单
            Map<String, Object> orderDetail = orderSearchAPI.getOrderDetail(1L);
            //订单数据
            List<TbOrder> tbOrders = (List<TbOrder>) orderDetail.get("tbOrders");
            if (tbOrders != null){
                for (TbOrder order : tbOrders){
                   if (tradeId == order.getTradeParentId()){    //找到了该订单信息
                       //更新订单(添加userId)
                       tbOrderMapper.updateBytradeParentId(tradeId, userId);
                       return RespBean.ok("订单绑定成功！");
                   }
                }
            }
        }
       return RespBean.error("订单绑定失败！无此订单信息。");
    }

    /**
     * 提现
     * @param userId 用户id
     */
    @Override
    public RespBean tbGetMoney(String userId) {
        //获取该用户所有的订单
        List<TbOrder> allOrders = tbOrderMapper.getAllOrdersByUser(userId);
        TbPromotionDTO tbPromotionDTO = new TbPromotionDTO();
        if (allOrders.size() != 0){
            //遍历订单获取订单付款时间，更新订单状态
            /*for (TbOrder tbOrder : allOrders){
                //当前订单的付款时间
                String tkPaidTime = tbOrder.getTkPaidTime();
                //当前订单的订单号
                String tradeParentId = tbOrder.getTradeParentId();
                //获取已知时间的订单集合
                List<TbOrder> orders = orderSearchAPI.getOrderByTime();
                for (TbOrder order : orders){
                    if (order.getTradeParentId() == tradeParentId){
                        //获取当前订单的状态
                        Long tkStatus = order.getTkStatus();
                        //更新该条订单的状态
                        tbOrderMapper.updateTkStatus(tradeParentId, tkStatus);
                    }
                }
            }*/
            //计算所有满足条件的订单的该用户佣金总和（3，12，14）
            Double allRealPromotion = tbOrderMapper.getAllRealPromotion(userId);
            //查询该用户的订单总数（3，12，14）
            int orderCount = tbOrderMapper.getOrderCount(userId);
            //查询该用户本次可提现金额（3）
            Double realPromotion = tbOrderMapper.getRealPromotion(userId) == null ? 0L : tbOrderMapper.getRealPromotion(userId);
            //未收货佣金（12）
            Double SurplusPromotion = tbOrderMapper.getSurplusPromotion(userId) == null ? 0L : tbOrderMapper.getSurplusPromotion(userId);
            //被冻结佣金（12，14）
            Double frozenPromotion = allRealPromotion - realPromotion;

            tbPromotionDTO.setTotalPromotion(allRealPromotion);
            tbPromotionDTO.setOrderCount(orderCount);
            tbPromotionDTO.setRealPromotion(realPromotion);
            tbPromotionDTO.setSurplusPromotion(SurplusPromotion);
            tbPromotionDTO.setFrozenPromotion(frozenPromotion);
        }else {
            return RespBean.error("提现失败，无此用户订单信息！");
        }
        return RespBean.ok("提现成功！", tbPromotionDTO);
    }

    /**
     * 更新所有订单状态
     */
    public void updateOrderStatus(){
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //20分钟
        long time = 20*60*1000;
        //当前页
        Long pageNo = 1L;
        //查询所有订单中，最早的一条记录付款的时间
        String tkPaidTime = tbOrderMapper.getAllOrders();
        //转换为Date类型
        Date earlyTime = null;
        try {
            earlyTime = df.parse(tkPaidTime);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("无订单信息需更新！");
        }

        //当前时间
        /*String format = df.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -10);*/

        Date now = new Date();  //当前时间
        Date beforeDate = new Date(now.getTime() - time);  //20分钟前的时间
        //Date beforeDate = new Date(earlyTime.getTime() - time);  //20分钟前的时间
        do {
            HashMap<String, Object> map = orderSearchAPI.getOrderByTime(now.toString(), beforeDate.toString(), pageNo);
            //订单集合
            List<TbOrder> tbOrders = (List<TbOrder>) map.get("tbOrders");
            //是否还有下一页
            Boolean hasNext = (Boolean) map.get("hasNext");
            if (tbOrders.size() > 0){
                //遍历更新订单
                for (TbOrder order : tbOrders){
                    //没更新说明表里没这个订单或者状态没发生变化
                    if (tbOrderMapper.updateTkStatus(order.getTradeParentId(), order.getTkStatus()) == 0){
                        //执行插入操作，已存在的话不会插入
                        tbOrderMapper.insert(order);
                    }
                }
                if (hasNext){
                    pageNo++;
                    continue;
                }
            }
            //更改时间
            now = beforeDate;
            beforeDate = new Date(beforeDate.getTime() - time) ;
            //System.out.println("now:"+now+"beforeDate:"+beforeDate);
        }while (now.compareTo(earlyTime) == 1);     //等于1代表now>earlyTime
    }

    /**
     * 将已完成返佣的订单移入历史表
     * @param userId 用户id
     * @return
     */
    @Override
    @Transactional
    public boolean moveToFinished(String userId) {
        //将返佣状态改为0（已返佣）
        if (tbOrderMapper.changePromotionStatus(userId) > 0){
            //将已完成返佣的订单插入历史表
            if (tbOrderMapper.insertIntoFinished(userId) > 0){
                //删除已完成返佣的订单
                tbOrderMapper.delete(userId);
                return true;
            }
        }
        return false;
    }

}
