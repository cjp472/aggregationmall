package com.yunwa.aggregationmall.service.tb;

import com.yunwa.aggregationmall.common.RespBean;
import com.yunwa.aggregationmall.pojo.CommissionDTO;
import org.springframework.stereotype.Repository;

/**
 * Created on 2019/10/21.
 *
 * @author yueyang
 */
@Repository
public interface TbOrderService {
    void tbOrderSearch();

    RespBean tbOrderBind(String tradeId, String userId);

    CommissionDTO tbGetMoney(String userId);

    void updateOrderStatus();

    boolean moveToFinished(String userId);
}
