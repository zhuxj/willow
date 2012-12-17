<%
    /**
     *版权声明：中图一购网络科技有限公司 版权所有 违者必究 2012
     *日    期： 12-3-19
     *作    者：
     *功能说明：
     *
     */%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--[30,59,1] published at 2009-04-30 21:10:13 from #194 by system-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>网站参数设置</title>
    <script src="${resourceRoot}/door/admin/js/website/websiteEdit.js" type="text/javascript"></script>
</head>
<body>
<div id="conheight">
    <div class="formlist">
        <INPUT type="hidden" id="companyId" value="${webSite.companyId}"/>
        <table>
            <tbody>
            <tr>
                <th width="120"><em class="fstar">*</em>网站名称：</th>
                <td><input class="ipt_250" name="websiteName" id="websiteName" value="${webSite.websiteName}"></td>
            </tr>
            <tr>
                <th width="120"><em class="fstar">*</em>网站英文名称：</th>
                <td><input class="ipt_250" name="websiteNameEn" id="websiteNameEn" value="${webSite.websiteNameEn}"></td>
            </tr>
            <tr>
                <th><em class="fstar">*</em>网站标题：</th>
                <td><input class="ipt_300" id="websiteTitle" name="websiteTitle" value="${webSite.websiteTitle}"></td>
            </tr>
            <tr>
                <th><em class="fstar">*</em>网站英文标题：</th>
                <td><input class="ipt_300" id="websiteTitleEn" name="websiteTitleEn" value="${webSite.websiteTitleEn}"></td>
            </tr>
            <tr>
                <th><em class="fstar">*</em>网站描述：</th>
                <td><textarea class="textarea" id="websiteDesc">${webSite.websiteDesc}</textarea></td>
            </tr>
            <tr>
                <th><em class="fstar">*</em>网站英文描述：</th>
                <td><textarea class="textarea" id="websiteDescEn">${webSite.websiteDescEn}</textarea></td>
            </tr>
            <tr>
                <th>网站关键字：</th>
                <td><input class="ipt_400" name="websiteKeyword" id="websiteKeyword"
                           value="${webSite.websiteKeyword}"><span class="fgray9">&nbsp;请以英文逗号分离</span></td>
            </tr>
            <tr>
                <th>网站英文关键字：</th>
                <td><input class="ipt_400" name="websiteKeywordEn" id="websiteKeywordEn"
                           value="${webSite.websiteKeywordEn}"><span class="fgray9">&nbsp;请以英文逗号分离</span></td>
            </tr>
            <tr style="display: none;">
                <th>网站模板ID：</th>
                <td><input class="ipt_100" name="templateId" id="templateId" value="${webSite.templateId}"></td>
            </tr>

            <tr>
                <th></th>
                <td></td>
            </tr>

            <tr>
                <th>客服邮箱：</th>
                <td><input class="ipt_100" name="serviceEmail" id="serviceEmail" value="${webSite.serviceEmail}"></td>
            </tr>
            <tr>
                <th>客服电话：</th>
                <td><input class="ipt_100" name="serviceTel" id="serviceTel" value="${webSite.serviceTel}"></td>
            </tr>
            <tr>
                <th>客服传真：</th>
                <td><input class="ipt_100" name="serviceFax" id="serviceFax" value="${webSite.serviceFax}"></td>
            </tr>
            <tr>
                <th>工作时间：</th>
                <td><textarea class="textarea" name="workTime" id="workTime">${webSite.workTime}</textarea></td>
            </tr>

            <tr>
                <th>暂时关闭网站：</th>
                <td><INPUT class="cbox" type="radio"
                           <c:if test="${webSite.shutDown=='Y'}">CHECKED</c:if> value='Y' name="shutDown"/> 是
                    <INPUT class="cbox" type="radio" value='N'
                           <c:if test="${webSite.shutDown=='N' or webSite == null}">CHECKED</c:if> name="shutDown"/> 否
                </td>
            </tr>

            <tr>
                <th>ICP备案证书号：</th>
                <td><input class="ipt_250" name="websiteIcp" id="websiteIcp"
                           value="${webSite.websiteIcp}">&nbsp;&nbsp;<span class="fgray9">例如：浙ICP备07506676号</span></td>

            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="bot_opbar clearfix">
    <span id="editWebSite" class="btn btn01"><a href="javascript:void(0)"><b>保存</b></a></span>
</div>
</body>
</html>