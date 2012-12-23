/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-2-6
 *作    者： 朱贤俊
 */
    //---------历史记录管理器  开始---------
function HistoryManager(opt) {
    var defaultOpt = {

    }
    this.opt = $.extend(defaultOpt, opt);
}

/**
 * 获取锚点的值
 */
HistoryManager.prototype.getHash = function () {
    var a = location.href;
    if (a.indexOf("?") > -1) {
        a.replace("?", "#");
    }
    var b = a.indexOf("#") + 1;
    return(b) ? a.substr(b) : ""
}
/**
 * 改变网店后台的url，通过锚点
 * @param opt
 */
HistoryManager.prototype.setHash = function (hash) {
    location.hash = hash;
}

/**
 * 解析url的锚点
 */
HistoryManager.prototype.parseUrlHash = function () {
    var _hash = location.hash.slice(1);
    var e = _hash.match("/([a-zA-Z]*)(/.*)");
    return e;
}


//---------历史记录管理器  结束---------

//---------内容显示管理器  开始---------
function ContentManager(opt) {
    var defaultOpt = {
        workground:"",
        loadMask:"loadMask",
        basePath:"",
        defultUrl:"",
        url:"",
        historyManager:{},
        resourceRoot:"",
        hash:true
    }
    this.opt = $.extend(defaultOpt, opt);
    this.historyManager = new HistoryManager();
}

/**
 * 设置内容显示管理对象参数
 * @param opt
 */
ContentManager.prototype.setOpt = function (opt) {
    this.opt = $.extend(this.opt, opt);
}

/**
 * 当内容显示时
 * @param opt  参数对象
 */
ContentManager.prototype.onStartLoadUrl = function () {
    $("#" + this.opt.loadMask).show();
}

/**
 * 当内容显示后，加载内容
 * @param opt  参数对象
 */
ContentManager.prototype.loadUrl = function (opt) {
    if (top.isLoading) {
        return;
    }
    top.isLoading = true;
    if ($.workgroundManager) {
        $.workgroundManager.showMainPage();
    }
    this.onStartLoadUrl();
    this.setOpt(opt);
    if (this.opt.hash) {
        this.historyManager.setHash(this.opt.url);
        if (this.historyManager.getHash() == "") {
            this.opt.url = this.opt.defultUrl
            this.load();
        } else {
            this.opt.url = this.historyManager.getHash();
            this.load();
        }
    } else {
        this.load();
        this.opt.hash = true;
    }

}
/**
 * 正式加载内容并显示
 */
ContentManager.prototype.load = function () {
    if (this.opt.target && this.opt.target == "_blank") {
        window.open(this.opt.basePath + this.opt.url, "_blank");
        top.isLoading = false;
        $("#" + this.opt.loadMask).hide();
        this.opt.target = false;
        this.historyManager.setHash("");
        return false;
    }

    var tempObj = this;
    var timeStamp = new Date().getTime();
    $("#" + this.opt.workground).empty();
    $("#" + this.opt.workground).load(this.opt.basePath + this.opt.url, {timeStamp:timeStamp, isAjax:true}, function (response, status, xhr) {
        tempObj.onComplete(response, status, xhr);
    });
    return true;
}

/**
 * 加载内容完成后
 */
ContentManager.prototype.onComplete = function (response, status, xhr) {
    var contentManagerObj = this;
    if (status === 'error') {
        var result = $.evalJSON(response);
        if (result.noLogon == 'NOLOGON') {
            var uuid = result.uuid;
            var orignUrl = result.orignUrl;
            if (result.type == "admin") {
                $.showLogonDialog(uuid, orignUrl, function () {
                    contentManagerObj.loadUrl(contentManagerObj.opt);
                });
            }
        } else {
            var errorInfo = $('<div class="error">页面未找到...</div>');
            $('body').append(errorInfo);
            var si = setInterval(function () {
                errorInfo.animate({
                    opacity:0.25,
                    height:'-=0',
                    top:'-=200'
                }, 1000, function () {
                    errorInfo.remove();
                    clearInterval(si);
                });
            }, 1000);
        }
    } else if (status === 'timeout') {
        alert("请求超时，请稍后再试...");
    }
    top.isLoading = false;
    $("#" + this.opt.loadMask).hide();
}
ContentManager.prototype.refresh = function () {
    this.loadUrl(this.opt);
}