package com.yunwa.aggregationmall;

import com.yunwa.aggregationmall.service.pdd.impl.PidServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class test {

    @Test
    public void testService(){
        //PddGoodsServiceImpl service = new PddGoodsServiceImpl();
        //service.goodsSearch("女装", null,10);
        //service.delPddGoods();

//        GoodsInterfaceTest test = new GoodsInterfaceTest();
//        test.goodsSearch();

        PidServiceImpl adminService = new PidServiceImpl();
        List<String> strings = new ArrayList<>();
        strings.add(0, "a");
        strings.add(1, "b");
        adminService.getPid(2, strings);

    }
}
