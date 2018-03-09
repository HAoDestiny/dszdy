(function ($) {
    $.fn.scrollPagination = function (options) {
        var opts = $.extend($.fn.scrollPagination.defaults, options);
        var target = opts.scrollTarget;
        if (target == null) {
            target = obj;
        }
        opts.scrollTarget = target;
        return this.each(function () {
            $.fn.scrollPagination.init($(this), opts);
        });
    };
    $.fn.stopScrollPagination = function () {
        return this.each(function () {
            $(this).attr('scrollPagination', 'disabled');
        });
    };
    $.fn.scrollPagination.loadContent = function (obj, opts) {
        var target = opts.scrollTarget;
        var mayLoadContent = $(target).scrollTop() + opts.heightOffset >= $(document).height() - $(target).height();
        if (mayLoadContent) {
            if (opts.beforeLoad != null) {
                opts.beforeLoad();
            }
            $.api(opts.contentMethod, opts.contentData, _callback, opts.contentPage);
            function _callback(data) {
                if (data == null || data == "" || data[0] != "SUCCESS") {
                    $('#loading').fadeOut();
                    $('#nomoreresults').fadeIn();

                    $('.product-list').stopScrollPagination();
                    return false;
                }
                if (opts.afterLoad != null) {
                    opts.afterLoad(data);
                }
            }
        }
    };
    $.fn.scrollPagination.init = function (obj, opts) {
        var target = opts.scrollTarget;
        $(obj).attr('scrollPagination', 'enabled');
        $(target).scroll(function (event) {
            if ($(obj).attr('scrollPagination') == 'enabled') {
                $.fn.scrollPagination.loadContent(obj, opts);
            } else {
                event.stopPropagation();
            }
        });
        $.fn.scrollPagination.loadContent(obj, opts);
    };
    $.fn.scrollPagination.defaults = {
        'contentPage': null,
        'contentMethod': null,
        'contentData': {},
        'beforeLoad': null,
        'afterLoad': null,
        'scrollTarget': $(window),
        'heightOffset': 300
    };
})(jQuery);