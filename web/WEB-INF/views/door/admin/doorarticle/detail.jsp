<%
/**
*版权声明：贤俊工作室 版权所有 违者必究
*日    期： 2012-12-21
*作    者： 朱贤俊
*/
%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>查看公司信息</title>
    <script type="text/javascript" src="${resourceRoot}/door/admin/js/doorarticle/detail.js"></script>
</head>
<body>
<form id="doorArticleForm">
    <input type="hidden" id="objId" name="objId" value="${doorArticle.objId}">
    <input type="hidden" id="userId" name="userId" value="${doorArticle.userId}">
    <input type="hidden" id="createTime" name="createTime" value="${doorArticle.createTime}">
    <div class="formlist">
        <table>
            <tbody>
                <tr>
                    <th width="150">文章编码:</th>
                    <td>${doorArticle.articleCode}</td>
                </tr>
                <tr>
                    <th width="150">文章中文标题:</th>
                    <td>${doorArticle.articleTitle}</td>
                </tr>
                <tr>
                    <th width="150">文章英文标题:</th>
                    <td>${doorArticle.articleTitleSn}</td>
                </tr>
                <tr>
                    <th width="150">文章中文内容:</th>
                    <td>${doorArticle.articleContent}</td>
                </tr>
                <tr>
                    <th width="150">文章英文内容:</th>
                    <td>${doorArticle.articleContentEn}</td>
                </tr>
                <tr>
                    <th width="150">序号:</th>
                    <td>${doorArticle.orderNo}</td>
                </tr>
            <tr>
                <th></th>
                <td>
                    <span class="btn btn03" id="_back"><a href="javascript:void(0)"><b>返回</b></a></span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>
</body>
</html>