package com.yunwa.aggregationmall.service.pdd;


public interface OrderService {
    /*boolean insertOrderData(OrderDto orderDto);*/

    //绑定订单
    Object orderBind(String order_sn);
}
