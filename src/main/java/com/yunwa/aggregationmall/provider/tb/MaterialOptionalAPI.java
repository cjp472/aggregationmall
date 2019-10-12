package com.yunwa.aggregationmall.provider.tb;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.request.TbkUatmFavoritesGetRequest;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkUatmFavoritesGetResponse;
import com.yunwa.aggregationmall.constant.TbkConstantValues;
import org.springframework.stereotype.Component;

/**
 * Created on 2019/10/11.
 *
 * @author yueyang
 */
// 淘宝客-推广者-物料搜索API
@Component
public class MaterialOptionalAPI {
    TaobaoClient client = new DefaultTaobaoClient(TbkConstantValues.GET__URL, TbkConstantValues.APP_KEY, TbkConstantValues.APP_SERCET);

    public Object getGoodsData(Integer pageNo, String keyword){
        TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
        req.setPageSize(100L);
        req.setPageNo(1L);
        req.setAdzoneId(TbkConstantValues.ADZONE_ID);
        req.setQ(keyword);
        TbkDgMaterialOptionalResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return null;
    }

}
