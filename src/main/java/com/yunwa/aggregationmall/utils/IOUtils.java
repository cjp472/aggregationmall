package com.yunwa.aggregationmall.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @ProjectName: taotie
 * @Package: com.lmfun.dinnerservice.common.util
 * @ClassName: IOUtils
 * @Description: IO 流处理工具类
 * @Author: liuzekun
 * @CreateDate: 2019/1/14 11:28
 * @Version: 1.0
 */
public class IOUtils {

    /**
     * 将流转化为字符串
     */
    public static String inputStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
