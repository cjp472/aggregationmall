package com.yunwa.aggregationmall.utils;

import com.alibaba.fastjson.JSON;
import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.provider.pdd.SuoImAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Component
public class UrlUtils {
    @Autowired
    private SuoImAPI suoImAPI;

    //长链转短链接
    public String getShortURL(String keyword){
        //去掉开头的“买”字并编码
        String _keyword = URLEncoder.encode(keyword.substring(1));
        //拼接原链接
        String url = "http://localhost:8088/index.html?keyword="+_keyword;
        try {
            //将原链接编码
            url = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //拼装成长链接
        String longUrl = "http://suo.im/api.htm?url="+url+"&key="+PddConstantValues.SUO_KEY;
        //转成短连接
        String shortUrl = suoImAPI.getShortUrl(longUrl);
        HashMap<String, String> map = new HashMap<>();
        map.put("url", shortUrl);
        return JSON.toJSONString(map);
    }
}
