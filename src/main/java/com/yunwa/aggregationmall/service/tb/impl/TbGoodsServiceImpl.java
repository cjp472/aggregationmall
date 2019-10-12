package com.yunwa.aggregationmall.service.tb.impl;

import com.yunwa.aggregationmall.constant.TbkConstantValues;
import com.yunwa.aggregationmall.provider.tb.MaterialOptionalAPI;
import com.yunwa.aggregationmall.service.tb.TbGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbGoodsServiceImpl implements TbGoodsService {
    @Autowired
    private MaterialOptionalAPI materialOptionalAPI;

    @Override
    public void tbGoodsSearch() {
        for (int i=0; i<TbkConstantValues.KEYWORD.length; i++){
            for (int j=0; j<10; j++){
                materialOptionalAPI.getGoodsData(j+1, TbkConstantValues.KEYWORD[i]);
            }
        }


    }
}
