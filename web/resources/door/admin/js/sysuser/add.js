/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-12-16
 *作    者： 朱贤俊
 */
$(document).ready(function () {
    function saveSysUser() {
        var user = $("#userForm").serializeJson();
        user.password = hex_sha1($("#password").val());
        user.confirmPassword = hex_sha1($("#confirmPassword").val());
        $.localAjax({
            url:"/admin/sysuser/save",
            data:user,
            dataType:"json",
            type:"POST",
            success:function (result) {
                if (result.success == "1") {
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
                name:"password",
                label:"密码",
                trim:true,
                required:true,
                lengthRange:{min:"6", max:"30"}
            },
            {
                name:"confirmPassword",
                label:"确认密码",
                trim:true,
                required:true
            } ,
            {
                name:"mobile",
                label:"手机",
                required:true,
                dataType:"tel"
            },
            {
                name:"email",
                label:"邮箱",
                required:true,
                dataType:"email"
            }
        ]
    }

    var checkValid = $.checkValid(config);//构建验证对象

    $("#saveUser").click(function () {
        if (checkValid.checkAll()) {
            saveSysUser();
        }
    });

    $("#_back").click(function () {
        top.jq.workgroundManager.returnPage(true);
    });

})