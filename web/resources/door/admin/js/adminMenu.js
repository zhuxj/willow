/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-1-10
 *作    者： 朱贤俊
 */
(function ($) {
    /**
     * 菜单管理对象
     * @param opt
     */
    function Menu(opt) {
        var defaultOpt = {
            workground:"workground",
            loadMask:"loadMask",
            basePath:"",
            defultUrl:"/admin/website/showWebSiteEdit",
            url:"",
            historyManager:{},
            menuCode:"ROOT",
            queryMenuUrl:"",
            rootMenuContainer:"rootMenuContainer",
            leftMenuContainer:"leftMenuContainer",
            resourceRoot:""
        }

        this.opt = $.extend({}, defaultOpt, opt);
        this.contentManager = new ContentManager(this.opt);
    }

    /**
     * 初始化菜单
     */
    Menu.prototype.init = function () {

        return this;
    }
    Menu.prototype.queryRootMenu = function () {
        var thisMenu = this;
        var opt = {
            success:function (result) {
                if (result) {
                    if (result.data) {
                        var dSize = (result.data).length;
                        var _li = null;
                        $.each(result.data, function (index, menu) {
                            var li = $('<li id="' + menu.objId + '"><a href="javascript:void(0)" url="' + menu.url + '" menuCode="' + menu.menuCode + '">' + menu.menuName + '</a></li>');
                            li.click(function () {
                                setCurrentMenu(menu.objId, null, null);
                                if (thisMenu.isLoading) {
                                    return false;
                                }
                                thisMenu.isLoading = true;
                                thisMenu.queryLeftMenu(menu.objId, function () {
                                    thisMenu.contentManager.loadUrl({
                                        url:menu.url
                                    });
                                    thisMenu.isLoading = false;
                                });
                                $(this).parent().find(".navli_current").removeClass("navli_current");
                                $(this).addClass("navli_current");

                            });
                            $("#" + thisMenu.opt.rootMenuContainer).append(li);
                            if ((dSize - 1) != index) {
                                $("#" + thisMenu.opt.rootMenuContainer).append('<li class="mainnav_line"></li>');
                            }

                            if (index == 0) {
                                _li = li;
                            }

                        });
                        //刷新页面时候，默认选择最后操作的菜单
                        var fl = getCurrentMenu().fl;
                        if (fl != null && fl != "") {
                            thisMenu.queryLeftMenu(fl, function () {
                            });
                            $("#" + fl).addClass("navli_current");
                        } else {
                            fl = _li.attr("id");
                            thisMenu.queryLeftMenu(fl, function () {
                            });
                            _li.addClass("navli_current");
                        }
                    }
                }
            }
        };
        this.queryMenu(opt);
    }

    Menu.prototype.queryLeftMenu = function (menuCode, callback) {
        var thisMenu = this;
        var opt = {
            data:{menuCode:menuCode},
            success:function (result) {
                if (result) {
                    if (result.data) {
                        var leftMenuContainer = $("#" + thisMenu.opt.leftMenuContainer);
                        leftMenuContainer.find("*").remove();
                        var _menubox_ttl = null;
                        $.each(result.data, function (index, menu) {
                                var menubox = $('<div class="menubox"></div>').appendTo(leftMenuContainer);
                                var menubox_ttl = $('<div isShow="false"  id="' + menu.objId + '" class="menubox_ttl2" style="cursor: pointer"><span class="menu_icon ' + menu.className + '" />' + menu.menuName + '</div>').appendTo(menubox);
                                var menubox_con = $('<div class="menubox_con" style="display: none"></div>').appendTo(menubox);
                                menubox_ttl.click(function () {
                                    if ($(this).attr("isShow") == "false") {
                                        $(".menubox_ttl").each(function () {//收缩其他的二级菜单
                                            $(this).next().hide();
                                            $(this).removeClass("menubox_ttl");
                                            $(this).addClass("menubox_ttl2");
                                            $(this).attr("isShow", "false");
                                        });
                                        menubox_con.show();
                                        $(this).removeClass("menubox_ttl2");
                                        $(this).addClass("menubox_ttl");
                                        $(this).attr("isShow", "true");
                                    } else {
                                        menubox_con.hide();
                                        $(this).removeClass("menubox_ttl");
                                        $(this).addClass("menubox_ttl2");
                                        $(this).attr("isShow", "false");
                                    }
                                });
                                var menubox_con_ul = $('<ul></ul>').appendTo(menubox_con);
                                if (menu.childMenuList != null) {
                                    $.each(menu.childMenuList, function (cidx, childMenu) {
                                        var menubox_con_ul_li = $('<li><a href="javascript:void(0)" url="' + childMenu.url + '" title="' + childMenu.menuName + '" id="' + childMenu.objId + '">' + childMenu.menuName + '</a></li>').appendTo(menubox_con_ul);
                                        menubox_con_ul_li.click(function () {
                                            setCurrentMenu(null, menubox_ttl.attr("id"), childMenu.objId);
                                            $(this).parent().parent().parent().parent().find(".menubox_current").removeClass("menubox_current");
                                            $(this).addClass("menubox_current"); //高亮当前菜单
                                            thisMenu.contentManager.loadUrl({
                                                url:childMenu.url
                                            });
                                        });
                                    });
                                }
                                if (index == 0) {
                                    _menubox_ttl = menubox_ttl;
                                }
                            }
                        );
                        var sl = getCurrentMenu().sl;
                        if (sl != null && sl != "") {
                            if ($("#" + sl)[0] != undefined) {
                                $("#" + sl).click();
                            } else {
                                _menubox_ttl.click();
                            }
                        } else {
                            _menubox_ttl.click();
                        }
                        leftMenuContainer.find("a[url='" + location.hash.slice(1) + "']").parent().addClass("menubox_current"); //高亮当前菜单
                    }
                }

                if ($.isFunction(callback)) {
                    callback();
                }
            }
        };
        this.queryMenu(opt);
    }

    /**
     * 查询菜单项
     * @param opt
     */
    Menu.prototype.queryMenu = function (opt) {
        var thisMenu = this;
        var defaultOpt = {
            url:thisMenu.opt.queryMenuUrl,
            data:{menuCode:thisMenu.opt.menuCode},
            dataType:'json',
            type:'post',
            success:function (result) {

            },
            error:function (jqXHR, statusText, error) {
                alert("加载菜单失败...");
            }
        }
        var menuAjaxOpt = $.extend({}, defaultOpt, opt);
        menuAjaxOpt.data.format = menuAjaxOpt.dataType;
        $.localAjax(menuAjaxOpt);

    }


    $.menu = function (opt) {
        var _adminMenu = new Menu(opt);
        return _adminMenu.init();
    }
})(jQuery);