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
    <title>增加产品信息</title>
    <script type="text/javascript" src="${resourceRoot}/door/admin/js/product/add.js"></script>
</head>
<body>
<form id="productForm">
    <div class="formlist">
        <table>
            <tbody>
                <tr>
                    <th width="150">产品编号：</th>
                    <td><input class="ipt_250" name="productSn" id="productSn"></td>
                </tr>
                <tr>
                    <th width="150">产品中文名称：</th>
                    <td><input class="ipt_250" name="productName" id="productName"></td>
                </tr>
                <tr>
                    <th width="150">产品英文名称：</th>
                    <td><input class="ipt_250" name="productNameEn" id="productNameEn"></td>
                </tr>
                <tr>
                    <th width="150">产品中文规格：</th>
                    <td><input class="ipt_250" name="productNorms" id="productNorms"></td>
                </tr>
                <tr>
                    <th width="150">产品英文规格：</th>
                    <td><input class="ipt_250" name="productNormsEn" id="productNormsEn"></td>
                </tr>
                <tr>
                    <th width="150">产品型号：</th>
                    <td><input class="ipt_250" name="productVersion" id="productVersion"></td>
                </tr>
                <tr>
                    <th width="150">产品分类：</th>
                    <td><input class="ipt_250" name="catalogId" id="catalogId"></td>
                </tr>
                <tr>
                    <th width="150">产品中文详情：</th>
                    <td><input class="ipt_250" name="productDesc" id="productDesc"></td>
                </tr>
                <tr>
                    <th width="150">产品英文详情：</th>
                    <td><input class="ipt_250" name="productDescEn" id="productDescEn"></td>
                </tr>
                <tr>
                    <th width="150">产品图片：</th>
                    <td><input class="ipt_250" name="productImage" id="productImage"></td>
                </tr>
                <tr>
                    <th width="150">浏览次数：</th>
                    <td><input class="ipt_250" name="browseTime" id="browseTime"></td>
                </tr>
            <tr>
                <th></th>
                <td>
                    <span class="btn btn01" id="saveProduct"><a href="javascript:void(0)"><b>保存</b></a></span>
                    <span class="btn btn03" id="_back"><a href="javascript:void(0)"><b>返回</b></a></span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>
</body>
</html>