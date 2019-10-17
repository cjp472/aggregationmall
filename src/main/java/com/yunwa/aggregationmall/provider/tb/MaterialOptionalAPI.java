package com.yunwa.aggregationmall.provider.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.yunwa.aggregationmall.constant.TbkConstantValues;
import com.yunwa.aggregationmall.pojo.tb.po.TbGoods;
import com.yunwa.aggregationmall.pojo.tb.po.TbGoodsWithBLOBs;
import com.yunwa.aggregationmall.pojo.tb.po.TbOpt;
import com.yunwa.aggregationmall.utils.NumberConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2019/10/11.
 *
 * @author yueyang
 */
// 淘宝客-推广者-物料搜索API
@Component
public class MaterialOptionalAPI {
    @Autowired
    private TCommandAPI tCommandAPI;
    @Autowired
    private NumberConvertUtil numberConvertUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    TaobaoClient client = new DefaultTaobaoClient(TbkConstantValues.GET__URL, TbkConstantValues.APP_KEY, TbkConstantValues.APP_SERCET);

    //获取商品数据
    public List<TbGoodsWithBLOBs> getGoodsData(Long pageNo, String keyword, Long startTkRate, String sort, Long adzoneId,
                               Boolean needFreeShipment, Boolean isTmall, Boolean hasCoupon){
        TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
        req.setPageNo(pageNo);
        req.setQ(keyword);
        req.setPageSize(100L);
        req.setStartTkRate(startTkRate);
        req.setSort(sort);
        req.setAdzoneId(adzoneId);
        req.setNeedFreeShipment(needFreeShipment);
        req.setIsTmall(isTmall);
        req.setHasCoupon(hasCoupon);
        List<TbGoodsWithBLOBs> list = null;
        String responseBody = null;
        try {
            TbkDgMaterialOptionalResponse rsp = client.execute(req);
            responseBody = rsp.getBody();
        } catch (ApiException e) {
            e.printStackTrace();
            logger.info("获取商品接口调用异常,返回的数据：{}", responseBody);
            return null;
        }
        try {
            if (responseBody != null){
                //解析json字符串
                JSONObject jsonObject = JSON.parseObject(responseBody);
                JSONObject tbk_dg_material_optional_response = jsonObject.getJSONObject("tbk_dg_material_optional_response");
                JSONObject result_list = tbk_dg_material_optional_response.getJSONObject("result_list");
                JSONArray map_data = result_list.getJSONArray("map_data");
                //将数据封装到商品对象里
                list = JSONArray.parseArray(map_data.toJSONString(), TbGoodsWithBLOBs.class);
                //遍历json数组获取轮播图
                for(int i=0;i<map_data.size();i++){
                    //获取轮播图数组
                    Object[] objects = map_data.getJSONObject(i).getJSONObject("small_images").getJSONArray("string").toArray();
                    //转换成字符串
                    if (objects != null){
                        String picURLs = Arrays.toString(objects);
                        list.get(i).setSmallImages(picURLs);
                    }
                }
                //设置其他值
                for (TbGoodsWithBLOBs TbGoods : list){
                    //商品短标题
                    String shortTitle = TbGoods.getShortTitle();
                    //商品主图
                    String pictUrl = TbGoods.getPictUrl();
                    //链接-宝贝+券二合一页面链接
                    String couponShareUrl = "https:"+TbGoods.getCouponShareUrl();
                    //商品优惠券面额
                    Double couponAmount = Double.parseDouble(TbGoods.getCouponAmount());
                    //获取商品折扣价
                    Double zkFinalPrice = Double.parseDouble(TbGoods.getZkFinalPrice());
                    //设置商品券后价(保留两位小数)
                    Double realPrice = numberConvertUtil.keepTwoDecimals(zkFinalPrice - couponAmount);
                    TbGoods.setRealPrice(realPrice);
                    //获取商品佣金比例
                    Double commissionRate = Double.parseDouble(TbGoods.getCommissionRate()) / 10000;
                    //设置总佣金（保留两位小数）
                    Double totalPromotion = numberConvertUtil.keepTwoDecimals(TbGoods.getRealPrice() * commissionRate);
                    TbGoods.setTotalPromotion(totalPromotion);
                    //获取商品淘口令
                    String tCommand = tCommandAPI.createTCommand(couponShareUrl, shortTitle, pictUrl);
                    //设置淘口令
                    TbGoods.settCommand(tCommand);
                }
            }
        } catch (Exception e){
            logger.info("接口返回的数据异常,返回的数据：{}", responseBody);
            return null;
        }
        return list;
    }

    //获取商品分类的数据
    public TbOpt getGoodsOptData(String keyword, Long startTkRate, String sort, Long adzoneId,
                                  Boolean needFreeShipment, Boolean isTmall, Boolean hasCoupon){
        TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
        req.setQ(keyword);
        req.setStartTkRate(startTkRate);
        req.setSort(sort);
        req.setAdzoneId(adzoneId);
        req.setNeedFreeShipment(needFreeShipment);
        req.setIsTmall(isTmall);
        req.setHasCoupon(hasCoupon);
        Long total_results = null;
        String responseBody = null;
        try {
            TbkDgMaterialOptionalResponse rsp = client.execute(req);
            responseBody = rsp.getBody();
            JSONObject jsonObject = JSON.parseObject(responseBody);
            JSONObject tbk_dg_material_optional_response = jsonObject.getJSONObject("tbk_dg_material_optional_response");
            total_results = tbk_dg_material_optional_response.getLong("total_results");
        } catch (ApiException e) {
            e.printStackTrace();
            logger.info("获取商品接口调用异常，分类名:{}, 返回的数据：{}", keyword, responseBody);
            return null;
        }

        TbOpt tbOpt = new TbOpt();
        tbOpt.setOptName(keyword);
        tbOpt.setTotalGoodsCount(total_results);
        return tbOpt;
    }

}
