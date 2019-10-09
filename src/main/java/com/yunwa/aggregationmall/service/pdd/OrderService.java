package com.yunwa.aggregationmall.service.pdd;


import java.util.Map;

public interface OrderService {
    /*boolean insertOrderData(OrderDto orderDto);*/

    //绑定订单
    Object orderBind(String order_sn, Long user_id);

    Long getMoney(Long user_id);
}
