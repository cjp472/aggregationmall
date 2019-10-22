package com.yunwa.aggregationmall.controller.tb;

import com.yunwa.aggregationmall.common.RespBean;
import com.yunwa.aggregationmall.pojo.tb.po.TbGoodsWithBLOBs;
import com.yunwa.aggregationmall.provider.tb.MaterialOptionalAPI;
import com.yunwa.aggregationmall.provider.tb.TCommandAPI;
import com.yunwa.aggregationmall.service.tb.TbGoodsService;
import com.yunwa.aggregationmall.service.tb.TbOptService;
import com.yunwa.aggregationmall.service.tb.TbOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sys")
public class AdminGoodsController {
    @Autowired
    private TbGoodsService tbGoodsService;
    @Autowired
    private TbOptService tbOptService;
    @Autowired
    private MaterialOptionalAPI materialOptionalAPI;
    @Autowired
    private TCommandAPI tCommandAPI;
    @Autowired
    private TbOrderService tbOrderService;

    /**
     * 获取选品库id
     * @return
     */
    @GetMapping(value = "/tbGoodsSearch")
    public String tbGoodsSearch(){
        tbGoodsService.tbGoodsSearch();
        return "";
    }

    /**
     * 获取淘宝商品分类信息
     * @return
     */
    /*@GetMapping(value = "/getGoodsOptInfo")
    public String getGoodsOptInfo(){
        tbOptService.getGoodsOptInfo();
        return "ok";
    }*/


    //测试获取商品数据
    @GetMapping(value = "/testGetGoods")
    public List<TbGoodsWithBLOBs> testGetGoods(Long pageNo, String keyword, Long startTkRate, String sort, Long adzoneId,
                                               Boolean needFreeShipment, Boolean isTmall, Boolean hasCoupon){
        return materialOptionalAPI.getGoodsData(pageNo, keyword, startTkRate, sort, adzoneId, needFreeShipment,
                isTmall, hasCoupon);
    }

    /**
     * 获取淘宝商品信息
     * @return
     */
    @GetMapping(value = "/getTbGoodsInfo")
    public RespBean getTbGoodsInfo(){
        tbGoodsService.getTbGoodsInfo();
        return RespBean.ok("获取商品数据成功！");
    }

    /**
     * 将该用户的已返佣的订单移入历史表
     * @return
     */
    @GetMapping(value = "/moveToFinished")
    public RespBean moveToFinished(String userId){
        if (tbOrderService.moveToFinished(userId)){
            return RespBean.ok("订单已移入历史表！");
        }
        return RespBean.error("订单移入历史表失败！");
    }

}
