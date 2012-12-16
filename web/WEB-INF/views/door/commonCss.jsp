<%
    if (request.getParameter("isAjax") == null || Boolean.valueOf(request.getParameter("isAjax")) != true) {
%>
<link type="text/css" rel="stylesheet" href="${resourceRoot}/door/admin/skins/default/css/public.css"/>
<link type="text/css" rel="stylesheet" href="${resourceRoot}/door/admin/skins/default/css/layout.css"/>
<link type="text/css" rel="stylesheet" href="${resourceRoot}/door/admin/skins/default/css/mend.css"/>
<link type="text/css" rel="stylesheet" href="${resourceRoot}/door/admin/skins/default/css/module.css"/>
<link type="text/css" rel="stylesheet" href="${resourceRoot}/door/admin/skins/default/css/print.css"/>
<link type="text/css" rel="stylesheet" href="${resourceRoot}/door/admin/skins/default/css/style.css"/>
<link type="text/css" rel="stylesheet" href="${resourceRoot}/door/admin/skins/default/css/manageindex.css"/>
<link rel="stylesheet" type="text/css" media="screen"
      href="${resourceRoot}/door/component/ejqgrid/jqgrid/jquery-ui-1.8.2.custom.css"/>
<link rel="stylesheet" type="text/css" media="screen"
      href="${resourceRoot}/door/component/ejqgrid/jqgrid/css/ui.jqgrid.css"/>
<link type="text/css" rel="stylesheet" href="${resourceRoot}/door/component/jdialog/css/jdialog.css"/>
<link href="${resourceRoot}/door/component/egrid/css/egrid.css" rel="stylesheet" type="text/css"/>
<%
    }
%>