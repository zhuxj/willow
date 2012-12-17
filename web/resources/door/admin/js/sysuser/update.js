/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-12-17
 *作    者： 朱贤俊
 */
$(document).ready(function () {
    function updateUser() {
        var user = $("#userForm").serializeJson();
        $.localAjax({
            url:"/admin/sysuser/update",
            data:user,
            dataType:"json",
            type:"POST",
            success:function (result) {
                if (result.success) {
                    top.jq.workgroundManager.returnPage(true);
                } else {
                    alert(result.error_msg);
                }
            }
        })
    }

    var config = {
        reportMode:alert,
        props:[
            {
                name:"userName",
                label:"用户名",
                trim:true, //在检验长度时，是否需要先去除前后的空格，默认为true
                required:true //是否必填，默认为false;
            },
            {
                name:"email",
                label:"邮箱",
                required:true,
                dataType:"email"
            },
            {
                name:"roleIds",
                label:"角色",
                required:true
            }
        ]
    }

    var checkValid = $.checkValid(config);//构建验证对象

    $("#updateUser").click(function () {
        if (checkValid.checkAll()) {
            updateUser();
        }
    });

    $("#_back").click(function () {
        top.jq.workgroundManager.returnPage(true);
    });
})