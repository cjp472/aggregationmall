package com.yunwa.aggregationmall.pojo.pdd.po;

public class PddOptId {
    private Integer id;     //主键

    private Long opt_id;    //类目id

    private Integer total_goods_count;     //商品总数量

    private Integer total_page_count;      //总页数

    private Integer current_page;          //当前页

    private Integer is_going;           //该是否在爬取商品中，1是，0不是

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(Long opt_id) {
        this.opt_id = opt_id;
    }

    public Integer getTotal_goods_count() {
        return total_goods_count;
    }

    public void setTotal_goods_count(Integer total_goods_count) {
        this.total_goods_count = total_goods_count;
    }

    public Integer getTotal_page_count() {
        return total_page_count;
    }

    public void setTotal_page_count(Integer total_page_count) {
        this.total_page_count = total_page_count;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public Integer getIs_going() {
        return is_going;
    }

    public void setIs_going(Integer is_going) {
        this.is_going = is_going;
    }
}