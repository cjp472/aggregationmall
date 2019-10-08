package com.yunwa.aggregationmall.pojo.pdd.dto;

public class OrderDto {
    private String order_sn;            //订单编号
    private Long goods_id;              //商品id
    private String goods_name;          //商品名称
    private Long goods_quantity;        //商品数量
    private Long goods_price;           //商品价格（分）
    private Long order_amount;          //订单价格（分）
    private Long promotion_rate;        //佣金比例 千分比
    private Long promotion_amount;      //佣金（分）
    private Integer order_status;       //订单状态
    private String order_status_desc;   //订单状态描述（ -1 未支付; 0-已支付；1-已成团；2-确认收货；3-审核成功；4-审核失败（不可提现）；5-已经结算；8-非多多进宝商品（无佣金订单）;10-已处罚）
    private Long order_create_time;     //订单创建时间
    private Long order_pay_time;        //订单支付时间
    private Long order_receive_time;    //订单确认收货时间
    private Integer type;               //订单类型：0：领券页面， 1： 红包页， 2：领券页， 3： 题页
    private Long auth_duo_id;           //多多客工具id
    private Long zs_duo_id;             //招商多多客id
    private Long url_last_generate_time;    //链接最后一次生产时间
    private String p_id;                 //推广位id

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public Long getGoods_quantity() {
        return goods_quantity;
    }

    public void setGoods_quantity(Long goods_quantity) {
        this.goods_quantity = goods_quantity;
    }

    public Long getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Long goods_price) {
        this.goods_price = goods_price;
    }

    public Long getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(Long order_amount) {
        this.order_amount = order_amount;
    }

    public Long getPromotion_rate() {
        return promotion_rate;
    }

    public void setPromotion_rate(Long promotion_rate) {
        this.promotion_rate = promotion_rate;
    }

    public Long getPromotion_amount() {
        return promotion_amount;
    }

    public void setPromotion_amount(Long promotion_amount) {
        this.promotion_amount = promotion_amount;
    }

    public Integer getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Integer order_status) {
        this.order_status = order_status;
    }

    public String getOrder_status_desc() {
        return order_status_desc;
    }

    public void setOrder_status_desc(String order_status_desc) {
        this.order_status_desc = order_status_desc;
    }

    public Long getOrder_create_time() {
        return order_create_time;
    }

    public void setOrder_create_time(Long order_create_time) {
        this.order_create_time = order_create_time;
    }

    public Long getOrder_pay_time() {
        return order_pay_time;
    }

    public void setOrder_pay_time(Long order_pay_time) {
        this.order_pay_time = order_pay_time;
    }

    public Long getOrder_receive_time() {
        return order_receive_time;
    }

    public void setOrder_receive_time(Long order_receive_time) {
        this.order_receive_time = order_receive_time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getAuth_duo_id() {
        return auth_duo_id;
    }

    public void setAuth_duo_id(Long auth_duo_id) {
        this.auth_duo_id = auth_duo_id;
    }

    public Long getZs_duo_id() {
        return zs_duo_id;
    }

    public void setZs_duo_id(Long zs_duo_id) {
        this.zs_duo_id = zs_duo_id;
    }

    public Long getUrl_last_generate_time() {
        return url_last_generate_time;
    }

    public void setUrl_last_generate_time(Long url_last_generate_time) {
        this.url_last_generate_time = url_last_generate_time;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }
}
