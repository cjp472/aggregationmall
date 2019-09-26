$(function () {
    $.ajax({
        url     :   "showGoodsDetil",
        data    :   {"goods_id":13515244},
        success :   success
    });
    //回调
    function success(goods) {
        //清楚页面内容
        $("#body-content").empty();
        //券后价
        var perferPrice = (goods.min_group_price - goods.coupon_discount)/100;
        //拼装页面内容
        var $goodsInfo = "<div class=\"serch\">\n" +
            "            <div class=\"serch-box\">\n" +
            "                <div class=\"serch-reletive\">\n" +
            "                <input class=\"content\"placeholder=\"搜索你想要的宝贝\">\n" +
            "                    <span class=\"iconfont icon-icon-test ico\"></span>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        \n" +
            "        <div class=\"banner\">\n" +
            "            <div class=\"banner-content\">\n" +
            "                <div class=\"banner-img\">\n" +
            "                    <div class=\"slide-1\">\n" +
            "                        <img src=\"images/banner01.jpg\" class=\"banimg1\">\n" +
            "\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"slide-buttle\">\n" +
            "                    <span class=\"bullter\"></span>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        \n" +
            "        <div class=\"info\">\n" +
            "            <h3 style=\"color: #030303;\n" +
            "    font-size: 16px;font-weight: 400;font-style: normal\">\n" +
            "                <span class=\"gico-tb\"></span>"+goods.goods_name+"</h3>\n" +
            "            <div class=\"price\">\n" +
            "                <span class=\"money\">券后价</span>\n" +
            "                <em class=\"money-later\">￥"+perferPrice+"</em>\n" +
            "                <em class=\"money-before\">￥"+goods.min_group_price/100+"</em>\n" +
            "                <span style=\"padding-left: 15px;\" class=\"count\">"+goods.sales_tip+"人</span>\n" +
            "                <strong class=\"strong\">\n" +
            "                    <i class=\"tiket-money\">"+goods.coupon_discount/100+"元</i>\n" +
            "                    <em class=\"tiket\">券</em>\n" +
            "                </strong>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        \n" +
            "        <div class=\"command\">\n" +
            "            <div class=\"command-content\">\n" +
            "                <h3 style=\"padding-left: 20px; font-weight: 700; font-size: 15px\">"+goods.mall_name+"</h3>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        \n" +
            "        <div class=\"score\">\n" +
            "            <div class=\"score-box\">\n" +
            "                <div class=\"store\"></div>\n" +
            "                <label style=\"font-size: 12px\">店铺评分</label>\n" +
            "            <span style=\"padding-left: 12px\">描述："+goods.desc_txt+"</span><span style=\"padding-left: 12px\">服务："+goods.serv_txt+"</span><span style=\"padding-left: 12px\">发货："+goods.lgst_txt+"</span>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "\n" +
            "        \n" +
            "        <div class=\"synopsis\">\n" +
            "            <div class=\"syn-detail\">\n" +
            "                <div class=\"syn-title\">商品简介</div>\n" +
            "                <div class=\"syn-show\">"+goods.goods_desc+"</div>\n" +
            "            </div>\n" +
            "        </div>";

        //把内容追加进去
        $("#body-content").append($goodsInfo);
    }
})