(function ($) {
    $.fn.eglazyload = function (options) {
        var settings = {func:0};
        if (options) {
            $.extend(settings, options)
        }
        var elements = this;
        $(window).bind("scroll",function () {
            elements = $.loadobj(elements, settings)
        }).bind("resize", function () {
                elements = $.loadobj(elements, settings)
            });
        this.each(function () {
            var self = this;
            if (settings.func) {
                $(self).one("dofunc", function () {
                    if (!$(self).attr("isloaded")) {
                        settings.func($(self));
                        $(self).attr("isloaded", "1")
                    }
                })
            } else {
                if (undefined == $(self).attr("original")) {
                    $(self).attr("original", $(self).attr("src"))
                }
                if (undefined == $(self).attr("src") || $.trim($(self).attr("src")) == '' || ($.abovetop(self) || $.leftbegin(self) || $.belowfold(self) || $.rightfold(self))) {
                    $(self).removeAttr("src");
                    self.loaded = false
                } else {
                    self.loaded = true
                }
                $(self).one("appear", function () {
                    if (!self.loaded) {
                        $(this).bind("load",function () {
                            $(self).hide().show();
                            self.loaded = true
                        }).attr("src", $(self).attr("original"))
                    }
                })
            }
        });
        elements = $.loadobj(elements, settings);
        return this
    };
    $.fn.eglazyshowimg = function () {
        $.loadobj($(this), {func:0})
    };
    $.loadobj = function (elements, settings) {
        elements.each(function () {
            if (!$.abovetop(this) && !$.leftbegin(this) && !$.belowfold(this) && !$.rightfold(this)) {
                if (settings.func) {
                    $(this).trigger("dofunc")
                } else {
                    $(this).trigger("appear")
                }
            }
        });
        var temp = $.grep(elements, function (element) {
            return!element.loaded
        });
        return $(temp)
    };
    $.belowfold = function (element) {
        var fold = $(window).height() + $(window).scrollTop();
        return fold <= $(element).offset().top
    };
    $.rightfold = function (element) {
        var fold = $(window).width() + $(window).scrollLeft();
        return fold <= $(element).offset().left
    };
    $.abovetop = function (element) {
        var fold = $(window).scrollTop();
        return fold >= $(element).offset().top + $(element).height()
    };
    $.leftbegin = function (element) {
        var fold = $(window).scrollLeft();
        return fold >= $(element).offset().left + $(element).width()
    }
})(jQuery);

//默认图片为延迟加载，需要将img的src写为original,src不保留
$(document).ready(function () {
//    $("img").eglazyload();
})