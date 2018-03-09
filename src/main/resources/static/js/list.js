$(function () {

    $(document).scroll(function () {
        if ($(document).scrollTop() > 600) {
            $("#indexToTop").attr("style", "display:block").on("click", function () {
                window.scrollTo(0, 0);
            });
        } else {
            $("#indexToTop").attr("style", "display:none");
        }
    });
     
    $('.js-menu-where').on('click', function () {
        $('.menu').removeClass("hide-item");
        $('.menu').fadeIn().addClass("show-item");
        var searchht = $(".js-search-rows").height() + 80
        var wht = $(window).height();
        if (searchht > wht) {
            $(".wrap").height(searchht);
        } else {
            $(".wrap").height("100%");
        }
    });

    $('.menu .arrow').on('click', function () {
        $('.menu').removeClass("show-item");
        $('.menu').fadeOut().addClass("hide-item");
    });
});