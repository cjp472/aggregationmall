$(function () {
    //获取商品id
    var args = decodeURI(window.location.href).split("=");
    var goods_id = args[1];

    $.ajax({
        url: "user/showGoodsDetil",
        data: { "goods_id": goods_id },
        success: success
    });
    //回调
    function success(goods) {
        //清除原页面内容
        $("#body-content").empty();
        //券后价
        var perferPrice = (goods.min_group_price - goods.coupon_discount) / 100;

        //拼装页面内容
        var $goodsInfo = "<div class=\"serch\">\n" +
            "            <div class=\"serch-box\">\n" +
            "                <div class=\"serch-reletive\">\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        \n" +
            "        <div class=\"banner\">\n" +
            "            <div class=\"banner-content\">\n" +
            "                <div class=\"banner-img\" id='banner-img'>\n" +

            "                </div>\n" +

            "            </div>\n" +
            "        </div>\n" +
            "        \n" +
            "        <div class=\"info\">\n" +
            "            <h3 style=\"color: #030303;\n" +
            "    font-size: 16px;font-weight: 400;font-style: normal\">\n" +
            "                <span class=\"gico-tb\"></span>" + goods.goods_name + "</h3>\n" +
            "            <div class=\"price\">\n" +
            "                <span class=\"money\">券后价</span>\n" +
            "                <em class=\"money-later\">￥" + perferPrice + "</em>\n" +
            "                <em class=\"money-before\">￥" + goods.min_group_price / 100 + "</em>\n" +
            "                <span style=\"padding-left: 15px;\" class=\"count\">" + goods.sales_tip + "人已购</span>\n" +
            "                <strong class=\"strong\">\n" +
            "                    <i class=\"tiket-money\">" + goods.coupon_discount / 100 + "元</i>\n" +
            "                    <em class=\"tiket\">券</em>\n" +
            "                </strong>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        \n" +
            "        <div class=\"command\">\n" +
            "            <div class=\"command-content\">\n" +
            "                <h3 style=\"padding-left: 20px; font-weight: 700; font-size: 15px\">" + goods.mall_name + "</h3>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        \n" +
            "        <div class=\"score\">\n" +
            "            <div class=\"score-box\">\n" +
            "                <div class=\"store\"></div>\n" +
            "                <label style=\"font-size: 12px\">店铺评分</label>\n" +
            "            <span style=\"padding-left: 12px\">描述：" + goods.desc_txt + "</span><span style=\"padding-left: 12px\">服务：" + goods.serv_txt + "</span><span style=\"padding-left: 12px\">发货：" + goods.lgst_txt + "</span>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "\n" +
            "        \n" +
            "        <div class=\"synopsis\">\n" +
            "            <div class=\"syn-detail\">\n" +
            "                <div class=\"syn-title\">商品简介</div>\n" +
            "                <div class=\"syn-show\">" + goods.goods_desc + "</div>\n" +
            "            </div>\n" +
            "        </div>";

        //把主体内容追加进去
        $("#body-content").append($goodsInfo);

        //轮播图
        var goods_gallery_urls = goods.goods_gallery_urls;  //拼接的轮播图链接
        var urls = goods_gallery_urls.replace(/(^;)|(;$)/g, '');     //去掉尾部的分号
        var arr_urls = urls.split(";");     //已分号截取，形成一个数组
        //清空原来的图片
        $("#banner-img").empty();
        //遍历数组，将图片拼接上去
        for (var i = 0; i < arr_urls.length; i++) {
            $("#banner-img").append("<a href=\"###\" class=\"active\">\n" +
                "                        <img src=" + arr_urls[i] + " class=\"banimg1\">\n" +
                "                    </a>");
        }

    }

    //搜索框
    $("#search").click(function () {
        alert(11);
        var keyword = $("input").val();
        console.log(keyword);
        if (!typeof keyword == "undefined" || keyword == null || keyword == ""){
            window.location.href = "index.html?keyword="+keyword;
        }
    });

    //复制文案按钮
    $("#copy-document").click(function () {
        $.ajax({
            url     :   "user/getGoodsDocument",
            data    :   {"goods_id":goods_id},
            success :   success
        });
        //回调
        function success(goods) {
            //券后价
            var perferPrice = (goods.min_group_price - goods.coupon_discount) / 100;

            //拼装文案
            var $goodsDocument =
                "【商品】"+goods.goods_name+"\n" +
                "【在售价】"+goods.min_group_price/100+"元\n" +
                "【优惠券】"+goods.coupon_discount/100+"元\n" +
                "【券后价】"+perferPrice+"元\n" +
                "推荐理由："+goods.goods_desc+"\n" +
                "- - - - -\n" +
                "【商品领券下单】"+goods.promotion_url.short_url+"\n";

            //替代原有内容
            //$("#goods-document").replaceWith($goodsDocument);
            $("#goods-document").html('');
            $("#goods-document").html($goodsDocument);
        }

    });

    //立即领券按钮
    $("#getTicket").click(function () {
        $.ajax({
            url: "user/getPromotionUrl",
            data: { "goods_id": goods_id },
            success: success
        });
        //回调
        function success(data) {
            window.location.href = data.mobile_url;
        }
    });

    //轮播图
    var timer = null
    var step = -1;
    $('.banner a').get().forEach(function (item, index) {
        a = document.createElement('span')
        $('.slide-buttle').append(a);
    });

    $('.slide-buttle span:first-child').prop('class', 'bullter');

    function auto() {
        timer = setInterval(function () {
            if (step == $('.banner a').length - 1) {
                step = -1
            }
            step++;
            $('.banner-img a').eq(step).addClass('active').siblings().removeClass('active')
            $('.slide-buttle span').eq(step).addClass('bullter').siblings().removeClass('bullter')
        }, 2000);
    }
    auto();

    //按住图片停止轮播
    $('body').on('touchstart', function () {
        clearInterval(timer)
    });
    $('body').on('touchend', function () {
        auto();
    });

    //弹出层
    $('.foot-btn-left').click(function (event) {
        $('.detaile-hidden').show();
        event.preventDefault();
    });
    $('.detaile-hidden').click(function () {
        $('.detaile-hidden').hide();
    });

    //复制按钮
    $('#copy-text').click(function () {
        $(this).addClass('copy-text').html('已复制');
        //var clipboard = new Clipboard('#copy-text');   //这个类名是点击触发的类名
    });


});
