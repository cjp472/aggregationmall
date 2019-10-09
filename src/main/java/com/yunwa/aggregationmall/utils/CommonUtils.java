package com.yunwa.aggregationmall.utils;


import java.util.logging.Logger;

/**
 * @author: liuzekun
 */
public class CommonUtils {

//    private static final Logger logger = Logger.getLogger(CommonUtils.class);

    public static String postRemoteRequest(String url, String processName, String requestParam) {
        try {
            String postResult = HttpUtils.doPost(url, requestParam);
            return postResult;
        } catch (Exception e) {
            String errorMsg = String.format("POST请求服务出错,描述:%s,Url:%s,参数:%s", processName, url, requestParam);
            //throw new BaseException(BaseCode.FAIL_TO_INVOKE_REMOTE_SERVICE, errorMsg);
        }
        return null;
    }

    public static String getRemoteRequest(String url, String processName) {
        try {
            String getResult = HttpUtils.get(url);
            return getResult;
        } catch (Exception e) {
            String errorMsg = String.format("GET请求服务出错,描述:%s,url:%s", processName, url);
            //throw new BaseException(BaseCode.FAIL_TO_INVOKE_REMOTE_SERVICE, errorMsg);
        }
        return null;
    }

}
