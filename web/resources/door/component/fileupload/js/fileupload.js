/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-2-17
 *作    者： 朱贤俊
 */
(function ($) {
    var _successFun;
    var _failFun;
    var _fileElementId;
    var imageServer = imageServerUrl;
//    var imageServer = "http://127.0.0.1:8080";

    function FileUpload(opt) {
        var defaultOpt = {
            url:"",
            fileElementId:'',
            data:{orginUrl:"", companyId:'', fileSort:""},
            secureuri:false,
            dataType:'json',
            successFun:function (data) {

            },
            failFun:function (data) {

            }
        }
        this.opt = $.extend(true, defaultOpt, opt);
        _successFun = this.opt.successFun;
        _failFun = this.opt.failFun;
        _fileElementId = this.opt.fileElementId;
        this.opt.data.time = new Date().getTime();
    }

    FileUpload.prototype.upload = function () {
        this.startUpload()
        $.ajaxFileUpload(this.opt);

    }

    FileUpload.prototype.startUpload = function () {
        $("#" + this.opt.fileElementId).attr("loading", true);
    }
    $.fn.completeUpload = function () {
        this.attr("loading", false);
    }

    $.fn.isUploading = function () {
        if (this.attr("loading") == true) {
            return true;
        } else {
            return false;
        }
    }


    $.fileUploadCallBack = function (data) {
        $("#" + _fileElementId).completeUpload();
        var _data = decodeURI(data);
        var dataObj = $.parseJSON(_data);
        if (dataObj && dataObj.result == "success") {
            _successFun(dataObj);
        } else {
            _failFun(dataObj);
        }
    }
    $.imageUpload = function (opt) {
        var imageDefault = {
            fileElementId:'',
            data:{companyId:'', fileSort:""},
            successFun:function (data) {

            },
            failFun:function (data) {
                alert("图片服务器繁忙，请稍后重试...");
            }
        }
        var imageOpt = $.extend(true, imageDefault, opt);
        imageOpt.url = imageServer + "/upload.do";//图片服务上传地址
        var reg = new RegExp("^(((https|http)://)?[A-Za-z0-9.]*(:\\d*)?/?)");
        var currentSize = "";
        if (reg.test(location.href)) {
            currentSize = (RegExp.$1);
        } else {
            alert("无法获取当前域名!");
            return false;
        }
        imageOpt.data.orginUrl = currentSize + "fileResult.htm";
        var imageUploadObj = new FileUpload(imageOpt);
        imageUploadObj.upload();
    }
    /**
     * 图片客户端工具类
     */
    $.extend({
        ImageUtils:{
            Size:{
                IMAGE_SRC:"src",
                IMAGE_U:"u", //50x50图片
                IMAGE_60:"60x60",
                IMAGE_80:"80x80",
                IMAGE_S:"s", //100x100图片
                IMAGE_120:"120x120",
                IMAGE_150:"150x150",
                IMAGE_M:"m", //200x200图片
                IMAGE_L:"l", //300x300
                IMAGE_200x100:"200x100",
                IMAGE_560x200:"560x200",
                IMAGE_175x60:"175x60",
                IMAGE_100x40:"100x40",
                IMAGE_160x60:"160x60",
                IMAGE_600x600:"600x600",
                IMAGE_300x150:"300x150",
                IMAGE_250x100:"250x100",
                IMAGE_150x60:"150x60",
                IMAGE_720x250:"720x250",
                IMAGE_770x150:"770x150"
            },
            getSrcImageUrl:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_SRC);
            },
            getSmallImageUrl:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_U);
            },
            getImageUrlFor60x60:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_60);
            },
            getImageUrlFor80x80:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_80);
            },
            getMiddleImageUrl:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_S);
            },
            getImageUrlFor120:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_120);
            },
            getImageUrlFor150:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_150);
            },
            getBigImageUrl:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_M);
            },
            getLargeImageUrl:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_L);
            },
            getImageUrlFor200x100:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_200x100);
            },
            getImageUrlFor560x200:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_560x200);
            },
            getImageUrlFor175x60:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_175x60);
            },
            getImageUrlFor100x40:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_100x40);
            },
            getImageUrlFor160x60:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_160x60);
            },
            getImageUrlFor600x600:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_600x600);
            },
            getImageUrlFor300x150:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_300x150);
            },
            getImageUrlFor250x100:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_250x100);
            },
            getImageUrlFor150x60:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_150x60);
            },
            getImageUrlFor720x250:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_720x250);
            },
            getImageUrlFor770x150:function (imageId) {
                return this.getImageUrl(imageId, this.Size.IMAGE_770x150);
            },
            getImageUrl:function (imageId, type) {
                return imageServer + "/" + type + "/" + imageId + ".jpg";
            }
        }
    })

    $.fileUpload = function (opt) {

    }
})(jQuery)
