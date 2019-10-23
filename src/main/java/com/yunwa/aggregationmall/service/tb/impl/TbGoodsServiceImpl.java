package com.yunwa.aggregationmall.service.tb.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunwa.aggregationmall.constant.TbkConstantValues;
import com.yunwa.aggregationmall.dao.tb.TbGoodsMapper;
import com.yunwa.aggregationmall.dao.tb.TbGoodsTaskMapper;
import com.yunwa.aggregationmall.dao.tb.TbOptMapper;
import com.yunwa.aggregationmall.dao.tb.TbSearchParaMapper;
import com.yunwa.aggregationmall.pojo.tb.po.TbGoodsTask;
import com.yunwa.aggregationmall.pojo.tb.po.TbGoodsWithBLOBs;
import com.yunwa.aggregationmall.pojo.tb.po.TbOpt;
import com.yunwa.aggregationmall.pojo.tb.po.TbSearchPara;
import com.yunwa.aggregationmall.provider.tb.MaterialOptionalAPI;
import com.yunwa.aggregationmall.service.tb.TbGoodsService;
import com.yunwa.aggregationmall.service.tb.TbOptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.helpers.ParseConversionEventImpl;
import java.util.HashMap;
import java.util.List;

@Service
public class TbGoodsServiceImpl implements TbGoodsService {
    @Autowired
    private MaterialOptionalAPI materialOptionalAPI;
    @Autowired
    private TbSearchParaMapper tbSearchParaMapper;
    @Autowired
    private TbGoodsTaskMapper tbGoodsTaskMapper;
    @Autowired
    private TbOptMapper tbOptMapper;
    @Autowired
    private TbGoodsMapper tbGoodsMapper;
    @Autowired
    private TbOptService tbOptService;

    @Override
    public void getTbGoodsInfo() {
        //获取商品查询条件
        TbSearchPara tbSearchPara = tbSearchParaMapper.getLastRecord();
        Long startTkRate = tbSearchPara.getStartTkRate();//佣金比率下限
        String sort = tbSearchPara.getSort();   //排序方式
        Long adzoneId = tbSearchPara.getAdzoneId(); //推广位id
        Boolean needFreeShipment = tbSearchPara.getNeedFreeShipment();  //是否包邮
        Boolean isTmall = tbSearchPara.getIsTmall();    //是否天猫商品
        Boolean hasCoupon = tbSearchPara.getHasCoupon();    //是否有优惠券

        //查询商品任务表的最后一条记录
        TbGoodsTask tbGoodsTask = tbGoodsTaskMapper.getLastRecord();
        //期望获取的商品的分类名串
        String optNameString = tbGoodsTask.getOptName();
        //转换成分类名数组
        String[] optNames = optNameString.split(",");
        //更新分类信息
        tbOptService.updateByOptName(optNames, startTkRate, sort, adzoneId, needFreeShipment,
                isTmall, hasCoupon);
        //获取期望的商品数量
        Integer goodsNum = tbGoodsTask.getGoodsNum();
        //接口总调用次数
        Integer totalCallNum = (goodsNum / 100);
        //每个分类调用次数
        Integer callNum = (totalCallNum / optNames.length) % 2 == 0 ? totalCallNum / optNames.length :
                totalCallNum / optNames.length + 1;

        //遍历分类名，获取商品数据
        for (int i=0; i<optNames.length; i++){
            //获取当前分类的信息
            TbOpt tbOpt = tbOptMapper.selectByOptName(optNames[i]);
            Long currentPage = tbOpt.getCurrentPage();   //爬取的当前页
            Long totalPageCount = tbOpt.getTotalPageCount();    //总页数
            String categoryIdString = tbOpt.getCategoryId();    //分类id串
            //遍历调用接口查询商品数据
            for (int j=0; j<callNum; j++){
                if (currentPage > totalPageCount) {
                    //将当前类目的当前页置为1
                    tbOptMapper.updateCurrentPage(optNames[i], 1L);
                    break;   //进行下一个类目的遍历
                }
                //调用接口获取淘宝商品
                List<TbGoodsWithBLOBs> list = materialOptionalAPI.getGoodsData(currentPage, categoryIdString, startTkRate, sort, adzoneId,
                        needFreeShipment, isTmall, hasCoupon);
                currentPage++;
                if (list != null){
                    //遍历商品集合插入到数据库
                    for (TbGoodsWithBLOBs tbGoods : list){
                        tbGoodsMapper.insert(tbGoods);
                    }
                }
            }
            //当前这个分类的次数爬取完，更新`当前页`字段
            tbOptMapper.updateCurrentPage(optNames[i], currentPage);
        }
    }

    /**
     * 首页商品查询
     * @param pageNum   当前页
     * @param map   参数集合
     * @return 商品集合
     */
    @Override
    public PageInfo<TbGoodsWithBLOBs> getGoodsList(int pageNum, HashMap<String, Object> map) {
        PageHelper.startPage(pageNum, TbkConstantValues.pageSize);
        List<TbGoodsWithBLOBs> list =  tbGoodsMapper.selectByPage(map);
        return new PageInfo<>(list);
    }

    /**
     * 获取商品详情
     * @param itemId 商品id
     * @return 商品实体
     */
    @Override
    public TbGoodsWithBLOBs getTbGoodsDetail(Long itemId) {
        return tbGoodsMapper.getTbGoodsDetail(itemId);
    }

    /**
     * 删除优惠券过期商品
     */
    @Override
    public void deleteOverdueGoods() {
        tbGoodsMapper.deleteOverdueGoods();
    }

    @Override
    public void tbGoodsSearch() {
        /*for (int i=0; i<TbkConstantValues.KEYWORD.length; i++){
            for (int j=0; j<10; j++){
                materialOptionalAPI.getGoodsData(j+1, TbkConstantValues.KEYWORD[i]);
            }
        }*/
    }


}
