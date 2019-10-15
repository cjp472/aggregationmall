package com.yunwa.aggregationmall.utils;

import org.springframework.stereotype.Component;

/**
 * Created on 2019/10/15.
 *
 * @author yueyang
 */
@Component
public class SalesTipUtils {

    /**
     * 将销量转换为纯数字
     * @param getSales_tip  商品销量
     * @return 纯数字形式的销量
     */
    public String chengeToSalesNum(String getSales_tip){
        Integer sales_tip = null;
        //判断字符串包不包含“万”字
        if (getSales_tip.indexOf("万") != -1){
            //只保留“万”字前面的数据
            //getSales_tip = getSales_tip.substring(0, getSales_tip.length() - 1);
            getSales_tip = getSales_tip.split("万")[0];
            //转换为相应的数值型
            sales_tip = (int)(Double.valueOf(getSales_tip) * 10000);
            return sales_tip.toString();
            //再将数据转换为String类型并去掉小数点然后赋值给商品DTO对象
            //getSales_tip = sales_tip.toString().split(".")[0];
        }else if (getSales_tip.indexOf("+") != -1){
            //没万字但是有“+”号的情况
            getSales_tip = getSales_tip.replace("+", "");
            return getSales_tip;
        }else {
            return getSales_tip;
        }

    }
}
