/**
*版权声明：${codeGenConfig.developerConfig.company!} 版权所有 违者必究
*日    期： ${date!}
*作    者： ${codeGenConfig.developerConfig.developer!}
*/
$(document).ready(function () {
    function update${codeGenConfig.table.classVar!}() {
        var obj = $("#${codeGenConfig.table.classVariable!}Form").serializeJson();
        $.localAjax({
        url:"/admin/${codeGenConfig.table.simplePackageVar!}/update",
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

    $("#update${codeGenConfig.table.classVar!}").click(function () {
         update${codeGenConfig.table.classVar!}();
    });

    $("#_back").click(function () {
         top.jq.workgroundManager.returnPage(true);
    });

})