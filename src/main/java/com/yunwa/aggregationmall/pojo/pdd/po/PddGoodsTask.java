package com.yunwa.aggregationmall.pojo.pdd.po;

import java.io.Serializable;
import java.util.Date;

public class PddGoodsTask implements Serializable {
    private Integer id;     //主键

    private String opt_id;   //商品类目id

    private Date create_time;//任务创建时间

    private Integer goods_num;//期望的商品数量

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(String opt_id) {
        this.opt_id = opt_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(Integer goods_num) {
        this.goods_num = goods_num;
    }
}