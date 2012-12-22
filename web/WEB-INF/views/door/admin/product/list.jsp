<%
/**
*版权声明：贤俊工作室 版权所有 违者必究
*日    期： 2012-12-21
*作    者： 朱贤俊
*/
%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>产品信息列表</title>
    <script type="text/javascript"
            src="${resourceRoot}/door/admin/js/product/list.js"></script>
</head>
<body>
<div class="opbar clearfix">
    <div>
        <div>
            <form name="queryProductForm" id="queryProductForm">
                产品编号：<input class="ipt_100 mart_5" style="width: 90px;padding: 2px;"name="productSn"/>
                产品中文名称：<input class="ipt_100 mart_5" style="width: 90px;padding: 2px;"name="productName"/>
                产品英文名称：<input class="ipt_100 mart_5" style="width: 90px;padding: 2px;"name="productNameEn"/>
                产品中文规格：<input class="ipt_100 mart_5" style="width: 90px;padding: 2px;"name="productNorms"/>
                产品英文规格：<input class="ipt_100" style="width: 90px;padding: 2px;"name="productNormsEn"/>
                产品型号：<input class="ipt_100" style="width: 90px;padding: 2px;"name="productVersion"/>
                产品分类：<select class="ipt_100 mart_5" style="width: 90px;padding: 2px;"  name="catalogId">
                <option value="">请选择</option>
                <c:forEach items="${productCatalogs}" var="productCatalog">
                    <option value="${productCatalog.objId}">${productCatalog.catalogName}</option>
                </c:forEach>
                </select>
            </form>
        </div>
        <div style="text-align: center" class="mart_5">
            <span class="btnbg btn_search" id="queryOk"><a class="btnbg" href="javascript:void(0)">查询</a></span>
            <span class="btnbg btn_search" id="queryReset"><a class="btnbg" href="javascript:void(0)">重置</a></span>
        </div>
    </div>
    <div class="fl">
        <a id="addProduct" name="addProduct" href="javascript:void(0)"
           class="btn_add02 btnbg">
            <span class="btnbg"><i class="iconbg"></i>添加</span>
        </a>
    </div>
    <div class="fr">

    </div>
</div>
<div id="listProductDiv"></div>
</body>
</html>