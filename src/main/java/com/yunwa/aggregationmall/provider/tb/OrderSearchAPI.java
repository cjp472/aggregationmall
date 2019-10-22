package com.yunwa.aggregationmall.provider.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkOrderDetailsGetRequest;
import com.taobao.api.response.TbkOrderDetailsGetResponse;
import com.yunwa.aggregationmall.constant.TbkConstantValues;
import com.yunwa.aggregationmall.pojo.tb.po.TbOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created on 2019/10/21.
 *
 * @author yueyang
 */
@Component
public class OrderSearchAPI {
    TaobaoClient client = new DefaultTaobaoClient(TbkConstantValues.GET__URL, TbkConstantValues.APP_KEY, TbkConstantValues.APP_SERCET);
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //Long pageNo = 1L;   //当前页

    //获取淘宝订单详情
    public Map<String, Object> getOrderDetail(Long pageNo){
        HashMap<String, Object> map = new HashMap<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设置日期格式
        //当前时间
        String nowTime = df.format(new Date());
        //当前时间的前20分钟
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -20);   //20分钟之前的时间
        Date beforeD = beforeTime.getTime();
        String before20Time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beforeD);  // 前20分钟时间

        TbkOrderDetailsGetRequest req = new TbkOrderDetailsGetRequest();
        req.setQueryType(2L);
        req.setPositionIndex("2222_334666");
        req.setPageSize(100L);
        req.setTkStatus(12L);
        req.setEndTime(nowTime);
        req.setStartTime(before20Time);
        req.setPageNo(pageNo);
        String responseBody = null;
        try {
            TbkOrderDetailsGetResponse rsp = client.execute(req);
            responseBody = rsp.getBody();
        } catch (ApiException e) {
            e.printStackTrace();
            logger.info("获取订单接口调用异常,返回的数据：{}", responseBody);
            return map;
        }
        try {
            if (responseBody != null){
                JSONObject jsonObject = JSON.parseObject(responseBody);
                JSONObject tbk_order_details_get_response = jsonObject.getJSONObject("tbk_order_details_get_response");
                JSONObject data = tbk_order_details_get_response.getJSONObject("data");
                //是否还有下一页
                Boolean hasNext = data.getBoolean("has_next");
                JSONObject results = data.getJSONObject("results");
                //订单对象数组
                JSONArray publisher_order_dto = results.getJSONArray("publisher_order_dto");
                //将数据封装到订单对象里
                List<TbOrder> tbOrders = JSONArray.parseArray(publisher_order_dto.toJSONString(), TbOrder.class);
                map.put("hasNext", hasNext);
                map.put("tbOrders", tbOrders);
            }
        } catch (Exception e){
            e.printStackTrace();
            logger.info("订单数据为空！查询时间:{}", nowTime);
            return map;
        }
        return map;
    }

    //查询订单
    public HashMap<String, Object> getOrderByTime(String endTime, String startTime, Long pageNo){
        HashMap<String, Object> map = new HashMap<>();
        TbkOrderDetailsGetRequest req = new TbkOrderDetailsGetRequest();
        req.setQueryType(2L);
        req.setPageSize(100L);
        req.setPageNo(pageNo);
        req.setEndTime(endTime);
        req.setStartTime(startTime);
        String responseBody = null;
        TbkOrderDetailsGetResponse rsp = null;
        try {
            rsp = client.execute(req);
            responseBody = rsp.getBody();
        } catch (ApiException e) {
            e.printStackTrace();
            logger.info("获取订单接口调用异常,返回的数据：{}", responseBody);
            return map;
        }
        List<TbOrder> tbOrders = null;
        try {
            if (responseBody != null){
                JSONObject jsonObject = JSON.parseObject(responseBody);
                JSONObject tbk_order_details_get_response = jsonObject.getJSONObject("tbk_order_details_get_response");
                JSONObject data = tbk_order_details_get_response.getJSONObject("data");
                //是否还有下一页
                Boolean hasNext = data.getBoolean("has_next");
                JSONObject results = data.getJSONObject("results");
                //订单对象数组
                JSONArray publisher_order_dto = results.getJSONArray("publisher_order_dto");
                //将数据封装到订单对象里
                tbOrders = JSONArray.parseArray(publisher_order_dto.toJSONString(), TbOrder.class);
                map.put("hasNext", hasNext);
                map.put("tbOrders", tbOrders);
            }
        } catch (Exception e){
            e.printStackTrace();
            logger.info("订单数据为空！查询订单的开始时间:{}", startTime);
            return map;
        }
        return map;
    }

}
