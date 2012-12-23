<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><#if customDirectives.isEnglish>${webSite.websiteTitleEn}<#else>${webSite.websiteTitle}</#if></title>
    <meta name="keywords"
          content="<#if customDirectives.isEnglish>${webSite.websiteKeywordEn}<#else>${webSite.websiteKeyword}</#if>">
    <meta name="description"
          content="<#if customDirectives.isEnglish>${webSite.websiteDescEn}<#else>${webSite.websiteDesc}</#if>">
<#include "*/common.ftl">
<#if customDirectives.isEnglish>
    <script src="${resourceRoot}/door/front/js/template_01/en_functions.js" type="text/javascript"></script>
    <script src="${resourceRoot}/door/front/js/template_01/en_ScrollPic.js" type="text/javascript"></script>
<#else>
    <script src="${resourceRoot}/door/front/js/template_01/functions.js" type="text/javascript"></script>
    <script src="${resourceRoot}/door/front/js/template_01/ScrollPic.js" type="text/javascript"></script>
</#if>
</head>
<body>
<div id="wrapper">
    <!--head start-->
    <div id="head">
    ${customDirectives.loadModule("top",extMap)}
    ${customDirectives.loadModule("slide",extMap)}
    </div>
    <!--head end-->
${customDirectives.loadModule("indexBody",extMap)}
${customDirectives.loadModule("bottom",extMap)}

</div>
</body>
</html>
