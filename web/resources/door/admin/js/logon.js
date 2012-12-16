/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-3-10
 *作    者： 朱贤俊
 */
$(document).ready(function () {
    init();
    function init() {
        setCurrentMenu("", "", "");
        $("#userName").focus();
        $(".inputbox").live("keyup", function (event) {
            if (event.keyCode == 13) {
                if ($(this).attr("id") == 'verifyCode') {
                    $('#logon').click();
                } else {
                    $(this).parent().next().find(".inputbox").focus();
                }
            }
        });

        var config = {props:[
            {
                name:"userName",
                label:"用户名",
                trim:true,
                required:true
            },
            {
                name:"password",
                label:"密码",
                trim:true,
                required:true
            },
            {
                name:"verifyCode",
                label:"验证码",
                trim:true,
                required:true
            }
        ]};
        var checkValid = $.checkValid(config);
        $('#logon').click(function () {
            if (checkValid.checkAll()) {
                logoning();
                var userName = $("#userName").val();
                var password = hex_sha1($("#password").val());
                var logonId = $("#logonId").val();
                var verifyCode = $("#verifyCode").val();
                var data = {};
                data.userName = userName;
                data.pwd = password;
                data.verifyCode = verifyCode;
                data.logonId = logonId;
                $.localAjax({
                    url:"/shopadmin/onLogon",
                    data:data,
                    dataType:'json',
                    type:'post',
                    success:function (result) {
                        logonend();

                        if (result && result.resultCode == "1") {
                            location.href = "/shopadmin/index.html";
                        }

                        /*验证码错误*/
                        if (typeof(result.resultCode) != "undefined") {
                            if (result.resultCode =='2') {
                                alert("验证码错误！");
                                //获取图片验证码
                                getCodeImage();
                                //清空验证码
                                $("#verifyCode").val("");
                                return;
                            }
                        }

                        /*密码不正确*/
                        /*if (typeof(result.resultCode) != "undefined") {
                            if (result.resultCode =='3') {
                                alert("密码错误！");
                                //获取图片验证码
                                getCodeImage();

                                //清空密码
                                $("#password").val("");
                                //清空验证码
                                $("#verifyCode").val("");

                                return;
                            }
                        }*/

                        /*用户名不正确*/
                        if (typeof(result.resultCode) != "undefined") {
                            if (result.resultCode =='9'||result.resultCode =='3') {
                                alert("用户名或者密码错误！");
                                //获取图片验证码
                                getCodeImage();

                                //清空密码
                                $("#password").val("");
                                //清空验证码
                                $("#verifyCode").val("");

                                return;
                            }
                        }
                    }
                });
            }
        });

        //获取cookie中的用户名，
        var userName = getLogonUserName();
        $("#userName").val(userName);
    }

    $("#imgRepeat").click(function(){
        getCodeImage();
        return false;
    });
    //获取图片验证码
//    getCodeImage();
});


//正在登录
function logoning(){

    if($("#logon").hasClass("disabled")){
        return;
    }
    $("#logonLoading").show();
    $("#logon").addClass("disabled");

}

//登录结束（不管是登录成功或者失败）
function logonend(){
    $("#logon").removeClass("disabled");
    $("#logonLoading").hide();
}

/**
 * 调用ajax去获取图片验证码
 * @param logonId
 */
function getCodeImage(){
    var logonId = $("#logonId").val();
    $("#imgCode").attr("src","/shopadmin/getImageCode?logonId="+logonId+"&randomNum="+Math.random());
}

