/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-1-19
 *作    者： 朱贤俊
 */
(function ($) {
    var DIALOG_OFFSET = 15;

    /**
     * 窗口对象
     * @param opt
     */
    function jDialog(opt) {
        var _jDialog = this;
        this.dialog_data = [];
        var defaultOpt = {
            id:"dialog",
            needTitle:true,
            title:"",
            url:"",
            content:"",
            _ajax:false,
            _ajaxdata:{},
            modal:true,
            width:"600",
            height:"200",
            needAction:false,
            bottomClass:"dright",
            actions:[],
            hideOpt:{
                properties:{opacity:"hide"},
                duration:1,
                easing:null,
                complete:function (h) {

                }
            },
            onClosed:function (h, dialogObj) {

            },
            onConfirm:function (dialogObj) {

            },
            onCancel:function (dialogObj) {

            }
        };
        this.opt = $.extend(true, defaultOpt, opt);
        this.opt.width = this.opt.width.indexOf("px") > -1 ? this.opt.width : this.opt.width + "px";
        this.opt.height = this.opt.height.indexOf("px") > -1 ? this.opt.height : this.opt.height + "px";
        this.opt.actions.push({
            label:"关 闭",
            actionClasss:'popbtn popbtn02',
            action:function (dialogObj) {
                dialogObj.dialog.jqmHide();
            }
        });
        this.dialog = this.makeDialogDiv();
        this.setDialogPostion();
        //jModal的参数化对象
        var _overlay = 15; //默认透明度
        if ($.getDialogsSize() > 0) {
            _overlay = 0;
        }
        this.jmOpt = $.extend({}, {
            overlay:_overlay,
            closeClass:"pop_close",
            onHide:function (h) {
                _jDialog.destroy(h, _jDialog);
            },
            onShow:function (h) {
                h.w.css("z-index", h.w.css("z-index") + (h.s * 2));
                h.o.css("z-index", h.w.css("z-index") - 1);
                h.w.show();
            }
        }, this.opt);
        this.dialog.jqm(this.jmOpt).jqDrag(".jqDrag").jqmShow();

        this.loadContent();
    }


    jDialog.prototype.saveData = function (key, value) {
        this.dialog_data[key] = value;
    }

    jDialog.prototype.removeData = function (key) {
        delete this.dialog_data[key];
    }

    jDialog.prototype.getData = function (key) {
        return this.dialog_data[key];
    }

    jDialog.prototype.cancel = function () {
        if ($.isFunction(this.opt.onCancel)) {
            this.opt.onCancel(this);
        }
        this.dialog.jqmHide();
    }

    jDialog.prototype.confirm = function () {
        if ($.isFunction(this.opt.onConfirm)) {
            this.opt.onConfirm(this);
        }
        this.dialog.jqmHide();
    }
    /**
     * 销毁窗口
     * @param h
     * @param dialogObj
     */
    jDialog.prototype.destroy = function (h, dialogObj) {
        this.opt.onClosed(h, dialogObj);
        var hideOpt = this.opt.hideOpt;
        h.w.animate(hideOpt.properties, hideOpt.duration, hideOpt.easing, function () {
            hideOpt.complete(h);
            h.w.remove();
        });
        if (h.o) {
            h.o.remove();
        }
        $.removeDialog(dialogObj.methods);
    }

    /**
     * 加载内容
     */
    jDialog.prototype.loadContent = function () {
        var _jDialog = this;
        _jDialog.loading.show();
        var _content = this.opt.content;
        if ($.trim(_content) == "") {//当content为空串时
            if (this.opt._ajax) {
                _content = $("<div style='height: 100%;'></div>");
                var timeStamp = new Date().getTime();
                var defaultParams = {timeStamp:timeStamp, isAjax:true};
                defaultParams = $.extend(defaultParams, this.opt._ajaxdata);
                _content.load(this.opt.url, defaultParams, function (response, status, xhr) {
                    if (status === 'error') {
                        alert("页面未找到!");
                    } else if (status === 'timeout') {
                        alert("请求超时，请稍后再试...");
                    }
                    _jDialog.loading.hide();
                });
            } else {
                this._contentDiv.removeClass("pad_15 pop_bd_scroll");
                var _iframe = $('<iframe style="width:100%;height:100%;margin: 0;border: 0;" src="' + this.opt.url + '"></iframe>');
                _iframe.load(function () {
                    _jDialog.loading.hide();
                });
                _content = _iframe;
            }
        } else {
            if (_content instanceof jQuery) {//当content为jQuery对象时

            } else {//当content不为空的字符串
                _content = $("<div>" + _content + "</div>");
            }
            _jDialog.loading.hide();
        }

        this._contentDiv.append(_content);
    }

    /**
     * 计算设置窗口位置
     */
    jDialog.prototype.setDialogPostion = function () {
        var scrollTop = $.browser.msie ? document.documentElement.scrollTop : document.body.scrollTop;
        var dialogOffset = $.getDialogsSize() * DIALOG_OFFSET; //窗口位置偏移量
        var _top = scrollTop + ($(window).height() - this.dialog.height()) / 2 + dialogOffset;

        var scrollLeft = $.browser.msie ? document.documentElement.scrollLeft : document.body.scrollLeft;
        var _left = scrollLeft + ($(window).width() - this.dialog.width()) / 2 + dialogOffset;
        this.dialog.css("top", _top);
        this.dialog.css("left", _left);
    }

    jDialog.prototype.makeDialogDiv = function () {
        var _dialogDiv = $('<div class="pop jqmWindow" style="width:' + this.opt.width + ';" id="' + this.opt.id + '"></div>').appendTo(top.$("body"));
        _dialogDiv.append(this.makeDialogInnerDiv());
        return _dialogDiv;
    }

    jDialog.prototype.makeDialogInnerDiv = function () {
        var _innerDiv = $('<div class="pop_inner"></div>');
        if (this.opt.needTitle) {
            _innerDiv.append(this.makeDialogTitleDiv());
        }
        _innerDiv.append(this.makeDialogContentDiv());
        if (this.opt.needAction) {
            _innerDiv.append(this.makeDialogBottomDiv());
        }
        return _innerDiv;
    }

    jDialog.prototype.makeDialogTitleDiv = function () {
        var _titleDiv = $('<div class="pop_hd jqDrag"><strong>' + this.opt.title + '</strong><a class="pop_close" href="javascript:void(0)" title="关闭"></a></div>');
        return _titleDiv;
    }

    jDialog.prototype.makeDialogContentDiv = function () {
        var _contentDiv = $('<div class="pop_bd pad_15 pop_bd_scroll" style="height:' + this.opt.height + ';"></div>');
        var loading = $('<div class="loading"><img src="' + resourceRoot + '/skins/shopadmin/images/loading.gif" />正在加载...</div>').appendTo(_contentDiv);
        loading.hide();
        this._contentDiv = _contentDiv;
        this.loading = loading;
        return _contentDiv;
    }

    jDialog.prototype.makeDialogBottomDiv = function () {
        var _jDialog = this;
        var _bottomDiv = $(' <div class="pop_bot ' + _jDialog.opt.bottomClass + '"></div>');
        $.each(this.opt.actions, function (index, action) {
            var _action = $('<span class="' + action.actionClasss + '"><a href="javascript:void(0)">' + action.label + '</a></span>');
            _action.click(function () {
                action.action(_jDialog);
            });
            _bottomDiv.append(_action);
        });
        return _bottomDiv;
    }

    function Methods() {

    }

    /**
     * 定义开放对话框的方法
     */
    jDialog.prototype.openMehtods = function () {
        var _jDialog = this;
        var methods = new Methods();
        methods.saveData = function (key, value) {
            _jDialog.saveData(key, value);
        }

        methods.removeData = function (key) {
            _jDialog.removeData(key);
        }
        methods.getData = function (key) {
            return  _jDialog.getData(key);
        }

        methods.getOpt = function () {
            return   _jDialog.opt;
        }

        methods.getDialogDiv = function () {
            return  _jDialog.dialog;
        }
        methods.cancel = function () {
            _jDialog.cancel();
        }
        methods.confirm = function () {
            _jDialog.confirm();
        }
        this.methods = methods;
        return methods;
    }


    var jDialogs = [];//保存打开的窗体列表
    /**
     * 构造窗口对象并显示
     * @param opt
     *  id:"" 对话框的ID
     *  title:"" 对话框的标题
     *  content:"" 内容区的内容
     *  url: "" 如果content为空时适用，如果 content不为空，则使用content
     *  _ajax:false,是否使用ajax加载内容区
     *  modal:true,
     *  width:"600",窗口宽度
     *  height:"200", 窗口高度
     *  needAction:false,底部是否需要按钮
     *  actions:[ 定义底部按钮
     *   {
     *     label:"关 闭",
     *     actionClasss:'btn btn04_s',
     *     action:function (dialogObj) {}
     *   }],
     *  hideOpt:{//关闭效果
     *  onClosed:function (h, dialogObj) {
     *
     *  },
     * onConfirm:function (dialogObj) {
     *
     * },
     * onCancel:function (dialogObj) {
     *
     * }
     *  }
     */
    $.jDialog = function (opt) {
        var _jDialog = new jDialog(opt);
        var methods = _jDialog.openMehtods();
        $.addDialog(methods);
        return methods;
    }

    $.addDialog = function (_jDialog) {
        jDialogs.push(_jDialog);
    }

    $.removeDialog = function (_jDialog) {
        jDialogs = $.grep(jDialogs, function (n, i) {
            return n != _jDialog;
        });
    }
    /**
     * 获取激活的窗体
     */
    $.getActiveDialog = function () {
        return jDialogs[jDialogs.length - 1];
    }

    $.getAllDialogs = function () {
        return jDialogs;
    }

    $.getDialogsSize = function () {
        return jDialogs.length;
    }

    /**
     * 信息显示窗口
     * @param content
     * @param options
     * @param width
     * @param height
     */
    $.info = function (content, options, width, height) {
        var _options = $.extend({}, {
            imageClass:"",
            background:"#fff",
            autoClosed:false, //是否自动关闭
            closeTime:3000, //3秒钟自动关闭
            onClosed:function () {
            }
        }, options);
        var _content = $("<div style='text-align: center;  padding: 30px 0 0;'></div>");
        if (content instanceof jQuery) {
            _content.append(content);
        } else {
            var _p = $('<p class="f14"></p>').appendTo(_content);
            var _span = $('<span class="' + _options.imageClass + '"></span>').appendTo(_p);
            _p.append($("<b>" + content + "</b>"));
        }
        //自动关闭
        if (_options.autoClosed) {
            var mi = _options.closeTime / 1000;
            var _autoClosed_div = $('<div>' + mi + '秒后自动关闭' + '</div>').appendTo(_content);
            var bt = $("<span style='padding-left:10px;'><b>" + mi + "</b></span>");
            var si = setInterval(function () {
                mi = mi - 1;
                bt.find("*").remove();
                bt.append('<b>' + mi + '</b>');
                if (mi == 0) {
                    clearInterval(si)
                    _dialogMethods.confirm();
                }
            }, 1000);
            _autoClosed_div.append(bt);
        }

        width = width ? width + "" : "300";
        height = height ? height + "" : "80";
        var _alertOpt = {
            width:width,
            height:height,
            content:_content,
            id:"alert",
            needTitle:false,
            needAction:true,
            bottomClass:"dcen",
            modal:true,
            onClosed:_options.onClosed
        }
        var _dialogMethods = $.jDialog(_alertOpt);
        _content.parent().removeClass("pad_15 pop_bd_scroll");
        _content.parent().css("background", _options.background);
    }

    /**
     * 提示框
     * @param content 内容
     * @param autoClosed 是否自动关闭
     * @param closeTime  自动关闭的时间
     * @param width      宽度
     * @param height     高度
     */
    $.alert = function (content, autoClosed, closeTime, width, height) {
        $.info(content, {
            imageClass:"pop_icon_tip",
            background:"#FFEDAB",
            autoClosed:autoClosed,
            closeTime:closeTime
        }, width, height);
    }

    /**
     * 成功信息提示框
     * @param content 内容
     * @param autoClosed 是否自动关闭
     * @param closeTime  自动关闭的时间
     * @param width      宽度
     * @param height     高度
     */
    $.success = function (content, autoClosed, closeTime, onClosed, width, height) {
        $.info(content, {
            imageClass:"pop_icon_right",
            background:"#E8F3FF",
            autoClosed:autoClosed,
            closeTime:closeTime,
            onClosed:onClosed
        }, width, height);
    }
    /**
     * 错误信息提示框
     * @param content 内容
     * @param autoClosed 是否自动关闭
     * @param closeTime  自动关闭的时间
     * @param width      宽度
     * @param height     高度
     */
    $.error = function (content, autoClosed, closeTime, width, height) {
        $.info(content, {
            imageClass:"pop_icon_tip",
            background:"#FFE3D6",
            autoClosed:autoClosed,
            closeTime:closeTime
        }, width, height);
    }


    if (!top.dialog_messages) {
        top.dialog_messages = {};
    }

    /**
     * 单次消息保存
     * @param key 消息主键
     * @param msg 消息信息
     */
    $.saveFlash = function (key, msg) {
        top.dialog_messages[key] = msg;
    }

    /**
     * 单次消息读取
     * @param key 消息主键
     */
    $.loadFlash = function (key) {
        var msg = top.dialog_messages[key];
        delete top.dialog_messages[key];
        return msg;
    }

    $.getFlash = function (key) {
        var msg = top.dialog_messages[key];
        return msg;
    }

    $.removeFlash = function (key) {
        delete top.dialog_messages[key];
    }

    /**
     * 提醒框
     * @param opt
     */
    $.fn.tipBox = function (opt) {
        var _self = this;
        var defaultOpt = {
            type:"oneRow",
            content:"",
            rows:[],
            title:""
        }
        this.opt = $.extend(true, defaultOpt, opt);
        var tipContent = $("");
        switch (this.opt.type) {
            case "oneRow"://一行
            {
                _self.append('<p class="tipbox_a">' + _self.opt.content + '</p>');
                break;
            }
            case "multiRow"://多行
            {
                var _contentArr = [];
                $.each(this.opt.rows, function (index, row) {
                    var r = (index + 1) + "." + row.content;
                    _contentArr.push(r);
                });
                _self.append('<p class="tipbox_b">' + _contentArr.join("<br/>") + '</p>');
                break;
            }
            case "canClose"://可一行，多行和可关闭
            {
                _self.addClass("tipbox_cbox");
                var _div = $('<div class="tipbox_c"><div class="tipbox_c_hd"><span></span></div></div>').appendTo(_self);
                var closeButton = $('<a class="tipbox_c_close" href="javascript:void(0)" title="关闭"></a>');
                closeButton.click(function () {
                    _div.hide();
                });
                _div.append(closeButton);
                var _bd = $('<div class="tipbox_c_bd"></div>').appendTo(_div);
                var title = $('<h4>' + _self.opt.title + '</h4>').appendTo(_bd);
                var _bdp = $('<p>' + _self.opt.content + '</p>').appendTo(_bd);
                if (!_self.opt.content) {//当content为空时
                    var _contentArr = [];
                    $.each(this.opt.rows, function (index, row) {
                        var r = (index + 1) + "." + row.content;
                        _contentArr.push(r);
                    });
                    _bdp.append(_contentArr.join("<br/>"));
                }
                var iknow = $('<a href="javascript:void(0)">[我知道了]</a>').appendTo(_bdp);
                iknow.click(function () {
                    _div.hide();
                });
                var bot = $('<div class="tipbox_c_bot"></div>').appendTo(_div);
                break;
            }

        }
    }

})(jQuery)