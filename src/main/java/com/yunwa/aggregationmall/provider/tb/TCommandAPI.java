package com.yunwa.aggregationmall.provider.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkTpwdCreateResponse;
import com.yunwa.aggregationmall.constant.TbkConstantValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created on 2019/10/17.
 * 淘口令生成接口
 * @author yueyang
 */
@Component
public class TCommandAPI {
    TaobaoClient client = new DefaultTaobaoClient(TbkConstantValues.GET__URL, TbkConstantValues.APP_KEY, TbkConstantValues.APP_SERCET);
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String createTCommand(String couponShareUrl, String shortTitle, String pictUrl){
        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
        req.setText(shortTitle);
        req.setUrl(couponShareUrl);
        req.setLogo(pictUrl);
        TbkTpwdCreateResponse rsp = null;
        String model = "";
        try {
            rsp = client.execute(req);
            String responseBody = rsp.getBody();
            JSONObject jsonObject = JSON.parseObject(responseBody);
            JSONObject tbk_tpwd_create_response = jsonObject.getJSONObject("tbk_tpwd_create_response");
            JSONObject data = tbk_tpwd_create_response.getJSONObject("data");
            model = data.getString("model");
        } catch (ApiException e) {
            e.printStackTrace();
            logger.info("淘口令接口调用异常，url{}，shortTitle{}", couponShareUrl, shortTitle);
            return "";
        }
        return model;
    }
}
