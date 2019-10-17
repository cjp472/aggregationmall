package com.yunwa.aggregationmall.utils;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

/**
 * Created on 2019/10/17.
 * 数值转换
 * @author yueyang
 */
@Component
public class NumberConvertUtil {
    //Double 类型的数值保留两位小数
    public Double keepTwoDecimals(Double number){
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(number));
    }
}
