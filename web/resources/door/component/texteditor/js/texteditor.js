/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-4-6
 *作    者： 朱贤俊
 */
(function ($) {

    /**
     * 文本编辑器
     * @param opt
     */
    $.texteditor = function (opt) {
        var defaultOpt = {
            textArea:'',
            mode:'full',
            customConfig:{}
        }
        var opt = $.extend({}, defaultOpt, opt);

        var options = {};
        var mode = opt.mode;
        switch (mode) {
            case 'ad'://广告模式
            {
                options = {
                    resizeType:1,
                    allowPreviewEmoticons:false,
                    allowImageUpload:false,
                    filterMode:true,
                    items:[
                        'image', 'flash', 'link'
                    ]
                };
                break;
            }
            case 'simple'://简单模式
            {
                options = {
                    resizeType:1,
                    allowPreviewEmoticons:false,
                    allowImageUpload:false,
                    filterMode:true,
                    items:[
                        'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                        'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                        'insertunorderedlist', '|', 'emoticons', 'image', 'link', 'fullscreen']
                };
                break;
            }
            case 'goods'://商品模式
            {
                options = {resizeMode:0, newlineTag:"p", filterMode:true, items:[
                    'source', 'preview', '|', 'undo', 'redo', 'cut', 'copy', 'paste', '|',
                    'fontname', 'fontsize', 'forecolor', 'hilitecolor', 'bold',
                    'italic', 'underline', 'strikethrough', 'lineheight', '|', 'justifyleft', 'justifycenter', 'justifyright',
                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', '|', 'image', 'flash', 'table', 'emoticons', 'hr', '|',
                    'link', 'unlink', 'removeformat', 'fullscreen'
                ]};
                break;
            }
            case 'full': //完全模式
            {
                options = {filterMode:true};
                break;
            }
            case 'custom'://自定义
            {
                options = opt.customConfig;
                break;
            }
            default://默认模式
            {
                options = {resizeMode:0, newlineTag:"p", filterMode:true, items:[
                    'source', 'preview', '|', 'undo', 'redo', 'cut', 'copy', 'paste', '|',
                    'fontname', 'fontsize', 'forecolor', 'hilitecolor', 'bold',
                    'italic', 'underline', 'strikethrough', 'lineheight', '|', 'justifyleft', 'justifycenter', 'justifyright',
                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', '|', 'image', 'table',
                    'link', 'unlink', 'removeformat', 'fullscreen'
                ]};
                break;
            }
        }

        top.editor = KindEditor.create(opt.textArea, options);
        return  top.editor;
    }
})(jQuery)