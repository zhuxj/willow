/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-1-10
 *作    者： 朱贤俊
 */

//菜单工具方法集menuUtils
var menuUtils = {};
var jq = null;
$(document).ready(function () {
    jq = $;
    shopAdminInit();
    /**
     * 初始化后台管理
     */
    function shopAdminInit() {
        var _menu = $.menu({
            basePath:"",
            queryMenuUrl:"/admin/queryMenu",
            resourceRoot:resourceRoot
        });
        _menu.queryRootMenu();//初始化一级菜单
        buildMenuUtils(_menu);
        setLeftTogglerHight();
        menuUtils.loadUrl({
            url:location.hash.slice(1)
        });
        disableF5();
        $("#mainPageReturn").click(function () {
            if ($("#workground").css("display") != "none") {
                menuUtils.loadUrl({
                    url:location.hash.slice(1)
                });
            } else {
                jq.workgroundManager.returnPage(true);
            }

        });
        $("#mainRefresh").click(function () {
            refreshPage();
        });
    }

    /**
     * 构建菜单工具方法集menuUtils
     * @param _menu
     */
    function buildMenuUtils(_menu) {
        menuUtils.loadUrl = function (opt) {
            _menu.contentManager.loadUrl(opt);
        };
    }


    /**
     * 左侧菜单栏显示控制
     */
    var closeLeft = false;
    $("#leftToggler").click(function () {
        $("#leftMenuContainer").toggle();
        if (!closeLeft) {
            $('body').addClass("closeLeft");
            $('#workspace').removeClass("mainbox");
            $('#workspace').addClass("mainbox100");
            closeLeft = true;
        } else {
            $('body').removeClass("closeLeft");
            $('#workspace').removeClass("mainbox100");
            $('#workspace').addClass("mainbox");
            closeLeft = false;
        }
        $(window).trigger('resize'); //很重要，触发改变窗口大小时间，从而使得列表自适应
    });


    /**
     * 伸缩栏高度控制
     */
    function setLeftTogglerHight() {
        debugger
        var height = (($(window).height() - $("#leftToggler").offset().top));
        $("#leftToggler").css("height", height + "px");
        $("#workground").css("height", (height - 30) + "px");
        $("#workgroundSec").css("height", (height - 30) + "px");
        $("#leftMenuContainer").css("height", height + "px");
    }

    /**
     * 屏蔽F5的浏览器刷新，但是刷新内容区
     */
    function disableF5() {
        $(document).bind("keydown", function (event) {
            var e;
            if ($.browser.msie) {
                e = window.event;
            } else {
                e = event;
            }
            if (e.keyCode == 116) {
                refreshPage();
                e.keyCode = 0;
                return false;
            }
        });
    }

    /*刷新页面*/
    function refreshPage() {
        if ($("#workground").css("display") != "none") {
            menuUtils.loadUrl({
                url:location.hash.slice(1)
            });
        } else {
            top.jq.workgroundManager.refreshPage();
        }

    }

    //绑定退出登录的事件
    $("#logout").click(function () {
        logout();
        return false;
    });
    /**
     * 退出登录
     */
    function logout() {
        $.localAjax({
            url:"/shopadmin/logout",
            data:{},
            dataType:'json',
            type:'post',
            success:function () {
                location.href = "/shopadmin/index.html";
            }
        });
    }

    $("#manageIndexA").click(function () {
        top.menuUtils.loadUrl({
            url:"/shopadmin/showManageIndex"
        })
    })
});

