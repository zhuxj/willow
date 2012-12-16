<%
    if (request.getParameter("isAjax") == null || Boolean.valueOf(request.getParameter("isAjax")) != true) {
%>
<script type="text/javascript">
    var resourceRoot = '${resourceRoot}';
    var imageServerUrl = '';
</script>
<script src="${resourceRoot}/js/lib/jquery/jquery-1.6.3.min.js" type="text/javascript"></script>
<%--日期选择器--%>
<script src="${resourceRoot}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

<%--图片上传--%>
<script type="text/javascript" src="${resourceRoot}/door/component/fileupload/js/fileupload.js"></script>

<script src="${resourceRoot}/js/lib/jquery/plugins/jquery.cookie.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/utils/cookieUtil.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/lib/jquery/plugins/ajaxfileupload.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/utils/jquery.paginationPlug.js" type="text/javascript"></script>

<script src="${resourceRoot}/door/component/ejqgrid/jqgrid/grid.locale-cn.js" type="text/javascript"></script>
<script src="${resourceRoot}/door/component/ejqgrid/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="${resourceRoot}/door/component/ejqgrid/js/ejqGrid.js" type="text/javascript"></script>

<script src="${resourceRoot}/js/utils/ajax.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/utils/jquery.utils.js" type="text/javascript"></script>
<script src="${resourceRoot}/door/utils/contentManager.js" type="text/javascript"></script>

<%--文本编辑器--%>
<script src="${resourceRoot}/door/component/texteditor/kindeditor/kindeditor-min.js" type="text/javascript"></script>
<script src="${resourceRoot}/door/component/texteditor/kindeditor/lang/zh_CN.js" type="text/javascript"></script>
<script src="${resourceRoot}/door/component/texteditor/js/texteditor.js" type="text/javascript"></script>

<%--jdialog--%>
<script src="${resourceRoot}/door/component/jdialog/js/jqDnR.js" type="text/javascript"></script>
<script src="${resourceRoot}/door/component/jdialog/js/dimensions.js" type="text/javascript"></script>
<script src="${resourceRoot}/door/component/jdialog/js/jqModal.js" type="text/javascript"></script>
<script src="${resourceRoot}/door/component/jdialog/js/jdialog.js" type="text/javascript"></script>
<script type="text/javascript" src="${resourceRoot}/door/component/egrid/js/egrid.js"></script>

<%--验证表单--%>
<script type="text/javascript" src="${resourceRoot}/door/component/checkValid/js/checkValid.js"></script>

<script src="${resourceRoot}/js/lib/jquery/plugins/highcharts.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/lib/jquery/plugins/exporting.js" type="text/javascript"></script>

<script src="${resourceRoot}/js/utils/sha1.js" type="text/javascript"></script>
<%
    }
%>
