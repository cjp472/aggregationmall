package com.yunwa.aggregationmall.service.pdd.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPidGenerateRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPidGenerateResponse;
import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.dao.pdd.PIdMapper;
import com.yunwa.aggregationmall.pojo.pdd.po.PId;
import com.yunwa.aggregationmall.service.pdd.PidService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PidServiceImpl implements PidService {
    @Resource
    private PIdMapper pIdMapper;

    /**
     * 获取推广位id
     * @param number    数量
     * @param p_id_name_list    pid名字数组
     * @return
     */
    @Override
    public void getPid(long number, List<String> p_id_name_list) {
        PopClient client = new PopHttpClient(PddConstantValues.clientId, PddConstantValues.clientSecret);

        PddDdkGoodsPidGenerateRequest request = new PddDdkGoodsPidGenerateRequest();
        request.setNumber(number);
        if(p_id_name_list.size() > 0){
            request.setPIdNameList(p_id_name_list);
        }

        //返回的pid回复对象
        PddDdkGoodsPidGenerateResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String pidResponse = JsonUtil.transferToJson(response);

        JSONObject jsonObject = new JSONObject();
        jsonObject = JSON.parseObject(pidResponse);        //将json文本转化为jsonobject
        String p_id_generate_response =  jsonObject.getString("p_id_generate_response");
        jsonObject = JSON.parseObject(p_id_generate_response);       //将json文本转化为jsonobject
        String p_id_list = jsonObject.getString("p_id_list");

        List<PId> list = JSONArray.parseArray(p_id_list, PId.class);

        //遍历集合插入到数据库
        for (PId pId : list){
            pIdMapper.insertPid(pId);
        }


//        JSONArray.parseArray(goods_list, PddGoods.class);
//        return adminMapper.getPid(number, p_id_name_list);
    }
}
