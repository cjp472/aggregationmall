package com.yunwa.aggregationmall.service.pdd;

public interface OrderService {
    /*boolean insertOrderData(OrderDto orderDto);*/

    //绑定订单
    String orderBind(String order_sn, String user_id);

    //返佣
    String getMoney(String user_id);
}
