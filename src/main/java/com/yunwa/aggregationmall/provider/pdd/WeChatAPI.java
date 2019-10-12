package com.yunwa.aggregationmall.provider.pdd;

import com.alibaba.fastjson.JSONObject;
import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;

public class WeChatAPI {
    /**
     * 获取access_token
     * @param appid
     * @param appSecret
     */
    public static String getAccessToken(String appid, String appSecret) {
        String requestUrl = PddConstantValues.ACCESS_TOKEN.replace("APPID", appid).replace("APPSECRET", appSecret);
        String result = CommonUtils.getRemoteRequest(requestUrl, "获取assess_token对象");
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println(jsonObject.getString("access_token"));
        if (StringUtils.isNotEmpty(jsonObject.getString("errmsg"))) {
            //logger.error("获取access_token失败:{}", jsonObject.getString("errmsg"));
            return null;
        }
        //logger.info("得到的access_token:{}", jsonObject.getString("access_token"));
        return jsonObject.getString("access_token");
    }

    /*public static String long2short(String long_url){
        String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
        String accessToken = getAccessToken(PddConstantValues.VX_APP_ID, PddConstantValues.VX_APP_SECRET);
        url = url.replace("ACCESS_TOKEN", accessToken);

        Map<String, String> map = new HashMap<>();
        map.put("action","long2short");
        map.put("long_url",long_url);
        JSONObject jsonObject = HttpJsonWechatUtils.httpRequest(url,"POST", JSON.toJSONString(map));

        String errCode = jsonObject.getString("errcode");
        return jsonObject.toString();
    }*/


}
