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
import com.yunwa.aggregationmall.dao.pdd.PddOptIdMapper;
import com.yunwa.aggregationmall.dao.pdd.PromotionUrlMapper;
import com.yunwa.aggregationmall.pojo.pdd.dto.PddGoodsDto;
import com.yunwa.aggregationmall.pojo.pdd.po.PddGoods;
import com.yunwa.aggregationmall.pojo.pdd.po.PddOptId;
import com.yunwa.aggregationmall.pojo.pdd.po.PromotionUrl;
import com.yunwa.aggregationmall.pojo.pdd.vo.PddGoodsDocumentVO;
import com.yunwa.aggregationmall.provider.pdd.GoodsAPI;
import com.yunwa.aggregationmall.provider.pdd.GoodsPicUrlAPI;
import com.yunwa.aggregationmall.provider.pdd.PromotionUrlAPI;
import com.yunwa.aggregationmall.service.pdd.PddGoodsService;
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

    /*@Value("${pdd.client.id}")
    private String clientId;

    @Value("{pdd.client.secret}")
    private String clientSecret;*/

    //发送请求的客户端对象
    PopClient client = new PopHttpClient(PddConstantValues.clientId, PddConstantValues.clientSecret);
    //PopClient client = new PopHttpClient(clientId, clientSecret);

    /**
     * 获取拼多多商品数据
     * @pdd.ddk.goods.search
     * @return
     */
    /*public String goodsSearch(){
        for (int i=0; i<PddConstantValues.OPT_IDS.length; i++){
            for (int j=0; j<10; j++){
                //调用商品接口，获取商品DTO对象

                List<PddGoodsDto> list = goodsAPI.getGoodsData(PddConstantValues.OPT_IDS[i], j+1);

                //遍历商品DTO对象集合将销量转换为相应的数值类型、设置轮播图属性然后赋值给商品对象
                List<PddGoods> pddGoodsList = new ArrayList<PddGoods>();
                if (list != null){
                    for (PddGoodsDto goods : list){
                        //获取销量值
                        String getSales_tip = goods.getSales_tip();
                        //判断字符串包不包含“万”字
                        if (getSales_tip.indexOf("万") != -1){
                            //只保留“万”字前面的数据
                            //getSales_tip = getSales_tip.substring(0, getSales_tip.length() - 1);
                            getSales_tip = getSales_tip.split("万")[0];
                            //转换为相应的数值型
                            Integer sales_tip = (int)(Double.valueOf(getSales_tip) * 10000);
                            //再将数据转换为String类型并去掉小数点然后赋值给商品DTO对象
                            //getSales_tip = sales_tip.toString().split(".")[0];
                            goods.setSales_tip(sales_tip.toString());
                        }else if (getSales_tip.indexOf("+") != -1){
                            //没万字但是有“+”号的情况
                            getSales_tip = getSales_tip.replace("+", "");
                            goods.setSales_tip(getSales_tip);
                        }

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

                    //遍历商品集合，并生成对应的推广链接
                    this.getUrls(PddConstantValues.P_ID, pddGoodsList);
                }else {
                    continue;
                }
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 200);
        return JSON.toJSONString(map);
    }*/

    public String goodsSearch(){
        PddOptId pddOptId = pddOptIdMapper.selectIsGoing();
        Long optId = pddOptId.getOpt_id();      //类目id
        Integer total_page_count = pddOptId.getTotal_page_count(); //总页数
        Integer current_page = pddOptId.getCurrent_page();    //当前页

        for (; current_page<total_page_count; current_page++){
            //调用商品接口，获取商品DTO对象
            HashMap<String, Object> goodsData = goodsAPI.getGoodsData(optId, current_page);

            //接口调用错误的情况（如今日次数已用完）
            if ((int)goodsData.get("code") == 0){
                return "";
            }else if ((int)goodsData.get("code") == 1){ //无商品的情况
                //当前是在最后一个分类的情况
                if (optId == 2048){
                    //把各数据重置
                    pddOptIdMapper.update(optId);
                    //把第一个分类改为在进行中状态，然后爬取
                    pddOptIdMapper.updateStatus(58);
                    this.goodsSearch();
                }else { //当前不是在最后一个分类的情况
                    //把各数据重置
                    pddOptIdMapper.update(optId);
                    //查询出当前分类的id
                    Integer id = pddOptIdMapper.getId(optId);
                    //将当前分类的下一个分类改为进行中的状态，然后爬取
                    pddOptIdMapper.updateStatus(id+1);
                    this.goodsSearch();
                }
            }

            //获取商品DTO对象
            List<PddGoodsDto> list = (List<PddGoodsDto>) goodsData.get("data");

            //遍历商品DTO对象集合将销量转换为相应的数值类型、设置轮播图属性然后赋值给商品对象
            List<PddGoods> pddGoodsList = new ArrayList<PddGoods>();
            if (list != null){
                for (PddGoodsDto goods : list){
                    //获取销量值
                    String getSales_tip = goods.getSales_tip();
                    //判断字符串包不包含“万”字
                    if (getSales_tip.indexOf("万") != -1){
                        //只保留“万”字前面的数据
                        //getSales_tip = getSales_tip.substring(0, getSales_tip.length() - 1);
                        getSales_tip = getSales_tip.split("万")[0];
                        //转换为相应的数值型
                        Integer sales_tip = (int)(Double.valueOf(getSales_tip) * 10000);
                        //再将数据转换为String类型并去掉小数点然后赋值给商品DTO对象
                        //getSales_tip = sales_tip.toString().split(".")[0];
                        goods.setSales_tip(sales_tip.toString());
                    }else if (getSales_tip.indexOf("+") != -1){
                        //没万字但是有“+”号的情况
                        getSales_tip = getSales_tip.replace("+", "");
                        goods.setSales_tip(getSales_tip);
                    }

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

                //遍历商品集合，并生成对应的推广链接
                //this.getUrls(PddConstantValues.P_ID, pddGoodsList);
            }else {
                continue;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 200);
        return JSON.toJSONString(map);
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
     * 获取商品推广手机推广链接和手机推广短连接
     * @param p_id  推广位id
     * @param list 商品对象集合
     * @return
     */
    /*public void getUrls(String p_id, List<PddGoods> list){
        //获取商品推广链接对象集合
        List<PromotionUrl> promotion_url_list = promotionUrlAPI.getPromotionURL(p_id, list);

        //遍历插入到数据库
        if (promotion_url_list != null){
            for (int i=0; i<promotion_url_list.size(); i++){
                //设置商品id
                promotion_url_list.get(i).setGoods_id(list.get(i).getGoods_id());
                //执行插入操作
                promotionUrlMapper.insertUrl(promotion_url_list.get(i));
            }
        }

    }*/

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

}
