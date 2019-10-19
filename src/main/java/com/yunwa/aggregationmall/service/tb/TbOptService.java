package com.yunwa.aggregationmall.service.tb;

/**
 * Created on 2019/10/16.
 *
 * @author yueyang
 */
public interface TbOptService {
    //void getGoodsOptInfo();

    void updateByOptName(String[] optNames, Long startTkRate, String sort, Long adzoneId, Boolean needFreeShipment, Boolean isTmall, Boolean hasCoupon);
}
