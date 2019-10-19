package com.yunwa.aggregationmall;

import com.yunwa.aggregationmall.provider.pdd.WeChatAPI;
import com.yunwa.aggregationmall.provider.tb.TCommandAPI;
import com.yunwa.aggregationmall.service.pdd.PddGoodsService;
import com.yunwa.aggregationmall.service.pdd.impl.PddGoodsServiceImpl;
import com.yunwa.aggregationmall.service.pdd.impl.PidServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    private PddGoodsService pddGoodsService;

    @Test
    public void testService(){
        //PddGoodsServiceImpl service = new PddGoodsServiceImpl();
        //service.goodsSearch("女装", null,10);
        //service.delPddGoods();

//        GoodsInterfaceTest test = new GoodsInterfaceTest();
//        test.goodsSearch();

        /*PidServiceImpl adminService = new PidServiceImpl();
        List<String> strings = new ArrayList<>();
        strings.add(0, "a");
        strings.add(1, "b");
        adminService.getPid(2, strings);*/
        //WeChatAPI.getAccessToken("wx956068d954f63185", "434f4cc794e4cb9b5fd7ee32894a8653" );
        //TCommandAPI tCommandAPI = new TCommandAPI();
        //String tCommand = tCommandAPI.createTCommand("www.baidu.com", "测试！测试！测试！");
        //System.out.println(tCommand);
        pddGoodsService.goodsSearch();
    }

}
