package com.yunwa.aggregationmall.service.pdd;

import com.yunwa.aggregationmall.common.RespBean;
import com.yunwa.aggregationmall.pojo.CommissionDTO;

public interface OrderService {
    /*boolean insertOrderData(CommissionDTO orderDto);*/

    //绑定订单
    RespBean orderBind(String order_sn, String user_id);

    //返佣
    CommissionDTO getMoney(String user_id);
}
