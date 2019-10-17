package com.yunwa.aggregationmall.dao.tb;

import com.yunwa.aggregationmall.pojo.tb.po.TbGoods;
import com.yunwa.aggregationmall.pojo.tb.po.TbGoodsWithBLOBs;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface TbGoodsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TbGoodsWithBLOBs record);

    int insertSelective(TbGoodsWithBLOBs record);

    TbGoodsWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbGoodsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TbGoodsWithBLOBs record);

    int updateByPrimaryKey(TbGoods record);

    //首页商品查询
    List<TbGoodsWithBLOBs> selectByPage(HashMap<String,Object> map);
}