/*
package com.yunwa.aggregationmall.controller.pdd;

import com.yunwa.aggregationmall.service.pdd.PidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys")
public class PidController {
    @Autowired
    private PidService adminService;

    */
/**
     * 获取推广位 jd
     * @param number  要生成的推广位数量，默认为10，范围为：1~100
     * @param p_id_name_list    pid名字数组
     * @return  pid 集合
     *//*

    @RequestMapping("/getPid")
    public Object getPid(@RequestParam("number") long number,
                            @RequestParam(value = "p_id_name_list", required = false) List<String> p_id_name_list){
        adminService.getPid(number, p_id_name_list);
        return "ok";
    }

}
*/
