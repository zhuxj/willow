<%
    /**
     *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
     *日    期： 12-3-9
     *作    者： 朱贤俊
     */%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<html>
<head>
    <title>后台登录</title>
    <jsp:include page="../commonCss.jsp"/>
    <jsp:include page="../commonJs.jsp"/>
    <script src="${resourceRoot}/door/admin/js/logon.js" type="text/javascript"></script>
</head>
<body>
<table height="100%">
    <tr>
        <td align="left" bgcolor="#fafafa">
            <div class="loginbox">
                <div class="logo"></div>
                <div class="loginbox_inner">
                    <ul class="clearfix">
                        <li style="display: none;">
                            <input type="text" name="logonId" id="logonId" value="${logonId}"/>
                        </li>

                        <li>
                            <label class="fl">用户名</label><input class="inputbox" type="text" name="userName" title="用户名"
                                                                id="userName">
                        </li>
                        <li>
                            <label class="fl">密　码</label><input class="inputbox" type="password" name="password"
                                                                title="密码"
                                                                id="password"></label>
                        </li>
                        <li style="position: relative; z-index: 2;"><input class="logbutton" type="button" id="logon"><a class="f12" href="#" target="_blank"
                                                                                 style="color:#fff;">忘记密码？</a><span class="loading" id="logonLoading" style="display: none;"></span></li>
                    </ul>
                </div>
            </div>
        </td>
    </tr>
</table>
</body>
</html>