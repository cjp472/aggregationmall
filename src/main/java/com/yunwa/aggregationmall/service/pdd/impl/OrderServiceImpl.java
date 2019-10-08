package com.yunwa.aggregationmall.service.pdd.impl;

import com.yunwa.aggregationmall.dao.pdd.NotCpsOrderMapper;
import com.yunwa.aggregationmall.dao.pdd.PddOrderMapper;
import com.yunwa.aggregationmall.pojo.pdd.dto.OrderDto;
import com.yunwa.aggregationmall.pojo.pdd.po.PddOrder;
import com.yunwa.aggregationmall.provider.pdd.OrderAPI;
import com.yunwa.aggregationmall.service.pdd.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private PddOrderMapper pddOrderMapper;

    @Autowired
    private OrderAPI orderAPI;

    @Autowired
    private NotCpsOrderMapper notCpsOrderMapper;

    /*@Override
    public boolean insertOrderData(OrderDto orderDto) {
        //将Dto封装成po对象，存入到数据库
        PddOrder pddOrder = new PddOrder();
        BeanUtils.copyProperties(orderDto, pddOrder);
        int row = orderMapper.insertOrderData(pddOrder);
        if (row > 0){
            return true;
        }
        return false;
    }*/

    @Override
    public HashMap<String, Object> orderBind(String order_sn) {
        //在订单表查询有无此订单信息
        int row = pddOrderMapper.selectByOrderSn(order_sn);
        HashMap<String, Object> map = new HashMap<>();
        if(row > 0) {
            map.put("status", "exist");
        }else {
            //调用pddAPI查询此订单信息
            OrderDto orderDto = orderAPI.getOrderDetail(order_sn);
            if (orderDto == null){
                //为空说明不是CPS订单，存入not_cps_order表
                notCpsOrderMapper.insertOrderSn(order_sn);
                map.put("status", "notCpsOrder");
            }else {
                //不为空说明是CPS订单，将Dto封装成po对象，存入pdd_order表
                PddOrder pddOrder = new PddOrder();
                BeanUtils.copyProperties(orderDto, pddOrder);
                pddOrderMapper.insertOrderData(pddOrder);
                map.put("status", "cpsOrder");
            }
        }
        return map;
    }
}
