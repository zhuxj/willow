<%
    /**
    *版权声明：${codeGenConfig.developerConfig.company!} 版权所有 违者必究
    *日    期： ${date!}
    *作    者： ${codeGenConfig.developerConfig.developer!}
    */
%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>增加${codeGenConfig.table.tableName!}</title>
    <script type="text/javascript" src="${r"${resourceRoot}"}${codeGenConfig.codeGenFileConfig.jsDir!}/add.js"></script>
</head>
<body>
<form id="${codeGenConfig.table.classVariable!}Form">
    <div class="formlist">
        <table>
            <tbody>
            <#list tableClass.fieldColumns as fieldColumn>
                <#if  !fieldColumn.isIncludeField>
                <tr>
                    <th width="150">
                    <#if fieldColumn.required><em class="fstar">*</em></#if>${fieldColumn.propName!}：
                    </th>
                    <td><input class="ipt_250" name="${fieldColumn.javaProperty!}" id="${fieldColumn.javaProperty!}"></td>
                </tr>
                </#if>
            </#list>
            <tr>
                <th></th>
                <td>
                    <span class="btn btn01" id="save${codeGenConfig.table.classVar!}"><a href="javascript:void(0)"><b>保存</b></a></span>
                    <span class="btn btn03" id="_back"><a href="javascript:void(0)"><b>返回</b></a></span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>
</body>
</html>