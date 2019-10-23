$(function () {
    //初始化参数
    var pageNo;
    var opt_name;
    var keyword;
    var sort_type;

    //解析链接，获取用户输入的关键字
    var args = decodeURI(window.location.href).split("=");
    keyword = args[1];

    //首次进入加载商品列表
    pageNo = 1;
    showGoodsList(pageNo, null, keyword);

    //搜索按钮
    $("#search").click(function () {
        //获取输入的关键字
        keyword = $("input").val();
        //清空原有商品
        $("#index-theme-box").empty();
        //查询
        pageNo = 1;     //重置当前页
        showGoodsList(pageNo, opt_name, keyword, sort_type);
    });

    //点击顶部的分类标签
    $(".swip-wrp a").click(function () {
        opt_name = $(this).next().val();    //获取选中的分类标签，给参数赋值
        //清空原有商品
        $("#index-theme-box").empty();
        //查询
        pageNo = 1;     //重置当前页
        showGoodsList(pageNo, opt_name, keyword, sort_type);
    });

    //点击隐藏的分类标签
    $(".category-body a").click(function () {
        opt_name = $(this).next().val();    //获取选中的分类标签，给参数赋值
        //清空原有商品
        $("#index-theme-box").empty();
        //查询
        pageNo = 1;     //重置当前页
        showGoodsList(pageNo, opt_name, keyword, sort_type);
    });

    //点击排序方式
    $(".res01 a").click(function () {
        sort_type = $(this).next().val();    //获取选中的排序方式，给参数赋值
        //清空原有商品
        $("#index-theme-box").empty();
        //查询
        pageNo = 1;     //重置当前页
        showGoodsList(pageNo, opt_name, keyword, sort_type);
    });

    //商品懒加载
    $(window).scroll(function(){
        if (Math.round($(window).scrollTop() + $(window).height()) == $(document).height()) {
            //获取输入的关键字
            var keyword = $("input").val();
            console.log(pageNo);
            //加载下一页商品
            showGoodsList(++pageNo, opt_name, keyword, sort_type);
        }
    });

    //回到顶部
    $('.totop').click(function () {
        //$('html, body').scrollTop('0')    //快速回到顶部
        $("html,body").animate({ scrollTop: '0px' }, 500);  //缓慢回到顶部
    });

    //详情页点返回，跳回到首页原来的位置
    /*function jump(goods_id) {
        var pageEntity = {
            "pageNo": pageNo,
            "opt_name": opt_name,
            "keyword": keyword,
            "sort_type": sort_type
        };
        // 存储值：将对象转换为Json字符串
        sessionStorage.setItem('page', JSON.stringify(pageEntity));
    }*/

    //底部logo文字变色
    /*$(".aui-bottom-box a").click(function () {
        $(this).find(".sub").addClass("suba");
    });*/
});

//首页商品展示(当前页， 分类标签名，用户输入的关键字，排序方式)
function showGoodsList(pageNum, opt_name, keyword, sort_type) {
    $.ajax({
        url: "user/getGoodsList",
        data: { "pageNum": pageNum, "opt_name": opt_name, "keyword": keyword, "sort_type": sort_type },
        success: success
    });
    //回调
    function success(pageInfo) {
        $.each(pageInfo.list, function (index, goods) {
            console.log(goods.goods_id);
            //商品券后价
            var perferPrice = (goods.min_group_price - goods.coupon_discount) / 100;
            var $html = "<a href=\"pddDetails.html?goods_id=" + goods.goods_id + " \"  class='aui-list-item' >\n" +
            /*var $html = "<a href=\"javascript:void(0); \" onclick='jump("+goods.goods_id+")' class='aui-list-item'>\n" +*/
            /*var $html = "<a href=\"pddDetails.html?goods_id=" + goods.goods_id + " \" class='aui-list-item' >\n" +*/
                "<input type='hidden' value='"+goods.goods_id+"'/>                            "+
                "\t\t\t\t\t\t\t<div class=\"aui-list-theme-img\">\n" +
                "\t\t\t\t\t\t\t\t<img src=" + goods.goods_thumbnail_url + " >\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t<div class=\"aui-list-theme-message\">\n" +
                "\t\t\t\t\t\t\t\t<h1 class=\"aui-list-theme-title\">" + goods.goods_name + "</h1>\n" +
                "\t\t\t\t\t\t\t\t<h1 class=\"aui-list-theme-subtitle\"></h1>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<h4>" +
                "                   <em class=\"aui-infos\">" + goods.sales_tip + "人已购</em>" +
                "\t\t\t\t\t\t\t\t\t<div class=\"aui-coupon\">\n" +
                "<span class=\"line-group coupon-tag-wrap\"style=\"border:1px solid red\">\n" +
                "\t <span class=\"coupon-tag\" style=\"background: linear-gradient(90deg,rgb(255, 0, 0),rgb(255, 0, 0));\">券</span><span class=\"coupon-price\" style=\"color:rgb(255, 0, 0);padding-right: 1px;border-right:1px solid red\">" + goods.coupon_discount / 100 + "元</span>\n" +
                " </span>\n" +
                " </div>\n" +
                "               </h4>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"aui-flex\">\n" +
                "\t\t\t\t\t\t\t\t\t<div class=\"aui-flex-box\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<h2><em>￥</em><span>" + perferPrice + "</span> <i>￥" + goods.min_group_price / 100 + "</i></h2>\n" +
                "\t\t\t\t\t\t\t\t\t</div>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t</a>";

            //将商品数据追加进去
            $("#index-theme-box").append($html);

        });
    }

}

//分类变颜色
var flag = 1;
$('.category-mode').click(function () {
    if (flag == 1) {
        $('.category-body').addClass('change');
        $('.category-mode').addClass('traform');
        flag = 0;
    } else {
        $('.category-body').removeClass('change');

        $('.category-mode').removeClass('traform');
        flag = 1;
    }
});

//点击商品将页面信息存到sessionStorage，然后跳转到详情页
/*function jump(goods_id) {
    /!*var pageEntity = {
                        "pageNo"   : pageNo,
                        "opt_name" : opt_name,
                        "keyword"  : keyword,
                        "sort_type": sort_type
                     };
    // 存储值：将对象转换为Json字符串
    console.log(444444441);
    sessionStorage.setItem('page', JSON.stringify(pageEntity));*!/
    //跳转
    window.location.href = "pddDetails.html?goods_id="+goods_id;

}*/
