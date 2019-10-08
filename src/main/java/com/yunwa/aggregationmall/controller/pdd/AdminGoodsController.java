package com.yunwa.aggregationmall.controller.pdd;

import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.pojo.pdd.dto.OrderDto;
import com.yunwa.aggregationmall.provider.pdd.OrderAPI;
import com.yunwa.aggregationmall.service.pdd.OrderService;
import com.yunwa.aggregationmall.service.pdd.PddGoodsService;
import com.yunwa.aggregationmall.service.pdd.PromotionUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class AdminGoodsController {

    @Autowired
    private PddGoodsService pddGoodsService;

    @Autowired
    private PromotionUrlService promotionUrlService;

    @Autowired
    private OrderAPI orderAPI;

    @Autowired
    private OrderService orderService;
    /**
     * 根据商商品标签ID查询拼多多商品信息，插入到数据库
     *
     * @param opt_id 商品标签类目ID
     * @param page_size 每页商品数量
     * @param page  默认值1，商品分页数
     * @param p_id  商品推广位id
     * @return
     */
    @PostMapping(value = "/searchPddGoods")
    public Map<Object, Object> getGoodsInfo(@RequestParam(value = "opt_id") Long opt_id,
                               @RequestParam(value = "page_size", required = false) Integer page_size,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam("p_id") String p_id){
        HashMap<Object, Object> map = new HashMap<>();
        if ( pddGoodsService.goodsSearch(opt_id, page_size, p_id, page) != null){
            map.put("code", "200");
        }
        map.put("code", "500");
        return map;
    }

    //插入1000条测试数据
    @RequestMapping("/insert")
    public String insert(Long opt_id, Integer page_size, String p_id){
        for (int i=0; i<10; i++){
            this.getGoodsInfo(opt_id, page_size, (i+1), p_id);
        }
        return "ok";
    }

    /**
     * 获取淘宝商品数据
     * @return
     */
    @RequestMapping("/getGoodsData")
    public String getGoodsData(){
        for (int i=0; i<PddConstantValues.opt_ids.length; i++){
            for (int j=0; j<PddConstantValues.opt_ids[i].length; j++){
                for (int k=0; k<100; k++){
                    this.getGoodsInfo(PddConstantValues.opt_ids[i][j], 10, (k+1), PddConstantValues.p_id);
                }
            }
        }
        return "ok";
    }


    /**
     * 删除拼多多商品
     */
    @PostMapping(value = "/delPddGoods")
    public Map<Object, Object> delPddGoods(){
        HashMap<Object, Object> map = new HashMap<>();
        if(pddGoodsService.delPddGoods()){
            map.put("code", "200");
        }
        map.put("code", "500");
        return map;
    }

    /**
     * 插入订单记录
     * @param order_sn  订单号
     * @return
     */
    /*@RequestMapping(value = "/insertOrderData")
    public Map<String, Object> insertOrderData(@RequestParam("order_sn") String order_sn){
        //获取到订单传输对象
        OrderDto orderDto = orderAPI.getOrderDetail(order_sn);
        boolean flag = orderService.insertOrderData(orderDto);
        HashMap<String, Object> map = new HashMap<>();
        if (flag){
            map.put("200", 200);
        }else {
            map.put("500", 500);
        }
        return map;
    }*/

    /**
     * 判断是否是通过聚合商城下的订单
     * 是就插入订单记录到数据库
     * @param order_sn  订单号
     * @return
     */
   /* @RequestMapping(value = "/judgeOrder")
    public Map<String, Object> judgeOrder(@RequestParam("order_sn") String order_sn){
        //获取到订单传输对象
        OrderDto orderDto = orderAPI.getOrderDetail(order_sn);
        HashMap<String, Object> map = new HashMap<>();
        if (orderDto.getP_id() != null){
            map.put("isTrue", "yes");
            this.insertOrderData(order_sn);
        }else {
            map.put("isTrue", "no");
        }
        return map;
    }*/

    /**
     * 绑定订单
     * @param order_sn  订单号
     * @return
     */
    @RequestMapping(value = "/orderBind")
    public Map<String, Object> orderBind(@RequestParam("order_sn") String order_sn){
        return (Map<String, Object>) orderService.orderBind(order_sn);
    }

}
