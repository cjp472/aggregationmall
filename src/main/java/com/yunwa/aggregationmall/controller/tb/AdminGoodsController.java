package com.yunwa.aggregationmall.controller.tb;

import com.yunwa.aggregationmall.service.tb.TbGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sys")
public class AdminGoodsController {
    @Autowired
    private TbGoodsService tbGoodsService;

    /**
     * 获取选品库id
     * @return
     */
    @GetMapping(value = "/tbGoodsSearch")
    public String tbGoodsSearch(){
        tbGoodsService.tbGoodsSearch();
        return "";
    }
}
