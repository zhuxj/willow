/**
*版权声明：贤俊工作室 版权所有 违者必究
*日    期： 2012-12-20
*作    者： 朱贤俊
*/
$(document).ready(function () {
    function updateProductCatalog() {
        var obj = $("#productCatalogForm").serializeJson();
        $.localAjax({
        url:"/admin/productcatalog/update",
        data:obj,
        dataType:"json",
        type:"POST",
        success:function (result) {
            if (result.success == "1") {
                $.success("更新成功！", true, 3000);
                top.jq.workgroundManager.returnPage(true);
            } else {
                alert(result.msg);
            }
            }
        })
    }

    $("#updateProductCatalog").click(function () {
         updateProductCatalog();
    });

    $("#_back").click(function () {
         top.jq.workgroundManager.returnPage(true);
    });

})