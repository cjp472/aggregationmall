package com.yunwa.aggregationmall.provider.pdd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsSearchResponse;
import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.pojo.pdd.dto.PddGoodsDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GoodsAPI {
    public HashMap<String, Object> getGoodsData(Long opt_id, Integer page){
        //发送请求的客户端对象
        PopClient client = new PopHttpClient(PddConstantValues.clientId, PddConstantValues.clientSecret);
        PddDdkGoodsSearchRequest request = new PddDdkGoodsSearchRequest();
        HashMap<String, Object> map = new HashMap<>();

        request.setOptId(opt_id);
        request.setPage(page);
        request.setPageSize(100);

        PddDdkGoodsSearchResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        String goods_list = null;

        try {
            String goodsData = JsonUtil.transferToJson(response);
            JSONObject m1;
            m1 = JSON.parseObject(goodsData);       //将json文本转化为jsonobject
            String goods_search_response = m1.getString("goods_search_response");   //获取goods_search_response
            m1 = JSON.parseObject(goods_search_response);           //将json文本转化为jsonobject
            goods_list = m1.getString("goods_list");    //获取商品信息列表
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 1);
            return map;
        }

        //封装成商品DTO对象
        List<PddGoodsDto> list = null;
        if (goods_list != null){
            list = JSONArray.parseArray(goods_list, PddGoodsDto.class);
        }
        map.put("code", 2);
        map.put("data", list);
        return map;
    }
}
