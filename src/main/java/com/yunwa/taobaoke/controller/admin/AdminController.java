package com.yunwa.taobaoke.controller.admin;

import com.yunwa.taobaoke.pojo.PId;
import com.yunwa.taobaoke.service.AdminService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 获取推广位 id
     * @param number    数量
     * @param p_id_name_list    pid名字数组
     * @return  pid 集合
     */
    @RequestMapping("/getPid")
    public Object getPid(@RequestParam("number") long number,
                            @RequestParam(value = "p_id_name_list", required = false) List<String> p_id_name_list){
        adminService.getPid(number, p_id_name_list);
        return "ok";
    }

}
