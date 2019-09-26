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
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsDetailResponse;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsSearchResponse;
import com.yunwa.taobaoke.constant.PddConstantValues;
import com.yunwa.taobaoke.dao.PddGoodsMapper;
import com.yunwa.taobaoke.pojo.PddGoods;
import com.yunwa.taobaoke.service.PddGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PddGoodsServiceImpl implements PddGoodsService {

    @Autowired
    private PddGoodsMapper pddGoodsMapper;

    //发送请求的客户端对象
    PopClient client = new PopHttpClient(PddConstantValues.clientId, PddConstantValues.clientSecret);

    /**
     * @pdd.ddk.goods.search
     * @param keyword 关键字
     * @param opt_id 商品标签类目id
     * @param page_size 默认100，每页商品数量
     * @return
     */
    public List<PddGoods> goodsSearch(String keyword, Long opt_id, Integer page_size){
        PddDdkGoodsSearchRequest request = new PddDdkGoodsSearchRequest();

        if (keyword != null){
            request.setKeyword(keyword);
        }
        if (opt_id != null){
            request.setOptId(opt_id);
        }
        if (page_size != null){
            request.setPageSize(page_size);
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

        List<PddGoods> list = JSONArray.parseArray(goods_list, PddGoods.class);

        //遍历商品集合，将轮播图数据赋给商品对象
        List<PddGoods> pddGoodsList = new ArrayList<PddGoods>();
        for (PddGoods pddGoods : list){
            String goods_gallery_urls = (String) this.getGoodsPicUrls(pddGoods);   //获取商品轮播图
            pddGoods.setGoods_gallery_urls(goods_gallery_urls);
            pddGoodsList.add(pddGoods);
            System.out.println(pddGoods);
        }

        //遍历商品集合，执行插入操作
        for (PddGoods pddGoods : pddGoodsList){
            pddGoodsMapper.insertPddGoodsData(pddGoods);
        }
        return pddGoodsList;
    }

    /**
     *获取商品轮播图，pdd.PddGoods.detail.get
     * @param pddGoods 封装的商品对象
     */
    public Object getGoodsPicUrls(PddGoods pddGoods) {
        PddDdkGoodsDetailRequest request = new PddDdkGoodsDetailRequest();

        List<Long> goodsIdList = new ArrayList<Long>();
        goodsIdList.add(pddGoods.getGoods_id());
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
        //返回的一个PageInfo,包含了分页的所有信息
        return new PageInfo<PddGoods>(list);
    }

    @Override
    public PddGoods showGoodsDetil(long goods_id) {
        return pddGoodsMapper.showGoodsDetil(goods_id);
    }

}
