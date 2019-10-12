package com.yunwa.aggregationmall.provider.pdd;

import com.alibaba.fastjson.JSON;
import com.pdd.pop.ext.apache.http.HttpEntity;
import com.pdd.pop.ext.apache.http.client.methods.CloseableHttpResponse;
import com.pdd.pop.ext.apache.http.client.methods.HttpGet;
import com.pdd.pop.ext.apache.http.impl.client.CloseableHttpClient;
import com.pdd.pop.ext.apache.http.impl.client.HttpClients;
import com.pdd.pop.ext.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SuoImAPI {
    //长链转短链
    public String getShortUrl(String longUrl){
        //发送GET请求
        CloseableHttpClient client;
        CloseableHttpResponse response;
        String shortUrl = "";
        try {
            HttpGet httpGet = new HttpGet(longUrl);
            client = HttpClients.createDefault();
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            shortUrl = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shortUrl;
    }
}
