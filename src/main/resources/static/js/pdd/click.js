(function () {

    $('.res-ul li').click(function () {
        $(this).find('a').addClass('click').parent().siblings().find('a').removeClass('click')
    })
    $('.swip-wrp .swip-slide').click(function () {
        $(this).find('a').addClass('current').parent().siblings().find('a').removeClass('current')

    })
})()