package com.yunwa.aggregationmall.controller.pdd;

import com.github.pagehelper.PageInfo;
import com.yunwa.aggregationmall.pojo.pdd.po.PddGoods;
import com.yunwa.aggregationmall.pojo.pdd.po.PromotionUrl;
import com.yunwa.aggregationmall.pojo.pdd.vo.PddGoodsDocumentVO;
import com.yunwa.aggregationmall.pojo.pdd.vo.PddPromotionAmountVO;
import com.yunwa.aggregationmall.provider.pdd.SuoImAPI;
import com.yunwa.aggregationmall.service.pdd.OrderService;
import com.yunwa.aggregationmall.service.pdd.PddGoodsService;
import com.yunwa.aggregationmall.service.pdd.PromotionUrlService;
import com.yunwa.aggregationmall.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserGoodsController {

    @Autowired
    private PddGoodsService pddGoodsService;

    @Autowired
    private PromotionUrlService promotionUrlService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UrlUtils urlUtils;
    /**
     * 首页查询商品
     * @param pageNum   当前页
     * @param opt_name  商品标签ID
     * @param sort_type 排序方式
     * @param keyword   关键字
     * @return  商品信息集合
     */
    @RequestMapping("/getGoodsList")
    public PageInfo<PddGoods> getGoodsList(@RequestParam(value = "pageNum", required = false) int pageNum,
                                           @RequestParam(value = "opt_name", required = false) String opt_name,
                                           @RequestParam(value = "sort_type", required = false) String sort_type,
                                           @RequestParam(value = "keyword", required = false) String keyword){
        HashMap<String, Object> map = new HashMap<>();
        if (opt_name != null){
            map.put("opt_name", opt_name);
        }
        if (sort_type != null){
            map.put("sort_type", sort_type);
        }
        if (keyword != null){
            map.put("keyword", keyword);
        }
        System.out.println(map.toString());
        return pddGoodsService.getGoodsList(pageNum, map);
    }

    /**
     * 获取拼多多商品详情
     * @param goods_id  商品id
     * @return  商品详细信息
     */
    @RequestMapping("/showGoodsDetil")
    public PddGoods showGoodsDetil(long goods_id){
        return pddGoodsService.getGoodsDetil(goods_id);
    }

    /**
     * 查询商品推广链接对象
     * @param goods_id  商品id
     * @return  链接对象
     */
    @RequestMapping("/getPromotionUrl")
    public PromotionUrl getPromotionUrl(long goods_id){
        return promotionUrlService.getPromotionUrl(goods_id);
    }

    /**
     * 获取商品文案
     * @param goods_id  商品id
     * @return 文案对象
     */
    @RequestMapping("/getGoodsDocument")
    public PddGoodsDocumentVO getGoodsDocument(long goods_id){
        return pddGoodsService.getGoodsDocument(goods_id);
    }

    /**
     * 绑定订单
     * @param order_sn  订单号
     * @param user_id   用户id
     * @return
     */
    @PostMapping(value = "/orderBind")
    public String orderBind(@RequestParam("order_sn") String order_sn,
                            @RequestParam("user_id") String user_id){
        return orderService.orderBind(order_sn, user_id);
    }

    /**
     * 用户提现
     * @param user_id   用户id
     * @return  返佣金额
     */
    @PostMapping("/getMoney")
    public String getMoney(@RequestParam("user_id") String user_id){
        //获取返佣金额
        return orderService.getMoney(user_id);
    }

    /**
     * 根据用户输入的关键词 返回一个短连接
     * @param keyword  关键词（买XX）
     * @return
     */
    @PostMapping("buySomething")
    public Object buySomething(@RequestParam("keyword") String keyword){
        //返回一个短连接
        return urlUtils.getShortURL(keyword);
    }
}
