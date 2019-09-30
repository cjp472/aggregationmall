package com.yunwa.taobaoke.service.impl;

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
import com.yunwa.taobaoke.constant.PddConstantValues;
import com.yunwa.taobaoke.dao.PddGoodsMapper;
import com.yunwa.taobaoke.dao.PromotionUrlMapper;
import com.yunwa.taobaoke.pojo.dto.PddGoodsDto;
import com.yunwa.taobaoke.pojo.po.PddGoods;
import com.yunwa.taobaoke.pojo.po.PromotionUrl;
import com.yunwa.taobaoke.pojo.vo.PddGoodsDocumentVo;
import com.yunwa.taobaoke.service.PddGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PddGoodsServiceImpl implements PddGoodsService {

    @Autowired
    private PddGoodsMapper pddGoodsMapper;

    @Autowired
    private PromotionUrlMapper promotionUrlMapper;

    //发送请求的客户端对象
    PopClient client = new PopHttpClient(PddConstantValues.clientId, PddConstantValues.clientSecret);

    /**
     * @pdd.ddk.goods.search
     *
     * @param opt_id 商品标签类目id
     * @param page_size 默认100，每页商品数量
     * @param p_id  商品推广id
     * @return
     */
    public List<PddGoodsDto> goodsSearch(Long opt_id, Integer page_size, String p_id, Integer page){
        PddDdkGoodsSearchRequest request = new PddDdkGoodsSearchRequest();

        if (opt_id != null){
            request.setOptId(opt_id);
        }
        if (page_size != null){
            request.setPageSize(page_size);
        }
        if (page != null){
            request.setPage(page);
        }

        PddDdkGoodsSearchResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String goodsData = JsonUtil.transferToJson(response);
        JSONObject m1 = new JSONObject();
        m1 = JSON.parseObject(goodsData);       //将json文本转化为jsonobject
        String goods_search_response = m1.getString("goods_search_response");   //获取goods_search_response
        m1 = JSON.parseObject(goods_search_response);           //将json文本转化为jsonobject
        String goods_list = m1.getString("goods_list");    //获取商品信息列表

        //封装成商品DTO对象
        List<PddGoodsDto> list = JSONArray.parseArray(goods_list, PddGoodsDto.class);

        //遍历商品DTO对象集合将销量转换为相应的数值类型、设置轮播图属性然后赋值给商品对象
        List<PddGoods> pddGoodsList = new ArrayList<PddGoods>();
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
            pddGoods.setP_id(p_id);

            //设置商品的真实价格(原价减优惠券)
            Long real_price = pddGoods.getMin_group_price() - pddGoods.getCoupon_discount();
            pddGoods.setReal_price(real_price);

            //将商品数据插入到数据库
            pddGoodsMapper.insertPddGoodsData(pddGoods);
        }

        //遍历商品集合,将推广位id赋值给商品对象，并生成对应的推广链接
        this.getUrls(p_id, pddGoodsList);

        return list;
    }

    /**
     *获取商品轮播图，pdd.PddGoods.detail.get
     * @param pddGoodsDto 封装的商品对象
     */
    public String getGoodsPicUrls(PddGoodsDto pddGoodsDto) {
        PddDdkGoodsDetailRequest request = new PddDdkGoodsDetailRequest();

        List<Long> goodsIdList = new ArrayList<Long>();
        goodsIdList.add(pddGoodsDto.getGoods_id());
        request.setGoodsIdList(goodsIdList);

        PddDdkGoodsDetailResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String goodsDetails = JsonUtil.transferToJson(response);

        JSONObject jsonObject = new JSONObject();
        jsonObject = JSON.parseObject(goodsDetails);        //将json文本转化为jsonobject
        String goods_details = jsonObject.getString("goods_detail_response");   //获取第二层的商品详情回复数据
        jsonObject = JSON.parseObject(goods_details);       //将json文本转化为jsonobject
        JSONArray jsonArray = jsonObject.getJSONArray("goods_details");     //获取goods_details数组

        //获取数组的第一个元素，即商品详情
        JSONObject goodsInfo =  jsonArray.getJSONObject(0);
        String goods_gallery_urls = goodsInfo.getString("goods_gallery_urls");    //获取轮播图数组
        JSONArray urlsArray = JSONArray.parseArray(goods_gallery_urls);     //转换成数组

        //遍历轮播图数组，得到每张轮播图，将其拼装成一个字符串
        String picUrls = "";
        for(int i=0; i<urlsArray.size(); i++){
            picUrls += urlsArray.getString(i);
            picUrls += ";";
        }
        return  picUrls;
    }

    /**
     * 获取商品推广手机推广链接和手机推广短连接
     * @param p_id  推广位id
     * @param list 商品对象集合
     * @return
     */
    public void getUrls(String p_id, List<PddGoods> list){
        PddDdkGoodsPromotionUrlGenerateRequest request = new PddDdkGoodsPromotionUrlGenerateRequest();
        List<Long> goods_id_list = new ArrayList<>();

        //遍历获取商品id
        for (PddGoods goods : list){
            goods_id_list.add(goods.getGoods_id());
        }
        request.setPId(p_id);
        request.setGoodsIdList(goods_id_list);
        PddDdkGoodsPromotionUrlGenerateResponse response = null;
        try {
            response = client.syncInvoke(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response_urls = JsonUtil.transferToJson(response);

        JSONObject jsonObject = new JSONObject();
        jsonObject = JSON.parseObject(response_urls);        //将json文本转化为jsonobject
        String goods_promotion_url_generate_response = jsonObject.getString("goods_promotion_url_generate_response");
        jsonObject = JSON.parseObject(goods_promotion_url_generate_response);
        String  goods_promotion_url_list = jsonObject.getString("goods_promotion_url_list");

        //封装为链接对象集合
        List<PromotionUrl> promotion_url_list = JSONArray.parseArray(goods_promotion_url_list, PromotionUrl.class);

        //遍历插入到数据库
        for (int i=0; i<promotion_url_list.size(); i++){
            //设置商品id
            promotion_url_list.get(i).setGoods_id(list.get(i).getGoods_id());
            //执行插入操作
            promotionUrlMapper.insertUrl(promotion_url_list.get(i));
        }

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
    public PddGoodsDocumentVo getGoodsDocument(long goods_id) {
        return pddGoodsMapper.getGoodsDocument(goods_id);
    }

}
