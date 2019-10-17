package com.yunwa.aggregationmall.pojo.tb.po;

import com.alibaba.fastjson.annotation.JSONField;

public class TbGoodsWithBLOBs extends TbGoods {
    private String smallImages;

    @JSONField(name = "item_description")
    private String itemDescription;

    public String getSmallImages() {
        return smallImages;
    }

    public void setSmallImages(String smallImages) {
        this.smallImages = smallImages == null ? null : smallImages.trim();
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription == null ? null : itemDescription.trim();
    }
}