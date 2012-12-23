/**
*版权声明：贤俊工作室 版权所有 违者必究
*日    期： 2012-12-21
*作    者： 朱贤俊
*/
$(document).ready(function () {
    /**
     * 初始化编辑器
     */
    var editor = $.texteditor({
        textArea:'#articleContent',
        mode:'goods'
    });

    var editorEn = $.texteditor({
        textArea:'#articleContentEn',
        mode:'goods'
    });
    function updateDoorArticle() {
        editor.sync();
        editorEn.sync();
        var obj = $("#doorArticleForm").serializeJson();
        $.localAjax({
        url:"/admin/doorarticle/update",
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

    var config={
    reportMode:"alert",
    formDiv:"doorArticleForm",
    props:[
        {
            name:"articleCode",
            label:"文章编码",
            trim:true,
            required:true
        },
        {
            name:"articleTitle",
            label:"文章中文标题",
            trim:true,
            required:true
        },
        {
            name:"articleTitleSn",
            label:"文章英文标题",
            trim:true,
            required:true
        },
        {
            name:"articleContent",
            label:"文章中文内容",
            trim:true,
            required:true
        },
        {
            name:"articleContentEn",
            label:"文章英文内容",
            trim:true,
            required:true
        },
        {
            name:"orderNo",
            label:"序号",
            trim:true,
            required:false
            ,dataType:"int"
        }
    ]
    }
    var checkValid = $.checkValid(config);//构建验证对象


    $("#updateDoorArticle").click(function () {
    if(checkValid.checkAll()){
        updateDoorArticle();
    }
    });

    $("#_back").click(function () {
         top.jq.workgroundManager.returnPage(true);
    });

})