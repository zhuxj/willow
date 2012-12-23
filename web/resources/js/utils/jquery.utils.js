/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-1-18
 *作    者： 朱贤俊
 */
jQuery.extend({
    workgroundManager:{
        opt:{
            workgroundMain:"workground",
            workgroundSec:"workgroundSec",
            basePath:"",
            url:"",
            onReurnPage:function () {

            },
            onChanged:function () { //改变时触发

            },
            onLoadSuccess:function () {

            }
        },
        setOpt:function (opt) {
            this.opt = $.extend(this.opt, opt);
        },
        openPage:function (opt) {
            this.load(opt);
        },
        refreshPage:function () {
            this.load(this.opt);
        },
        returnPage:function (isChanged) {
            if (top.editor) {  //如果存在kindeditor编辑器，则删除
                top.editor.remove();
            }
            $("#" + this.opt.workgroundMain).show();
            if (isChanged) {
                this.opt.onChanged();
            } else {
                this.opt.onReurnPage();
            }
            $("#" + this.opt.workgroundSec).hide();
            $("#" + this.opt.workgroundSec).empty();
        },
        showMainPage:function () {
            if (top.editor) {  //如果存在kindeditor编辑器，则删除
                top.editor.remove();
            }
            $("#" + this.opt.workgroundMain).show();
            $("#" + this.opt.workgroundSec).hide();
            $("#" + this.opt.workgroundSec).empty();
        },
        load:function (opt) {
            $("#loadMask").show();
            this.opt = $.extend(this.opt, opt);
            if ("" == $.trim(this.opt.url)) {
                return;
            }
            var timeStamp = new Date().getTime();
            var defaultParams = {timeStamp:timeStamp};
            var params = $.extend(defaultParams, opt.params);
            var _workgroundManager = this;
            $("#" + this.opt.workgroundSec).load(this.opt.basePath + this.opt.url, params, function (response, status, xhr) {
                $("#loadMask").hide();
                if (status === 'error') {
                    var result = $.evalJSON(response);
                    //检查是否未登录
                    if (xhr.status == 406 && result.noLogon == 'NOLOGON') {
                        var uuid = result.uuid;
                        var orignUrl = result.orignUrl;
                        $.showLogonDialog(uuid, orignUrl, function () {
                            top.$.workgroundManager.load(opt);
                        });
                    } else {
                        alert("页面错误");
                    }
                } else if (status === 'timeout') {
                    alert("请求超时，请稍后再试...");
                } else if (status === 'success') {
                    $("#" + _workgroundManager.opt.workgroundMain).hide();
                    $("#" + _workgroundManager.opt.workgroundSec).show();
                    _workgroundManager.opt.onLoadSuccess();
                }
            });
        }
    },
    evalJSON:function (strJson) {//转换为json 对象
        return eval("(" + strJson + ")");
    },

    isURL:function (url) {
        var strRegex = "^((https|http|ftp|rtsp|mms)://)?([a-z0-9A-Z]{3})?\.[a-z0-9A-Z][a-z0-9A-Z]{0,61}?[a-z0-9A-Z]\.com|net|cn|cc (:s[0-9]{1-4})?/$";
        var re = new RegExp(strRegex);
        if (re.test(url)) {
            return true;
        } else {
            return false;
        }
    },

    toJsonString:function (o) {

        function arrayToJSON(arrayVal) {
            var a = ['['], // The array holding the text fragments.
                b, // A boolean indicating that a comma is required.
                i, // Loop counter.
                l = arrayVal.length,
                v;          // The value to be stringified.

            function p(s) {

                // p accumulates text fragments in an array. It inserts a comma before all
                // except the first fragment.

                if (b) {
                    a.push(',');
                }
                a.push(s);
                b = true;
            }

            // For each value in arrayVal array...

            for (i = 0; i < l; i += 1) {
                v = arrayVal[i];
                p(toJSONString(v));
            }

            // Join all of the fragments together and return.

            a.push(']');
            return a.join('');
        }

        function objectToJSON(objectVal) {
            var a = ['{'], // The array holding the text fragments.
                b, // A boolean indicating that a comma is required.
                k, // The current key.
                v;          // The current value.

            function p(s) {

                // p accumulates text fragment pairs in an array. It inserts a comma before all
                // except the first fragment pair.

                if (b) {
                    a.push(',');
                }
                a.push(toJSONString(k), ':', s ? s : "null");
                b = true;
            }

            // Iterate through all of the keys in the object, ignoring the proto chain.

            for (k in objectVal) {
                if (objectVal.hasOwnProperty(k)) {
                    v = objectVal[k];
                    p(toJSONString(v));
                }
            }

            // Join all of the fragments together and return.

            a.push('}');
            return a.join('');
        }

        function strToJSON(str) {
            var m = {
                '\b':'\\b',
                '\t':'\\t',
                '\n':'\\n',
                '\f':'\\f',
                '\r':'\\r',
                '"':'\\"',
                '\\':'\\\\'
            };

            // If the string contains no control characters, no quote characters, and no
            // backslash characters, then we can simply slap some quotes around it.
            // Otherwise we must also replace the offending characters with safe
            // sequences.

            if (/["\\\x00-\x1f]/.test(str)) {
                return '"' + str.replace(/([\x00-\x1f\\"])/g, function (a, b) {
                    var c = m[b];
                    if (c) {
                        return c;
                    }
                    c = b.charCodeAt();
                    return '\\u00' +
                        Math.floor(c / 16).toString(16) +
                        (c % 16).toString(16);
                }) + '"';
            }
            return '"' + str + '"';
        }

        function boolToJSON(bool) {
            return String(bool);
        }

        function dateToJSON(dateVal) {

            // Ultimately, this method will be equivalent to the date.toISOString method.

            function f(n) {

                // Format integers to have at least two digits.

                return n < 10 ? '0' + n : n;
            }

            return '"' + dateVal.getFullYear() + '-' +
                f(dateVal.getMonth() + 1) + '-' +
                f(dateVal.getDate()) + 'T' +
                f(dateVal.getHours()) + ':' +
                f(dateVal.getMinutes()) + ':' +
                f(dateVal.getSeconds()) + '"';
        }

        function numberToJSON(numberVal) {

            // JSON numbers must be finite. Encode non-finite numbers as null.

            return isFinite(numberVal) ? String(numberVal) : "null";
        }

        function toJSONString(anything) {
            switch ($.type(anything)) {

                // Serialize a JavaScript object value. Ignore objects thats lack the
                // toJSONString method. Due to a specification error in ECMAScript,
                // typeof null is 'object', so watch out for that case.
                case 'array':
                    return arrayToJSON(anything);
                case 'object':
                    if (anything) {
                        if ($.type(anything) == "array") {
                            return arrayToJSON(anything);
                        } else if ($.type(anything) == "date") {
                            return dateToJSON(anything);
                        } else {
                            return objectToJSON(anything);
                        }
                    } else {
                        return "null";
                    }
                    break;

                case 'string':
                    return strToJSON(anything);
                case 'number':
                    return numberToJSON(anything);
                case 'boolean':
                    return boolToJSON(anything);
                default:
                    return "";
            }
        }

        function parseJSON(str, filter) {
            var j;

            function walk(k, v) {
                var i;
                if (v && typeof v === 'object') {
                    for (i in v) {
                        if (v.hasOwnProperty(i)) {
                            v[i] = walk(i, v[i]);
                        }
                    }
                }
                return filter(k, v);
            }


            // Parsing happens in three stages. In the first stage, we run the text against
            // a regular expression which looks for non-JSON characters. We are especially
            // concerned with '()' and 'new' because they can cause invocation, and '='
            // because it can cause mutation. But just to be safe, we will reject all
            // unexpected characters.

            if (/^("(\\.|[^"\\\n\r])*?"|[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t])+?$/.
                test(str)) {

                // In the second stage we use the eval function to compile the text into a
                // JavaScript structure. The '{' operator is subject to a syntactic ambiguity
                // in JavaScript: it can begin a block or an object literal. We wrap the text
                // in parens to eliminate the ambiguity.

                try {
                    j = eval('(' + str + ')');
                } catch (e) {
                    throw new SyntaxError("parseJSON");
                }
            } else {
                throw new SyntaxError("parseJSON");
            }

            // In the optional third stage, we recursively walk the new structure, passing
            // each name/value pair to a filter function for possible transformation.

            if (typeof filter === 'function') {
                j = walk('', j);
            }
            dump(toJSONString(j));
            return j;
        }

        return toJSONString(o);
    },
    /**
     *功能： 延时执行函数
     *参数：fn执行的函数, when多久执行一次, periodic是否重复执行, o对象, data函数参数(多参数用数组)
     *返回：执行当前函数的对象，cancel方法可以结束当前方法（重复执行则执行下一次）
     */
    later:function (fn, when, periodic, o, data) {
        when = when || 0;
        o = o || {};
        var m = fn, d = $.makeArray(data), f, r;
        if (typeof fn === 'string') {
            m = o[fn];
        }
        if (!m) {
            $.error('method undefined');
        }
        f = function () {
            m.apply(o, d);
        };
        r = (periodic) ? setInterval(f, when) : setTimeout(f, when);
        return {
            id:r,
            interval:periodic,
            cancel:function () {
                if (this.interval) {
                    clearInterval(r);
                } else {
                    clearTimeout(r);
                }
            }
        };
    },
    /**
     * 取得url的参数
     * @param name
     */
    getQueryStringRegExp:function (name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(location.href)) {
            return decodeURI(RegExp.$2.replace(/\+/g, " "));
        }
        return "";
    },
    //后台session超时，继续操作，弹出登陆框让用户登录
    showLogonDialog:function (uuid, url, callback) {

        if ($("#logonDialog")[0]) {
            return;
        }
        debugger

        var contentStr = "<div class='mart_20' id='reLogonFormDiv'>" +

            "<ul style='padding-left: 40px;'>" +
            "<input type='hidden' id='logonId' name='logonId' value='" + uuid + "'/>" +
            "<input type='hidden' id='orignUrl' name='orignUrl' value='" + url + "'/>" +

            "<li class='pad_5 clearfix'><span class='fl f14'>用户名：</span><input class='ipt_200 fl' id='userName' name='userName'/></li>" +
            "<li class='pad_5 clearfix'><span class='fl f14'>密　码：</span><input class='ipt_200 fl' type='password' id='password' name='password'/></li>" +
            "</ul>" +
            "</div>";
        var content = $(contentStr);
        var userNameEle = content.find("#userName");
        var passwordEle = content.find("#password");

        userNameEle.keyup(function (event) {
            onEnterLogon(event);
        });
        passwordEle.keyup(function (event) {
            onEnterLogon(event);
        });

        function onEnterLogon(event) {
            if (event.keyCode == 13) {
                var dialogObj = $.getActiveDialog();
                onLogon(dialogObj);
            }
        }

        function onLogon(dialogObj) {
            var logonId = $("#reLogonFormDiv #logonId").val();
            var userName = userNameEle.val();
            var password = passwordEle.val();

            if (userName == "") {
                alert("登录名不能为空");
                userNameEle.focus();
                return false;
            }
            if (password == "") {
                alert("密码不能为空");
                passwordEle.focus();
                return false;
            }
            var orignUrl = $("#orignUrl").val();

            var data = {};
            data.userName = userName;
            data.pwd = hex_sha1(password);
            data.logonId = logonId;

            $.localAjax({
                url:"/admin/onLogon",
                data:data,
                dataType:'json',
                type:'post',
                success:function (result) {
                    if (result && result.resultCode == "1") {
                        dialogObj.confirm();
                        if ($.isFunction(callback)) {
                            callback();
                        }
                    }

                    /*验证码错误*/
                    if (typeof(result.resultCode) != "undefined") {
                        if (result.resultCode == '2') {
                            alert("验证码错误！");

                            return;
                        }
                    }

                    /*密码不正确*/
                    if (typeof(result.resultCode) != "undefined") {
                        if (result.resultCode == '3' || result.resultCode == '9') {
                            alert("用户名或密码错误！");
                            //清空密码
                            $("#password").val("");

                            return;
                        }
                    }

                }
            });
        }

        $.jDialog({id:"logonDialog",
            width:"380",
            height:'150',
            title:"重新登录",
            content:content,
            modal:true,
            needAction:true,
            bottomClass:"dcen",
            actions:[
                {
                    label:"确 定",
                    actionClasss:'popbtn popbtn01',
                    action:function (dialogObj) {
                        onLogon(dialogObj);
                    }
                }
            ]

        });

    },
    /**
     * 加入收藏夹方法
     * @param title
     * @param url
     * @return {Boolean}
     */
    addBookMark:function (title, url) {
        if (window.sidebar) {
            window.sidebar.addPanel(title, url, "");
        } else if (document.all) {
            window.external.AddFavorite(url, title);
        } else if (window.opera && window.print) {
            alert("对不起，您的浏览器不支持此操作，请使用菜单栏或CTRL+D收藏本站!");
            return true;
        } else if (window.chrome) {
            alert("对不起，您的浏览器不支持此操作，请使用菜单栏或CTRL+D收藏本站!");
        } else {
            alert("对不起，您的浏览器不支持此操作，请使用菜单栏或CTRL+D收藏本站!");
        }
    },
    getDomain:function () {
        var reg = new RegExp("^(((https|http)://)?[A-Za-z0-9.]*(:\\d*)?/?)");
        var mainUrl = "";
        if (reg.test(location.href)) {
            mainUrl = (RegExp.$1);
        }
        return mainUrl;
    },

    /**
     * 转成保存数字
     * @param number
     * @return num*100
     */
    toSaveMultNumber:function (number) {
        var newNumber = "";
        if (number.indexOf(".") >= 0) {
            var i = number.indexOf(".");
            var end = number.substring(i + 1);
            newNumber += number.substring(0, i);
            if (end.length >= 2) {
                newNumber += number.substring(i + 1, i + 3);
            } else {
                newNumber += end + "0";
            }
        } else {
            newNumber += number + "00";
        }
        return newNumber;
    },
    getReturnUrl:function () {
        var returnUrl = $.getQueryStringRegExp("returnUrl");
        if (returnUrl == "") {
            returnUrl = window.location;
        }
        $.cookie("logon_returnUrl", returnUrl, {expires:30, path:'/'});
    },

    //判断密码强度，返回数值1、2、3      1：弱 2：中 3：强  -4:长度小于6为  -2：与用户名一样
    checkPasswordStrength:function (password) {
        //CharMode函数
        //测试某个字符是属于哪一类.
        function CharMode(iN) {
            if (iN >= 48 && iN <= 57) //数字
                return 1;
            if (iN >= 65 && iN <= 90) //大写字母
                return 2;
            if (iN >= 97 && iN <= 122) //小写
                return 4;
            else
                return 8; //特殊字符
        }

        //bitTotal函数
        //计算出当前密码当中一共有多少种模式
        function bitTotal(num) {
            modes = 0;
            for (i = 0; i < 4; i++) {
                if (num & 1) modes++;
                num >>>= 1;
            }
            return modes;
        }

        //checkStrong函数
        //返回密码的强度级别

        function checkStrong(sPW) {
            if (sPW.length <= 6) {
                return 1; //密码太短
            }
            Modes = 0;
            for (i = 0; i < sPW.length; i++) {
                //测试每一个字符的类别并统计一共有多少种模式.
                Modes |= CharMode(sPW.charCodeAt(i));
            }
            return bitTotal(Modes);
        }

        return checkStrong(password);
    }
});

(function ($) {
    /**
     * 将表单元素变成json对象
     */
    $.fn.serializeJson = function () {
        var fields = this.serializeArray();
        var param = {};
        $.each(fields, function (i, field) {
            param[field.name] = field.value;
        });
        return param;
    }

    /**
     * 将表单元素变成json对象
     */
    $.fn.reSetForm = function () {
        var fields = this.serializeArray();
        var param = {};
        $.each(fields, function (i, field) {
            $("[name=" + field.name + "]").val("");
        });
        return param;
    }
    $.fn.extend({

    })

    $.fn.showSelectDataDiv = function (opt) {
        var _this = $(this);
        _this.empty();
        var defaultOpt = {
            onSaveHandler:function (gridObj) {

            },
            onBackHandler:function () {

            },
            onAfterBuildHandler:function (gridObj) {

            },
            gridOpt:{
                needPageBar:true,
                pageSize:5,
                showTableIndex:true
            }
        };
        _this.opt = $.extend(true, defaultOpt, opt);
        if (_this.opt.queryConditionDiv != undefined && _this.opt.queryConditionDiv != "") {
            var queryConditonDiv = $('<div class="pop-op"></div>').appendTo(_this);
            queryConditonDiv.append(_this.opt.queryConditionDiv);
        }

        var _id = _this.attr("id");
        var gridId = _id + "_list";
        var _dataListDiv = $("<div id='" + gridId + "'></div>").appendTo(_this);
        _this.opt.gridOpt.container = gridId;
        var gridObj = $.egrid(_this.opt.gridOpt);
        var _operDiv = $('<div class="dcen pad_20"></div>').appendTo(_this);
        var _saveOper = $('<a href="javaScript:void(0)" class="ed-btn01" id="' + _id + '_save' + '"><span><b>确定</b></span></a>');
        var _backOper = $('<a href="javaScript:void(0)" class="ed-btn02 marl_5" id="' + _id + '_back' + '"><span>返回</span></a>');
        _operDiv.append(_saveOper);
        _operDiv.append(_backOper);
        _saveOper.click(function () {
            if ($.isFunction(_this.opt.onSaveHandler)) {
                _this.opt.onSaveHandler(gridObj);
            }
        })
        _backOper.click(function () {
            if ($.isFunction(_this.opt.onBackHandler)) {
                _this.opt.onBackHandler();
            }
        });
        if ($.isFunction(_this.opt.onAfterBuildHandler)) {
            _this.opt.onAfterBuildHandler(gridObj);
        }
        return gridObj;
    }

})(jQuery)
/**
 * 扩展javascript数组的方法
 * @param index
 * @param value
 */
Array.prototype.insertAt = function (index, value) {
    var part1 = this.slice(0, index);
    var part2 = this.slice(index);
    part1.push(value);
    return( part1.concat(part2) );
};

Array.prototype.removeAt = function (index) {
    var part1 = this.slice(0, index);
    var part2 = this.slice(index);
    part1.pop();
    return( part1.concat(part2) );
}

String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "g"), s2);
}
