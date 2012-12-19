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
    <script type="text/javascript" src="${r"${resourceRoot}"}${codeGenConfig.codeGenFileConfig.jsDir!}/list.js"></script>
</head>
<body>
<div class="opbar clearfix">
    <a id="add${codeGenConfig.table.classVar!}" name="add${codeGenConfig.table.classVar!}" href="javascript:void(0)" class="btn_add02 btnbg">
        <span class="btnbg"><i class="iconbg"></i>添加</span>
    </a>
</div>
<div id="list${codeGenConfig.table.classVar!}Div"></div>
</body>
</html>