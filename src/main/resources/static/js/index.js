$(function () {
    //首次进入加载商品列表
    $.ajax({
        url         :   "getGoodsList",
        data        :   {"pageNum":1, "keyword":"衣"},
        success     :   success
    });
    //回调
    function success(pageInfo) {
        $.each(pageInfo.list, function (index, goods) {
            console.log(pageInfo.list);
            //商品券后价
            var perferPrice = (goods.min_group_price - goods.coupon_discount)/100;
            var $html = "<a href=\"pddDetails.html?goods_id="+goods.goods_id+" \" class='aui-list-item'>\n" +
                "\t\t\t\t\t\t\t<div class=\"aui-list-theme-img\">\n" +
                "\t\t\t\t\t\t\t\t<img src="+goods.goods_thumbnail_url+" >\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t<div class=\"aui-list-theme-message\">\n" +
                "\t\t\t\t\t\t\t\t<h1 class=\"aui-list-theme-title\">"+goods.goods_desc+"</h1>\n" +
                "\t\t\t\t\t\t\t\t<h1 class=\"aui-list-theme-subtitle\">预售商品 限时抽取三名幸运奖</h1>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<h4><em class=\"aui-infos\">"+goods.sales_tip+"人已购</em></h4>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"aui-flex\">\n" +
                "\t\t\t\t\t\t\t\t\t<div class=\"aui-flex-box\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<h2><em>￥</em><span>"+perferPrice+"</span> <i>￥"+goods.min_group_price/100+"</i></h2>\n" +
                "\t\t\t\t\t\t\t\t\t</div>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t<div class=\"aui-coupon\">\n" +
                "<span class=\"line-group coupon-tag-wrap\"style=\"border:1px solid red\">\n" +
                "\t <span class=\"coupon-tag\" style=\"background: linear-gradient(90deg,rgb(255, 0, 0),rgb(255, 0, 0));\">券</span><span class=\"coupon-price\" style=\"color:rgb(255, 0, 0);padding-right: 1px;border-right:1px solid red\">"+goods.coupon_discount/100+"元</span>\n" +
                " </span>\n" +
                " </div>\n" +
                "\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t</a>";

            $("#index-theme-box").append($html);

        });
    }

    /*//跳转详情页
    $("a").click(function () {
        window.location.href = "pddDetails.html?goods_id="+;
    });*/

});