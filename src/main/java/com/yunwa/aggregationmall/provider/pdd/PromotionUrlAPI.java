package com.yunwa.aggregationmall.provider.pdd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPromotionUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPromotionUrlGenerateResponse;
import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.pojo.pdd.po.PddGoods;
import com.yunwa.aggregationmall.pojo.pdd.po.PromotionUrl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PromotionUrlAPI {
    //发送请求的客户端对象
    PopClient client = new PopHttpClient(PddConstantValues.clientId, PddConstantValues.clientSecret);

    public List<PromotionUrl> getPromotionURL(String p_id, Long goodsId){
        PddDdkGoodsPromotionUrlGenerateRequest request = new PddDdkGoodsPromotionUrlGenerateRequest();
        List<Long> goods_id_list = new ArrayList<>();

        //遍历获取商品id
        /*for (PddGoods goods : list){
            goods_id_list.add(goods.getGoods_id());
        }*/
        goods_id_list.add(goodsId);
        request.setPId(p_id);
        request.setGoodsIdList(goods_id_list);
        PddDdkGoodsPromotionUrlGenerateResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response_urls = JsonUtil.transferToJson(response);

        String  goods_promotion_url_list;
        try {
            JSONObject jsonObject = JSON.parseObject(response_urls);        //将json文本转化为jsonobject
            String goods_promotion_url_generate_response = jsonObject.getString("goods_promotion_url_generate_response");
            jsonObject = JSON.parseObject(goods_promotion_url_generate_response);
            goods_promotion_url_list = jsonObject.getString("goods_promotion_url_list");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        //封装为链接对象集合
        return JSONArray.parseArray(goods_promotion_url_list, PromotionUrl.class);
    }
}
