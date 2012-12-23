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
    <title>更新公司信息</title>
    <script type="text/javascript" src="${resourceRoot}/door/admin/js/doorarticle/update.js"></script>
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
                    <th width="150"> <em class="fstar">*</em>文章编码：</th>
                    <td><input class="ipt_250" name="articleCode" id="articleCode" value="${doorArticle.articleCode}"></td>
                </tr>
                <tr>
                    <th width="150"> <em class="fstar">*</em>文章中文标题：</th>
                    <td><input class="ipt_250" name="articleTitle" id="articleTitle" value="${doorArticle.articleTitle}"></td>
                </tr>
                <tr>
                    <th width="150"> <em class="fstar">*</em>文章英文标题：</th>
                    <td><input class="ipt_250" name="articleTitleSn" id="articleTitleSn" value="${doorArticle.articleTitleSn}"></td>
                </tr>
                <tr>
                    <th width="150"> <em class="fstar">*</em>文章中文内容：</th>
                    <td>
                        <textarea style="width: 100%; height: 300px;" name="articleContent" id="articleContent" class="textarea">
                            ${doorArticle.articleContent}
                        </textarea>
                    </td>
                </tr>
                <tr>
                    <th width="150"> <em class="fstar">*</em>文章英文内容：</th>
                    <td>
                        <textarea style="width: 100%; height: 300px;" name="articleContentEn" id="articleContentEn" class="textarea">
                            ${doorArticle.articleContentEn}
                        </textarea>
                    </td>
                </tr>
                <tr>
                    <th width="150"> 序号：</th>
                    <td><input class="ipt_250" name="orderNo" id="orderNo" value="${doorArticle.orderNo}"></td>
                </tr>
            <tr>
                <th></th>
                <td>
                    <span class="btn btn01" id="updateDoorArticle"><a href="javascript:void(0)"><b>保存</b></a></span>
                    <span class="btn btn03" id="_back"><a href="javascript:void(0)"><b>返回</b></a></span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>
</body>
</html>