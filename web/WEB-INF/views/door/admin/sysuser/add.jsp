<%
    /**
     *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
     *日    期： 12-12-14
     *作    者： 朱贤俊
     */%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>增加系统用户</title>
    <script type="text/javascript" src="${resourceRoot}/door/admin/js/sysuser/add.js"></script>
</head>
<body>
<form id="userForm">
    <div class="formlist">
        <table>
            <tbody>
            <tr>
                <th width="150"><em class="fstar">*</em>用户名：</th>
                <td><input class="ipt_250" name="userName" id="userName"></td>
            </tr>
            <tr>
                <th width="150"><em class="fstar">*</em>密码：</th>
                <td><input type="password" class="ipt_250" name="password" id="password"></td>
            </tr>
            <tr>
                <th width="150"><em class="fstar">*</em>确认密码：</th>
                <td><input type="password" class="ipt_250" name="confirmPassword" id="confirmPassword"></td>
            </tr>
            <tr>
                <th width="150">真实姓名：</th>
                <td><input class="ipt_250" name="realName" id="realName"></td>
            </tr>
            <tr>
                <th width="150"><em class="fstar">*</em>手机：</th>
                <td><input class="ipt_250" name="mobile" id="mobile"></td>
            </tr>
            <tr>
                <th width="150">电话：</th>
                <td><input class="ipt_250" name="telphone" id="telphone"></td>
            </tr>
            <tr>
                <th width="150"><em class="fstar">*</em>邮箱：</th>
                <td><input class="ipt_250" name="email" id="email"></td>
            </tr>
            <tr>
                <th></th>
                <td>
                    <span class="btn btn01" id="saveUser"><a href="javascript:void(0)"><b>保存</b></a></span>
                    <span class="btn btn03" id="_back"><a href="javascript:void(0)"><b>返回</b></a></span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>
</body>
</html>