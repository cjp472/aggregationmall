package com.yunwa.aggregationmall.pojo.tb.po;

import java.util.Date;

public class TbSearchPara {
    private Integer id;     //主键

    private Long startTkRate;    //商品筛选-淘客佣金比率下限。如：1234表示12.34%

    private String sort;    //排序_des（降序），排序_asc（升序），销量（total_sales），淘客佣金比率（tk_rate）， 累计推广量（tk_total_sales），总支出佣金（tk_total_commi），价格（price）

    private Long adzoneId;  //推广位id

    private Boolean needFreeShipment;   //商品筛选-是否包邮。true表示包邮，false或不设置表示不限

    private Boolean isTmall;    //商品筛选-是否天猫商品。true表示属于天猫商品，false或不设置表示不限

    private Boolean hasCoupon;  //优惠券筛选-是否有优惠券。true表示该商品有优惠券，false或不设置表示不限

    private Date createTime;    //创建这条记录的时间

    private Date modifyTime;    //修改这条记录的时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getStartTkRate() {
        return startTkRate;
    }

    public void setStartTkRate(Long startTkRate) {
        this.startTkRate = startTkRate;
    }

    public Boolean getTmall() {
        return isTmall;
    }

    public void setTmall(Boolean tmall) {
        isTmall = tmall;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public Long getAdzoneId() {
        return adzoneId;
    }

    public void setAdzoneId(Long adzoneId) {
        this.adzoneId = adzoneId;
    }

    public Boolean getNeedFreeShipment() {
        return needFreeShipment;
    }

    public void setNeedFreeShipment(Boolean needFreeShipment) {
        this.needFreeShipment = needFreeShipment;
    }

    public Boolean getIsTmall() {
        return isTmall;
    }

    public void setIsTmall(Boolean isTmall) {
        this.isTmall = isTmall;
    }

    public Boolean getHasCoupon() {
        return hasCoupon;
    }

    public void setHasCoupon(Boolean hasCoupon) {
        this.hasCoupon = hasCoupon;
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