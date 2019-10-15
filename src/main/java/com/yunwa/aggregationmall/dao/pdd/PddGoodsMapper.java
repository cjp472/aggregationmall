package com.yunwa.aggregationmall.dao.pdd;

import com.yunwa.aggregationmall.pojo.pdd.po.PddGoods;
import com.yunwa.aggregationmall.pojo.pdd.vo.PddGoodsDocumentVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface PddGoodsMapper {
    //插入拼多多商品数据
    boolean insertPddGoodsData(PddGoods pddGoods);

    //删除全部商品
    int delPddGoods();

    //分页查询
    List<PddGoods> selectByPage(HashMap<String,Object> map);

    //商品详情
    PddGoods showGoodsDetil(long goods_id);

    //获取商品文案
    PddGoodsDocumentVO getGoodsDocument(long goods_id);

    int deleteByPrimaryKey(Long goodsId);

    int insert(PddGoods record);

    int insertSelective(PddGoods record);

    PddGoods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(PddGoods record);

    int updateByPrimaryKey(PddGoods record);

    //获取所有的商品id
    List<Long> getAllGoodsId();

    //将has_url字段设为1
    int changeUrlStatus(Long goodsId);
}