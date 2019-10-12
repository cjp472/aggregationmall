package com.yunwa.aggregationmall.provider.pdd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsDetailRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsDetailResponse;
import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.pojo.pdd.dto.PddGoodsDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GoodsPicUrlAPI {
    //发送请求的客户端对象
    PopClient client = new PopHttpClient(PddConstantValues.clientId, PddConstantValues.clientSecret);

    public JSONArray getGoodsPicURL(PddGoodsDto pddGoodsDto){
        PddDdkGoodsDetailRequest request = new PddDdkGoodsDetailRequest();

        List<Long> goodsIdList = new ArrayList<Long>();
        goodsIdList.add(pddGoodsDto.getGoods_id());
        request.setGoodsIdList(goodsIdList);

        PddDdkGoodsDetailResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray jsonArray;
        try {
            String goodsDetails = JsonUtil.transferToJson(response);

            JSONObject jsonObject;
            jsonObject = JSON.parseObject(goodsDetails);        //将json文本转化为jsonobject
            String goods_details = jsonObject.getString("goods_detail_response");   //获取第二层的商品详情回复数据
            jsonObject = JSON.parseObject(goods_details);       //将json文本转化为jsonobject
            jsonArray = jsonObject.getJSONArray("goods_details");     //获取goods_details数组
        }catch (Exception e){
            e.printStackTrace();
            return new JSONArray();
        }

        //获取数组的第一个元素，即商品详情
        JSONObject goodsInfo =  jsonArray.getJSONObject(0);
        String goods_gallery_urls = goodsInfo.getString("goods_gallery_urls");    //获取轮播图数组
        JSONArray urlsArray = JSONArray.parseArray(goods_gallery_urls);     //转换成数组

        return urlsArray;
    }
}
