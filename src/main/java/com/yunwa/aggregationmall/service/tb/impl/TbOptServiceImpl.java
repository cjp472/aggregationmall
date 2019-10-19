package com.yunwa.aggregationmall.service.tb.impl;

import com.yunwa.aggregationmall.constant.TbkConstantValues;
import com.yunwa.aggregationmall.dao.tb.TbOptMapper;
import com.yunwa.aggregationmall.dao.tb.TbSearchParaMapper;
import com.yunwa.aggregationmall.pojo.tb.po.TbOpt;
import com.yunwa.aggregationmall.pojo.tb.po.TbSearchPara;
import com.yunwa.aggregationmall.provider.tb.MaterialOptionalAPI;
import com.yunwa.aggregationmall.service.tb.TbOptService;
import com.yunwa.aggregationmall.service.tb.TbSearchParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/10/16.
 *
 * @author yueyang
 */
@Service
public class TbOptServiceImpl implements TbOptService {
    @Autowired
    private TbOptMapper tbOptMapper;
    @Autowired
    private TbSearchParaMapper tbSearchParaMapper;
    @Autowired
    private MaterialOptionalAPI materialOptionalAPI;

    //从淘宝获取商品分类的信息，存到数据库
    /*@Override
    public void getGoodsOptInfo() {
        //获取商品查询条件
        TbSearchPara tbSearchPara = tbSearchParaMapper.getLastRecord();
        Long startTkRate = tbSearchPara.getStartTkRate();//佣金比率下限
        String sort = tbSearchPara.getSort();   //排序方式
        Long adzoneId = tbSearchPara.getAdzoneId(); //推广位id
        Boolean needFreeShipment = tbSearchPara.getNeedFreeShipment();  //是否包邮
        Boolean isTmall = tbSearchPara.getIsTmall();    //是否天猫商品
        Boolean hasCoupon = tbSearchPara.getHasCoupon();    //是否有优惠券

        for (int i=0; i<TbkConstantValues.KEYWORD.length; i++){
            //获取商品分类对象
            TbOpt tbOpt = materialOptionalAPI.getGoodsOptData(TbkConstantValues.KEYWORD[i], startTkRate, sort, adzoneId,
                    needFreeShipment, isTmall, hasCoupon);
            if (tbOpt != null){
                //计算该分类下商品总页数
                Long totalPageCount = (tbOpt.getTotalGoodsCount() % 100) == 0 ? tbOpt.getTotalGoodsCount() / 100 :
                                            tbOpt.getTotalGoodsCount() / 100 + 1;
                if (totalPageCount == 0){
                    totalPageCount = 1L;
                }
                tbOpt.setTotalPageCount(totalPageCount);
                //将封装的商品分类对象存入到数据库
                tbOptMapper.insert(tbOpt);
            }

        }
        //TbkConstantValues.KEYWORD
        //tbOptMapper.getGoodsOptInfo()
    }*/

    //更新分类信息
    public void updateByOptName(String[] optNames, Long startTkRate, String sort, Long adzoneId, Boolean needFreeShipment,
                                  Boolean isTmall, Boolean hasCoupon){
        for (int i=0; i<optNames.length; i++){
            //获取当前分类对应的分类id串
            String categoryIdString = tbOptMapper.selectCategoryId(optNames[i]);
            //调用接口获取最新的分类信息
            TbOpt tbOpt = materialOptionalAPI.getGoodsOptById(categoryIdString, startTkRate, sort, adzoneId, needFreeShipment,
                    isTmall, hasCoupon);
            if (tbOpt != null){
                //设置分类名
                tbOpt.setOptName(optNames[i]);
                //更新分类信息
                tbOptMapper.updateOptInfo(tbOpt);
            }
        }
    }
}
