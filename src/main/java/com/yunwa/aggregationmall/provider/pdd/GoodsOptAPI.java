package com.yunwa.aggregationmall.provider.pdd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsSearchResponse;
import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.dao.pdd.PddGoodsMapper;
import com.yunwa.aggregationmall.pojo.pdd.po.PddOptId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created on 2019/10/12.
 *
 * @author yueyang
 */
@Component
public class GoodsOptAPI {
    @Autowired
    private GoodsAPI goodsAPI;

    //发送请求的客户端对象
    PopClient client = new PopHttpClient(PddConstantValues.clientId, PddConstantValues.clientSecret);

    //获取商品类目信息
    public PddOptId getOptInfo(Long opt_id){
        PddDdkGoodsSearchRequest request = new PddDdkGoodsSearchRequest();

        request.setOptId(opt_id);

        PddDdkGoodsSearchResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String goods_list = null;
        String goodsData = JsonUtil.transferToJson(response);
        JSONObject m1 = JSON.parseObject(goodsData);       //将json文本转化为jsonobject
        String goods_search_response = m1.getString("goods_search_response");   //获取goods_search_response
        m1 = JSON.parseObject(goods_search_response);           //将json文本转化为jsonobject
        //Integer total_count = m1.getString("total_count");    //获取商品信息列表
        Integer total_count = m1.getInteger("total_count"); //获取商品总数量
        PddOptId pddOptId = new PddOptId();
        pddOptId.setOpt_id(opt_id);
        pddOptId.setTotal_goods_count(total_count);

        return pddOptId;
    }
}
