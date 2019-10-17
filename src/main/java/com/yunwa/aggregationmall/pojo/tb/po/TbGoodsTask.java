package com.yunwa.aggregationmall.pojo.tb.po;

import java.util.Date;

public class TbGoodsTask {
    private Integer id;     //主键

    private String optName; //分类名

    private Date createTime;    //创建时间

    private Integer goodsNum;   //期望获取的商品数量

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName == null ? null : optName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }
}