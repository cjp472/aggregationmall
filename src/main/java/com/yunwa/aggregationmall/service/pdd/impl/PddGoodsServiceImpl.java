package com.yunwa.aggregationmall.service.pdd.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsDetailRequest;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPromotionUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsDetailResponse;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPromotionUrlGenerateResponse;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsSearchResponse;
import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.dao.pdd.PddGoodsMapper;
import com.yunwa.aggregationmall.dao.pdd.PddGoodsTaskMapper;
import com.yunwa.aggregationmall.dao.pdd.PddOptIdMapper;
import com.yunwa.aggregationmall.dao.pdd.PromotionUrlMapper;
import com.yunwa.aggregationmall.pojo.pdd.dto.PddGoodsDto;
import com.yunwa.aggregationmall.pojo.pdd.po.PddGoods;
import com.yunwa.aggregationmall.pojo.pdd.po.PddGoodsTask;
import com.yunwa.aggregationmall.pojo.pdd.po.PddOptId;
import com.yunwa.aggregationmall.pojo.pdd.po.PromotionUrl;
import com.yunwa.aggregationmall.pojo.pdd.vo.PddGoodsDocumentVO;
import com.yunwa.aggregationmall.provider.pdd.GoodsAPI;
import com.yunwa.aggregationmall.provider.pdd.GoodsPicUrlAPI;
import com.yunwa.aggregationmall.provider.pdd.PromotionUrlAPI;
import com.yunwa.aggregationmall.service.pdd.PddGoodsService;
import com.yunwa.aggregationmall.utils.SalesTipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PddGoodsServiceImpl implements PddGoodsService {

    @Autowired
    private PddGoodsMapper pddGoodsMapper;
    @Autowired
    private PromotionUrlMapper promotionUrlMapper;
    @Autowired
    private GoodsAPI goodsAPI;
    @Autowired
    private PromotionUrlAPI promotionUrlAPI;
    @Autowired
    private GoodsPicUrlAPI goodsPicUrlAPI;
    @Autowired
    private PddOptIdMapper pddOptIdMapper;
    @Autowired
    private PddGoodsTaskMapper pddGoodsTaskMapper;
    @Autowired
    private SalesTipUtils salesTipUtils;

    /*@Value("${pdd.client.id}")
    private String clientId;

    @Value("{pdd.client.secret}")
    private String clientSecret;*/

    //发送请求的客户端对象
    PopClient client = new PopHttpClient(PddConstantValues.clientId, PddConstantValues.clientSecret);

    Logger logger = LoggerFactory.getLogger(PddGoodsServiceImpl.class);
    /**
     * 获取拼多多商品数据
     * @pdd.ddk.goods.search
     * @return
     */
    public String goodsSearch() {
        //查询商品任务表的最后一条记录
        PddGoodsTask pddGoodsTask = pddGoodsTaskMapper.selectLastTask();
        //获取商品类目id串
        String optIdString = pddGoodsTask.getOpt_id();
        //需要调用的接口次数 （获取期望的商品数量/100)
        Integer callNum = pddGoodsTask.getGoods_num() / 100;
        //id串转换成数组
        String[] optIds = optIdString.split(",");
        //每个类目需要调用的接口次数
        Integer optCallNum = (callNum / optIds.length) % 2 == 0 ? callNum / optIds.length : callNum / optIds.length + 1;
        //转换成Long类型
        ArrayList<Long> optIdList = new ArrayList<>();
        for (int k = 0; k < optIds.length; k++) {
            optIdList.add(Long.valueOf(optIds[k]));
        }
        logger.info("callNum={}, optCallNum={}", callNum, optCallNum);

        //遍历类目集合，爬取商品数据
        for (Long optId : optIdList) {
            //获取当前的类目信息
            PddOptId pddOptId = pddOptIdMapper.selectByOptId(optId);
            if (pddOptId != null) {
                Integer total_page_count = pddOptId.getTotal_page_count(); //总页数
                Integer current_page = pddOptId.getCurrent_page();    //当前页
                //遍历调用接口查询商品数据
                for (int j = 0; j < optCallNum; j++) {

                    if (current_page > total_page_count) {
                        //将当前类目的当前页置为1
                        pddOptIdMapper.updateCurrentPage(optId, 1);
                        break;   //进行下一个类目的遍历
                    }

                    //调用接口获取拼多多商品
                    HashMap<String, Object> goodsData = goodsAPI.getGoodsData(optId, current_page);
                    current_page++;

                    //接口调用错误的情况
                    if ((int) goodsData.get("code") == 0) {
                        continue;
                    } else if ((int)goodsData.get("code") == 1){    //无商品的情况
                        //重置该类目的数据，爬取下一个类目的数据
                        pddOptIdMapper.updateCurrentPage(optId, 1);
                        break;
                    }
                    //获取商品DTO对象
                    List<PddGoodsDto> list = (List<PddGoodsDto>) goodsData.get("data");
                    //遍历商品DTO对象集合将销量转换为相应的数值类型、设置轮播图属性然后赋值给商品对象
                    List<PddGoods> pddGoodsList = new ArrayList<PddGoods>();
                    if (list != null){

                        for (PddGoodsDto goods : list){
                            //获取销量值
                            String getSales_tip = goods.getSales_tip();
                            //转换为纯数字形式的字符串
                            String _getSales_tip = salesTipUtils.chengeToSalesNum(getSales_tip);
                            //设置商品销量
                            goods.setSales_tip(_getSales_tip);

                            //获取商品轮播图
                            String goods_gallery_urls = (String) this.getGoodsPicUrls(goods);
                            goods.setGoods_gallery_urls(goods_gallery_urls);

                            //将商品DTO对象复制给商品对象
                            PddGoods pddGoods = new PddGoods();
                            BeanUtils.copyProperties(goods,  pddGoods);

                            //sales_tip类型不匹配无法自动复制，这里手动复制
                            pddGoods.setSales_tip(Integer.parseInt(goods.getSales_tip()));
                            pddGoodsList.add(pddGoods);
                        }

                        //遍历商品集合，将推广位id赋给商品对象同时执行插入操作
                        for (PddGoods pddGoods : pddGoodsList){
                            pddGoods.setP_id(PddConstantValues.P_ID);

                            //设置商品的真实价格(原价减优惠券)
                            Long real_price = pddGoods.getMin_group_price() - pddGoods.getCoupon_discount();
                            pddGoods.setReal_price(real_price);

                            //将商品数据插入到数据库
                            pddGoodsMapper.insertPddGoodsData(pddGoods);
                        }
                        logger.info("number={}", pddGoodsList.size());
                    }//TODO:查询不到数据就直接将当前分类的 `current_page` 字段重置，然后开始下一个循环的遍历
                }
                //一个分类今天的次数爬取完，更新当前页字段
                pddOptIdMapper.updateCurrentPage(optId, current_page);
            }
        }
        return "ok";
    }


    /**
     *获取商品轮播图，pdd.PddGoods.detail.get
     * @param pddGoodsDto 封装的商品对象
     */
    public String getGoodsPicUrls(PddGoodsDto pddGoodsDto) {
        //获取轮播图链接数组
        JSONArray urlsArray = goodsPicUrlAPI.getGoodsPicURL(pddGoodsDto);

        //遍历轮播图链接数组，得到每张轮播图链接，将其拼装成一个长字符串
        String picUrls = "";
        if (urlsArray != null){
            for(int i=0; i<urlsArray.size(); i++){
                picUrls += urlsArray.getString(i);
                picUrls += ";";
            }
        }
        return  picUrls;
    }

    /**
     * 删除商品
     * @return  有无删除成功
     */
    @Override
    public boolean delPddGoods() {
        if(pddGoodsMapper.delPddGoods() > 0){
            return true;
        }
        return false;
    }

    /**
     * 首页获取商品列表
     * @param map   参数集合
     * @return  商品集合
     */
    @Override
    public PageInfo<PddGoods> getGoodsList(int pageNum, HashMap<String, Object> map) {
        PageHelper.startPage(pageNum, PddConstantValues.pageSize);
        List<PddGoods> list =  pddGoodsMapper.selectByPage(map);
        //将得到的商品对象封装成
        //返回的一个PageInfo,包含了分页的所有信息
        return new PageInfo<PddGoods>(list);
    }

    /**
     * 获取商品详情
     * @param goods_id
     * @return
     */
    @Override
    public PddGoods getGoodsDetil(long goods_id) {
        return pddGoodsMapper.showGoodsDetil(goods_id);
    }

    /**
     * 获取商品文案
     * @param goods_id  商品id
     * @return
     */
    @Override
    public PddGoodsDocumentVO getGoodsDocument(long goods_id) {
        return pddGoodsMapper.getGoodsDocument(goods_id);
    }

    /**
     * 删除优惠券过期的商品
     */
    @Override
    public void deleteOverdueGoods() {
        pddGoodsMapper.deleteOverdueGoods();
    }


}
