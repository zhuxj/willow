/**
*版权声明：贤俊工作室 版权所有 违者必究
*日    期： 2012-12-21
*作    者： 朱贤俊
*/
$(document).ready(function () {
    function saveProductCatalog() {
        var obj = $("#productCatalogForm").serializeJson();
        $.localAjax({
            url:"/admin/productcatalog/save",
            data:obj,
            dataType:"json",
            type:"POST",
            success:function (result) {
                if (result.success == "1") {
                    $.success("增加成功！", true, 3000);
                    top.jq.workgroundManager.returnPage(true);
                } else {
                    alert(result.msg);
                }
            }
        })
    }
    var config={
    reportMode:"alert",
    formDiv:"productCatalogForm",
    props:[
        {
            name:"catalogName",
            label:"分类名称",
            trim:true,
            required:true
        },
        {
            name:"catalogNameEn",
            label:"分类英文名称",
            trim:true,
            required:true
        }
    ]
    }
    var checkValid = $.checkValid(config);//构建验证对象
    $("#saveProductCatalog").click(function () {
        if(checkValid.checkAll()){
            saveProductCatalog();
        }
    });

    $("#_back").click(function () {
        top.jq.workgroundManager.returnPage(true);
    });

})