/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-2-16
 *作    者： 高能力
 */

$(document).ready(function () {
    $("#conheight").css("height",$("#workground").height()-$(".bot_opbar").height()-15);
    //初始化显示图片
    $("#logoImage").attr("src",$.ImageUtils.getImageUrlFor160x60($("#logoImageId").val()));
    //更新
    $("#editWebSite").click(function () {

        var websiteName = $("#websiteName").val();
        var websiteTitle = $("#websiteTitle").val();
        var websiteDesc = $("#websiteDesc").val();
        var websiteKeyword = $("#websiteKeyword").val();

        var websiteNameEn = $("#websiteNameEn").val();
        var websiteTitleEn = $("#websiteTitleEn").val();
        var websiteDescEn = $("#websiteDescEn").val();
        var websiteKeywordEn = $("#websiteKeywordEn").val();


        var logoImageId = $("#logoImageId").val();

        var serviceEmail = $("#serviceEmail").val();
        var serviceFax = $("#serviceFax").val();
        var serviceTel = $("#serviceTel").val();
        var workTime = $("#workTime").val();
        var websiteIcp = $("#websiteIcp").val();
        var templateId = $("#templateId").val();

        var shutDown = $("input[name='shutDown']:checked").val();

        if(websiteName=="undefined"||$.trim(websiteName)==""){
            $.alert("网站名称不能为空");
            return;
        }
        if(websiteTitle=="undefined"||$.trim(websiteTitle)==""){
            $.alert("网站标题不能为空");
            return;
        }
        if(websiteDesc=="undefined"||$.trim(websiteDesc)==""){
            $.alert("网站描述不能为空");
            return;
        }

        //调用ajax保存数据成功
        $.localAjax({
            url:"/admin/website/webSiteEdit",
            data:{
                websiteName:websiteName,
                websiteTitle:websiteTitle,
                websiteDesc:websiteDesc,
                websiteKeyword:websiteKeywordEn,
                websiteNameEn:websiteNameEn,
                websiteTitleEn:websiteTitleEn,
                websiteDescEn:websiteDescEn,
                websiteKeywordEn:websiteKeywordEn,
                logoImageId:logoImageId,
                serviceEmail:serviceEmail,
                serviceFax:serviceFax,
                serviceTel:serviceTel,
                workTime:workTime,
                websiteIcp:websiteIcp,
                templateId:templateId,
                shutDown:shutDown
            },
            dataType:"json",
            type:"post",
            success:function (result) {
                $.success("修改成功", true, 5000);
            },
            error:function(){
                $.alert("服务器繁忙，请稍后重试");
            }
        });
    });
});
