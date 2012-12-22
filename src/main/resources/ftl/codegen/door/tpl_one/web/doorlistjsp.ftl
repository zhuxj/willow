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
    <title>${codeGenConfig.table.tableName!}列表</title>
    <script type="text/javascript"
            src="${r"${resourceRoot}"}${codeGenConfig.codeGenFileConfig.jsDir!}/list.js"></script>
</head>
<body>
<div class="opbar clearfix">
    <div>
        <div>
            <form name="query${codeGenConfig.table.classVar!}Form" id="query${codeGenConfig.table.classVar!}Form">
            <#list tableClass.fieldColumns as fieldColumn>
                <#if  !fieldColumn.isIncludeField>
                ${fieldColumn.propName!}：<input class="ipt_100 mart_5" style="width: 90px;padding: 2px;"name="${fieldColumn.javaProperty!}"/>
                </#if>
            </#list>
            </form>
        </div>
        <div style="text-align: center" class="mart_5">
            <span class="btnbg btn_search" id="queryOk"><a class="btnbg" href="javascript:void(0)">查询</a></span>
            <span class="btnbg btn_search" id="queryReset"><a class="btnbg" href="javascript:void(0)">重置</a></span>
        </div>
    </div>
    <div class="fl">
        <a id="add${codeGenConfig.table.classVar!}" name="add${codeGenConfig.table.classVar!}" href="javascript:void(0)"
           class="btn_add02 btnbg">
            <span class="btnbg"><i class="iconbg"></i>添加</span>
        </a>
    </div>
    <div class="fr">

    </div>
</div>
<div id="list${codeGenConfig.table.classVar!}Div"></div>
</body>
</html>