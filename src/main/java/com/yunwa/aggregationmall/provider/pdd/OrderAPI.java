package com.yunwa.aggregationmall.provider.pdd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkOrderDetailGetRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkOrderDetailGetResponse;
import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.pojo.pdd.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderAPI {
    //发送请求的客户端对象
    PopClient client = new PopHttpClient(PddConstantValues.clientId, PddConstantValues.clientSecret);

    public OrderDto getOrderDetail(String order_sn){
        PddDdkOrderDetailGetRequest request = new PddDdkOrderDetailGetRequest();
        request.setOrderSn(order_sn);
        PddDdkOrderDetailGetResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String orderString = JsonUtil.transferToJson(response);

        //转换字符串
        String order_detail_response = JSON.parseObject(orderString).getString("order_detail_response");
        //封装成数据传输对象
        OrderDto orderDto = JSON.parseObject(order_detail_response, new TypeReference<OrderDto>() {});
        return orderDto;
    }


}
