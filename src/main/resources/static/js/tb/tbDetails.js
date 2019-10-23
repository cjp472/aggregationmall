$(function () {
    //获取商品id
    var args = decodeURI(window.location.href).split("=");
    var itemId = args[1];
    var shortTitle;
    var title;
    var zkFinalPrice;
    var volume;
    var shopTitle;
    var couponAmount;
    var tCommand;
    var realPrice;
    var couponShareUrl;
    var itemDescription;
    var smallImages;

    $.ajax({
        url: "user/getTbGoodsDetail",
        data: { "itemId": itemId },
        success: success
    });
    //回调
    function success(goods) {
        //赋值给相关变量
        shortTitle = goods.shortTitle;
        title = goods.title;
        zkFinalPrice = goods.zkFinalPrice;
        volume = goods.volume;
        shopTitle = goods.shopTitle;
        couponAmount = goods.couponAmount;
        tCommand = goods.tCommand;
        realPrice = goods.realPrice;
        couponShareUrl = "https:"+goods.couponShareUrl;
        itemDescription = goods.itemDescription;
        smallImages = goods.smallImages;
        //拼装文案
        var $goodsDocument =
            "【商品】"+shortTitle+"\n" +
            "【在售价】"+zkFinalPrice+"元\n" +
            "【优惠券】"+couponAmount+"元\n" +
            "【券后价】"+realPrice+"元\n" +
            "推荐理由："+itemDescription+"\n" +
            "- - - - -\n" +
            "【商品领券下单】"+tCommand+"\n";
        //替代原有内容
        $("#goods-document").html('');
        $("#goods-document").html($goodsDocument);

        //拼接立即领券按钮的内容
        var $promotionUrl = "<a href=\""+couponShareUrl+"\" class=\"foot-btn foot-btn-right\" id=\"lq\">\n" +
            "                <p class=\"foot-sub\" id=\"getTicket\">立即领券</p>\n" +
            "            </a>";
        //代替原内容
        $("#lq").html('');
        $("#lq").html($promotionUrl);

        //轮播图
        var picUrls = smallImages.replace(']', '').replace('[', '');    //去掉首位的[ 和 ]
        //转换成链接数组
        var picUrlArr = picUrls.split(",");

        //拼接图文详情的图片
        var $picDetail = " <img src=\""+picUrlArr[0]+" \" class=\"sub-img\">" +
            " <img src=\""+picUrlArr[1]+"\" class=\"sub-img\">" +
            " <img src=\""+picUrlArr[2]+"\" class=\"sub-img\">" +
            " <img src=\""+picUrlArr[3]+"\" class=\"sub-img\">";

        //清除原页面内容
        $("#body-content").empty();
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
            "                <span class=\"gico-tb\"></span>" + title + "</h3>\n" +
            "            <div class=\"price\">\n" +
            "                <span class=\"money\">券后价</span>\n" +
            "                <em class=\"money-later\">￥" + realPrice + "</em>\n" +
            "                <em class=\"money-before\">￥" + zkFinalPrice + "</em>\n" +
            "                <span style=\"padding-left: 15px;\" class=\"count\"> 月销量 " + volume + "</span>\n" +
            "                <strong class=\"strong\">\n" +
            "                    <i class=\"tiket-money\">" + couponAmount + "元</i>\n" +
            "                    <em class=\"tiket\">券</em>\n" +
            "                </strong>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "<div class=\"command\">\n" +
            "            <div class=\"command-content\">\n" +
            "                <div class=\"command-code\">\n" +
            "                    <div class=\"code-box\">\n" +
            "                        <textarea class=\"code\" id='tCommand'>"+tCommand+"</textarea>\n" +
            "                    </div>\n" +
            "                    <button class=\"copy\" onclick='copyTCommand()' id='copy-tCommand'>一键复制</button>\n" +
            "                </div>\n" +
            "                <p style=\"font-size: 12px;color: #999;text-align: center;\">\n" +
            "                    <--kt-->\n" +
            "                </p>\n" +
            "            </div>\n" +
            "        </div>"+
            "        \n" +
            "        <div class=\"command\">\n" +
            "            <div class=\"command-content\">\n" +
            "                <h3 style=\"padding-left: 20px; font-weight: 700; font-size: 15px\">" + shopTitle + "</h3>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        \n" +
            "        <div class=\"score\">\n" +
            "            <div class=\"score-box\">\n" +
            "                <div class=\"store\"></div>\n" +
            /*"                <label style=\"font-size: 12px\">店铺评分</label>\n" +
            "            <span style=\"padding-left: 12px\">描述：" + goods.desc_txt + "</span><span style=\"padding-left: 12px\">服务：" + goods.serv_txt + "</span><span style=\"padding-left: 12px\">发货：" + goods.lgst_txt + "</span>\n" +*/
            "            </div>\n" +
            "        </div>\n" +
            "\n" +
            "        \n" +
            "        <div class=\"synopsis\">\n" +
            "            <div class=\"syn-detail\">\n" +
            "                <div class=\"syn-title\">商品简介</div>\n" +
            "                <div class=\"syn-show\">" + itemDescription + "</div>\n" +
            "            </div>\n" +
            "        </div>" +
            "    <div class=\"img-text\">\n" +
            "        <div class=\"text-content\">\n" +
            "            <div class=\"img-text-detail\">\n" +
            "                <div class=\"img-text-btn\" >\n" +
            "                    <span class=\"text-btn-span\" onclick='showPicture()'>\n" +
            "                        查看图文详情\n" +
            "                    </span>\n" +
            "                    <div hidden id='pic'></div>"+
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>";

        //把主体内容追加进去
        $("#body-content").append($goodsInfo);
        //把图文详情的图片追加进去
        $("#pic").append($picDetail);

        //轮播图
        /*var picUrls = smallImages.replace(']', '').replace('[', '');    //去掉首位的[ 和 ]
        //转换成链接数组
        var picUrlArr = picUrls.split(",");*/

        //清空原来的图片
        $("#banner-img").empty();
        //遍历数组，将图片拼接上去
        for (var i = 0; i < picUrlArr.length; i++  ) {
            $("#banner-img").append("<a href=\"###\" class=\"active\">\n" +
                "                        <img src=" + picUrlArr[i] + " class=\"banimg1\">\n" +
                "                    </a>");
        }

    }

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
    });

    //复制淘口令按钮
    $('#copy-tCommand').click(function () {
        $(this).addClass('copy-tCommand').html('已复制');
    });

    //查看图文详情
    $(".text-btn-span").click(function () {
        //遍历数组，将图片拼接上去
        for (var i = 0; i < picUrlArr.length; i++  ) {
            $(this).appendChild("<a href=\"###\" class=\"active\">\n" +
                "                        <img src=" + picUrlArr[i] + " class=\"banimg1\">\n" +
                "                    </a>");
        }
    });

});

function showPicture() {
    console.log(11);
    //将图片显示出来
    $("#pic").removeAttr("hidden");
}
