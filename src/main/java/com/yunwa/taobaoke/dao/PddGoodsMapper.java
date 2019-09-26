package com.yunwa.taobaoke.dao;

import com.yunwa.taobaoke.pojo.PddGoods;
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

    int deleteByPrimaryKey(Long goodsId);

    int insert(PddGoods record);

    int insertSelective(PddGoods record);

    PddGoods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(PddGoods record);

    int updateByPrimaryKey(PddGoods record);


}