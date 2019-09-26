package com.yunwa.taobaoke.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsBasicInfoGetRequest;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsDetailRequest;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.request.PddGoodsDetailGetRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsBasicInfoGetResponse;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsDetailResponse;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsSearchResponse;
import com.pdd.pop.sdk.http.api.response.PddGoodsDetailGetResponse;
import com.yunwa.taobaoke.constant.PddConstantValues;

import java.util.ArrayList;
import java.util.List;

public class GoodsInterfaceTest {

    String clientId = "3ebe31a78aee470189690aa9e1cf9317";
    String clientSecret = "75cffb0c91451a6b177d2de5ba2036e51685f668";

    /**
     * 查询商品信息
     */
    public void goodsSearch(){
        PopClient client = new PopHttpClient(clientId, clientSecret);

        PddDdkGoodsSearchRequest request = new PddDdkGoodsSearchRequest();
        request.setKeyword("女装");
        request.setPageSize(10);

        PddDdkGoodsSearchResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(JsonUtil.transferToJson(response));
    }

    /**
     * 获取商品基本信息
     */
    public void getGoodsInfo(){
        PopClient client = new PopHttpClient(PddConstantValues.clientId, PddConstantValues.clientSecret);

        PddDdkGoodsBasicInfoGetRequest request = new PddDdkGoodsBasicInfoGetRequest();
        List<Long> goodsIdList = new ArrayList<Long>();
        goodsIdList.add(2915674786L);
        request.setGoodsIdList(goodsIdList);
        PddDdkGoodsBasicInfoGetResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(JsonUtil.transferToJson(response));
    }

    /**
     * 获取商品详细信息
     */
    public void getGoodsDetails(){
        PopClient client = new PopHttpClient(clientId, clientSecret);

        PddDdkGoodsDetailRequest request = new PddDdkGoodsDetailRequest();
        List<Long> goodsIdList = new ArrayList<Long>();
        goodsIdList.add(2915674786L);
        request.setGoodsIdList(goodsIdList);
        PddDdkGoodsDetailResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String goodsDetails = JsonUtil.transferToJson(response);
        //JSONObject jsonObject = JSONObject.fromObject(goodsDetails);
//        JSONObject jsonObject = new JSONObject();
        //m1 = JSON.parseObject(goodsDetails);       //将json文本转化为jsonobject
        //String goods_detail_response = m1.getString("goods_detail_response");   //获取第二层的商品详情回复数据
        //JSONArray goods_details = jsonObject.getJSONArray("goods_details");
        //System.out.println(goods_details);
    }

    /**
     * 获取商品明细 （单价），pdd.PddGoods.detail.get
     */
    public void getGoods() {
        PopClient client = new PopHttpClient(clientId, clientSecret);

        PddGoodsDetailGetRequest request = new PddGoodsDetailGetRequest();
        request.setGoodsId(3643095122L);
//        PddGoodsDetailGetResponse response = client.syncInvoke(request, accessToken);
//        System.out.println(JsonUtil.transferToJson(response));
    }
}
