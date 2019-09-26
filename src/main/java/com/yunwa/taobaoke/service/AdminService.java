package com.yunwa.taobaoke.service;

import com.yunwa.taobaoke.pojo.PId;

import java.util.List;

public interface AdminService {
    void getPid(long number, List<String> p_id_name_list);
}
