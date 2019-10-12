package com.yunwa.aggregationmall.service.pdd.impl;

import com.yunwa.aggregationmall.constant.PddConstantValues;
import com.yunwa.aggregationmall.dao.pdd.PddOptIdMapper;
import com.yunwa.aggregationmall.pojo.pdd.po.PddOptId;
import com.yunwa.aggregationmall.service.pdd.OptService;
import com.yunwa.aggregationmall.utils.OptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/10/12.
 *
 * @author yueyang
 */
@Service
public class OptServiceImpl implements OptService {
    @Autowired
    private OptUtils optUtils;

    @Autowired
    private PddOptIdMapper pddOptIdMapper;

    @Override
    public void getOptInfo() {
        for (int i=0; i<PddConstantValues.OPT_IDS.length; i++){
            PddOptId pddOptId = optUtils.getGoodsOptInfo(PddConstantValues.OPT_IDS[i]);
            if (pddOptId != null){
                pddOptIdMapper.insert(pddOptId);
            }
        }
    }
}
