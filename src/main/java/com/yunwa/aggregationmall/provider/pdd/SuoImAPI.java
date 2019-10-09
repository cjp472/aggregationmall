package com.yunwa.aggregationmall.provider.pdd;

import com.pdd.pop.ext.apache.http.HttpEntity;
import com.pdd.pop.ext.apache.http.client.methods.CloseableHttpResponse;
import com.pdd.pop.ext.apache.http.client.methods.HttpGet;
import com.pdd.pop.ext.apache.http.impl.client.CloseableHttpClient;
import com.pdd.pop.ext.apache.http.impl.client.HttpClients;
import com.pdd.pop.ext.apache.http.util.EntityUtils;
import com.yunwa.aggregationmall.constant.PddConstantValues;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class SuoImAPI {
    public String getShortUrl(String keyword){
        //去掉开头的“买”字
        String _keyword = keyword.substring(1);
        //原链接
        String url = "http://localhost:8088/index.html?keyword="+_keyword;
        /*try {
            //将原链接编码
            java.net.URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        //拼装成长链接
        String longUrl = "http://suo.im/api.htm?url="+url+"&key="+PddConstantValues.key;

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
