package com.yunwa.aggregationmall.pojo.tb.po;

import java.util.Date;

public class TbOpt {
    private Integer id;     //主键

    private String optName; //类目名

    private Long totalGoodsCount;    //商品总数

    private Long totalPageCount;     //商品总页数

    private Long currentPage;        //爬取的当前页

    private Date createTime;            //创建这条记录的时间

    private Date modifyTime;            //修改这条记录的时间

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

    public Long getTotalGoodsCount() {
        return totalGoodsCount;
    }

    public void setTotalGoodsCount(Long totalGoodsCount) {
        this.totalGoodsCount = totalGoodsCount;
    }

    public Long getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Long totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}