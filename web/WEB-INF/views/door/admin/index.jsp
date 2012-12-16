<%
    /**
     *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
     *日    期： 12-1-10
     *作    者： 朱贤俊
     */%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>后台管理中心</title>
    <jsp:include page="../commonCss.jsp"/>
    <jsp:include page="../commonJs.jsp"/>
    <script src="${resourceRoot}/door/admin/js/adminMenu.js" type="text/javascript"></script>
    <script src="${resourceRoot}/door/admin/js/shopAdmin.js" type="text/javascript"></script>
</head>
<body>
<!--header-->
<div class="header">
    <div class="header_top clearfix">
        <div class="marl_10" style="float:left;font-weight: normal; font-size: 25px;color: #ffffff;">后台管理中心</div>

        <div class="header_top_right">
            您好：xx，欢迎使用xx系统！ <a id="logout" href="javascript:void(0)">[退出]</a>
        </div>
    </div>
    <div class="mainnav">
        <ul id="rootMenuContainer"></ul>
        <div class="mainnav_right">
            <a id="manageIndexA" class="header_btn01" href="javascript:void(0)">管理首页</a>
            <a class="header_btn02" href="/" target="_blank"><span></span>浏览网站</a>
        </div>
    </div>
</div>
<!--header end-->


<!--侧栏-->
<div id="leftMenuContainer" class="sidebar">

</div>
<!--侧栏 end-->
<!--切换-->
<div class="scrollbar" id="leftToggler">
    <div class="lefttoggler_btn"></div>
</div>
<!--切换 end-->

<!--主体内容-->
<div id="workspace" class="mainbox">
    <!--面包屑-->
    <div class="crumbsbox clearfix" id="navigationDiv">
        <div class="fl">
            <a class="iconbg btn_back" href="javascript:void(0)" id="mainPageReturn" title="返回"></a>

            <%--<p class="fl"><a href="#">首页</a>&gt<a href="#">名称A</a>&gt<span class="fgray7">名称B</span></p>--%>
        </div>
        <div class="fr">
            <a class="ben_refresh" href="javascript:void(0)" id="mainRefresh" title="刷新页面"><i
                    class="iconbg"></i>刷新页面</a>
        </div>
    </div>
    <!--面包屑 end-->

    <div id="workground"></div>
    <div id="workgroundSec" style="display: none;width: 100%"></div>
</div>


<!--begin LoadMask-->
<div id="loadMask" class="loading" style="display: none"><img
        src="${resourceRoot}/door/admin/skins/default/images/loading.gif"/>正在加载...
</div>
<!--END loadMask-->
</body>
</html>