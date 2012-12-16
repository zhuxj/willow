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
        function getCodeImage() {
            var logonId = $("#logonId").val();
            $("#imgCode").attr("src", "/shopadmin/getImageCode?logonId=" + logonId + "&randomNum=" + Math.random());
        }

        if ($("#logonDialog")[0]) {
            return;
        }


        var contentStr = "<div class='mart_20' id='reLogonFormDiv'>" +

            "<ul style='padding-left: 40px;'>" +
            "<input type='hidden' id='logonId' name='logonId' value='" + uuid + "'/>" +
            "<input type='hidden' id='orignUrl' name='orignUrl' value='" + url + "'/>" +

            "<li class='pad_5 clearfix'><span class='fl f14'>用户名：</span><input class='ipt_200 fl' id='userName' name='userName'/></li>" +
            "<li class='pad_5 clearfix'><span class='fl f14'>密　码：</span><input class='ipt_200 fl' type='password' id='password' name='password'/></li>" +
            "<li class='pad_5 clearfix'><span class='fl f14'>验证码：</span><input class='ipt_50 fl' type='text' id='verifyCode' name='verifyCode'/>" +
            "<img class='fl' style='margin: 2px 5px 0;' title='看不清验证码，请再次点击图片！' id='imgCode' src='/shopadmin/getImageCode?logonId=" + uuid + "'/>" +
            "<a href='javascript:void(0)' id='imgRepeat'>换一张&nbsp;</a></li>" +
            "</ul>" +

            "</div>";
        var content = $(contentStr);
        content.find("#imgRepeat").click(getCodeImage);

        var userNameEle = content.find("#userName");

        var passwordEle = content.find("#password");

        var verifyCodeEle = content.find("#verifyCode");
        userNameEle.keyup(function (event) {
            onEnterLogon(event);
        });
        passwordEle.keyup(function (event) {
            onEnterLogon(event);
        });
        verifyCodeEle.keyup(function (event) {
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
            var verifyCode = verifyCodeEle.val();

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
            if (verifyCode == "") {
                alert("验证码不能为空");
                verifyCodeEle.focus();
                return false;
            }
            var orignUrl = $("#orignUrl").val();

            var data = {};
            data.userName = userName;
            data.pwd = hex_sha1(password);
            data.verifyCode = verifyCode;
            data.logonId = logonId;

            $.localAjax({
                url:"/shopadmin/onLogon",
                data:data,
                dataType:'json',
                type:'post',
                success:function (result) {
                    if (result && result.resultCode == "1") {
                        dialogObj.confirm();
//                                        alert("应该跳转到:"+orignUrl);
                        if ($.isFunction(callback)) {
                            callback();
                        }
                    }

                    /*验证码错误*/
                    if (typeof(result.resultCode) != "undefined") {
                        if (result.resultCode == '2') {
                            alert("验证码错误！");
                            //获取图片验证码
                            getCodeImage();
                            //清空验证码
                            $("#verifyCode").val("");
                            return;
                        }
                    }

                    /*密码不正确*/
                    if (typeof(result.resultCode) != "undefined") {
                        if (result.resultCode == '3' || result.resultCode == '9') {
                            alert("用户名或密码错误！");
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

    //前台session超时，继续操作，弹出登陆框让用户登录
    showFrontLogonDialog:function (uuid, url, callback) {
        _callback = callback;
        var dialogObj = $.jDialog({id:"logonDialog",
            width:"600",
            height:'240',
            title:"您尚未登录",
            _ajax:true,
            _ajaxdata:{uuid:uuid, url:url},
            url:"/member/memberLogonDialog",
            modal:true,
            needAction:false,
            bottomClass:"dcen"
        });

    },

    //前台配送商session超时，继续操作，弹出登陆框让配送商登录
    showDistributorLogonDialog:function (uuid, url, callback) {
        _callback = callback;
        var dialogObj = $.jDialog({id:"logonDialog",
            width:"600",
            height:'240',
            title:"您尚未登录",
            _ajax:true,
            _ajaxdata:{uuid:uuid, url:url},
            url:"/distributor/distributorUserLogonDialog",
            modal:true,
            needAction:false,
            bottomClass:"dcen"
        });

    },

    //显示选择标准分类的框框
    showStandardCatalogDialog:function (catLibCode, callback) {
        var contentStr = "<div id='standardCatalogContent' class='appbox_list'></div>";
        var content = $(contentStr);

        /**
         * @param parentCatId 要获取的父分类ID
         * @param appendId 要将加载的子分类添加到那个节点id
         * @param content 内容
         */
        function getChildrenCatalog(parentCatId, appendNode) {

            $.localAjax({
                url:"/sys/getChildrenCatalog",
                data:{parentCatId:parentCatId},
                dataType:"json",
                type:"post",
                success:function (result) {
                    var cats = result.data;
                    if (cats.length < 1) {
                        var jiajian = appendNode.children("div").find("i");
                        jiajian.removeClass();
                        jiajian.addClass("appbox_td_spa04");
                        jiajian.attr("title", "");
                        return false;
                    }

                    var ulNode = $("<ul class='clearfix'></ul>");
                    for (var i = 0; i < cats.length; i++) {
                        var cat = cats[i];
                        var liNode = $("<li class='clearfix' catName='" + cat.name + "' id='" + cat.catalogId + "' catCode='" + cat.catCode + "'><div class='clearfix'><i class='app_jia' title='展开'></i><span class='fl' style='cursor: pointer;'>" + cat.name + "</span></div></li>");
                        ulNode.append(liNode);

                        liNode.click(function (event) {
                            var currentNode = $(this);

                            $("li", content).removeClass("currentSelected");
                            currentNode.addClass("currentSelected");

                            $("li span", content).removeClass("cat_current");
                            currentNode.children("div").find("span").addClass("cat_current");

                            var jiajian = currentNode.children("div").find("i");
                            if (jiajian.hasClass("appbox_td_spa04")) {
                                return false;
                            }

                            var ul = currentNode.children("ul");
                            var isShow = currentNode.attr("isShow");

                            if (isShow == "show") {
                                ul.hide();
                                currentNode.attr("isShow", "hide");
                                jiajian.removeClass();
                                jiajian.addClass("app_jia");
                                jiajian.attr("title", "展开");
                                return false;
                            } else if (isShow == "hide") {
                                ul.show();
                                currentNode.attr("isShow", "show");
                                jiajian.removeClass();
                                jiajian.addClass("app_jian");
                                jiajian.attr("title", "收起");

                                return false;
                            }
                            if (!isShow) {
                                currentNode.attr("isShow", "show");
                                jiajian.removeClass();
                                jiajian.addClass("app_jian");
                                jiajian.attr("title", "收起");
                            } else {
                                return false;
                            }

                            getChildrenCatalog(currentNode.attr("id"), currentNode);
                            //阻止事件冒泡
                            event.preventDefault();
                            return false;
                        });

                        //////////如果该分类为图书分类，则默认点击
                        if (cat.catalogId == "64e924aa-8449-11e1-aa0a-000b2f35ecf4") {
                            liNode.click();
                        }
                        //////////

                        liNode.dblclick(function (event) {
                            var currentNode = $(this);
                            var catCode = currentNode.attr("catCode");
                            var catName = currentNode.attr("catName");
                            var catId = currentNode.attr("id");

                            var dialogObj = $.getActiveDialog();
                            if ($.isFunction(callback)) {
                                var flag = callback({catCode:catCode, catName:catName, catId:catId});
                                if (flag == undefined || flag == true) {
                                    dialogObj.confirm();
                                }
                            } else {
                                dialogObj.confirm();
                            }
                            //阻止事件冒泡
                            event.preventDefault();
                            return false;
                        });
                        if (catLibCode != "") {
                            if (cat.catCode == catLibCode) {
                                liNode.click();
                            } else {
                                if (catLibCode.substr(0, cat.catCode.length) == cat.catCode) {
                                    liNode.click();
                                }
                            }
                        }


                    }
                    appendNode.append(ulNode);
                }
            });

        }


        getChildrenCatalog("ROOT", content);

        var dialogObj = $.jDialog({id:"logonDialog",
            width:"400",
            height:'400',
            title:"标准分类选择",
            content:content,
            modal:true,
            needAction:true,
            bottomClass:"dcen",
            actions:[
                {
                    label:"确 定",
                    actionClasss:'popbtn popbtn01',
                    action:function (dialogObj) {
                        var currentNode = content.find(".currentSelected");
                        var catCode = currentNode.attr("catCode");
                        var catName = currentNode.attr("catName");
                        var catId = currentNode.attr("id");

                        if ($.isFunction(callback)) {
                            var flag = callback({catCode:catCode, catName:catName, catId:catId});
                            if (flag == undefined || flag == true) {
                                dialogObj.confirm();
                            }
                        } else {
                            dialogObj.confirm();
                        }
                    }
                }
            ]

        });

    },
    //本地分类选择
    showLocalCatalogDialog:function (catCode, callback, url) {
        var contentStr = "<div id='localCatalogContent' class='appbox_list'></div>";
        var content = $(contentStr);

        /**
         * @param parentId 要获取的父分类ID
         * @param appendId 要将加载的子分类添加到那个节点id
         * @param content 内容
         */
        function getChildrenCatalog(parentId, appendNode, url) {
            if (!url) {
                url = "/shopadmin/goodsCatalog/getLocalCat";
            }
            $.localAjax({
                url:url,
                data:{parentId:parentId},
                dataType:"json",
                type:"post",
                success:function (result) {
                    var localCats = result.data;
                    if (localCats.length < 1) {
                        var jiajian = appendNode.children("div").find("i");
                        jiajian.removeClass();
                        jiajian.addClass("appbox_td_spa04");
                        jiajian.attr("title", "");
                        return false;
                    }

                    var ulNode = $("<ul class='clearfix'></ul>");
                    for (var i = 0; i < localCats.length; i++) {
                        var cat = localCats[i];
                        var liNode = $("<li class='clearfix' catName='" + cat.catName + "' id='" + cat.catId + "' catCode='" + cat.catCode + "'><div class='clearfix'><i class='app_jia' title='展开'></i><span class='fl' style='cursor: pointer;'>" + cat.catName + "</span></div></li>");
                        ulNode.append(liNode);

                        liNode.click(function (event) {
                            var currentNode = $(this);

                            $("li", content).removeClass("currentSelected");
                            currentNode.addClass("currentSelected");

                            $("li span", content).removeClass("cat_current");
                            currentNode.children("div").find("span").addClass("cat_current");

                            var jiajian = currentNode.children("div").find("i");
                            if (jiajian.hasClass("appbox_td_spa04")) {
                                return false;
                            }

                            var ul = currentNode.children("ul");
                            var isShow = currentNode.attr("isShow");

                            if (isShow == "show") {
                                ul.hide();
                                currentNode.attr("isShow", "hide");
                                jiajian.removeClass();
                                jiajian.addClass("app_jia");
                                jiajian.attr("title", "展开");
                                return false;
                            } else if (isShow == "hide") {
                                ul.show();
                                currentNode.attr("isShow", "show");
                                jiajian.removeClass();
                                jiajian.addClass("app_jian");
                                jiajian.attr("title", "收起");

                                return false;
                            }
                            if (!isShow) {
                                currentNode.attr("isShow", "show");
                                jiajian.removeClass();
                                jiajian.addClass("app_jian");
                                jiajian.attr("title", "收起");
                            } else {
                                return false;
                            }

                            getChildrenCatalog(currentNode.attr("id"), currentNode, url);
                            //阻止事件冒泡
                            event.preventDefault();
                            return false;
                        });

                        /*//////////如果该分类为图书分类，则默认点击
                         if (cat.catalogId == "64e924aa-8449-11e1-aa0a-000b2f35ecf4") {
                         liNode.click();
                         }
                         //////////*/

                        liNode.dblclick(function (event) {
                            var currentNode = $(this);
                            var catCode = currentNode.attr("catCode");
                            var catName = currentNode.attr("catName");
                            var catId = currentNode.attr("id");

                            var dialogObj = $.getActiveDialog();
                            if ($.isFunction(callback)) {
                                var flag = callback({catCode:catCode, catName:catName, catId:catId});
                                if (flag == undefined || flag == true) {
                                    dialogObj.confirm();
                                }
                            } else {
                                dialogObj.confirm();
                            }
                            //阻止事件冒泡
                            event.preventDefault();
                            return false;
                        });
                        if (catCode != "" && catCode != null) {
                            if (cat.catCode == catCode) {
                                liNode.click();
                            } else {
                                if (catCode.substr(0, cat.catCode.length) == cat.catCode) {
                                    liNode.click();
                                }
                            }
                        }


                    }
                    appendNode.append(ulNode);
                }
            });

        }


        getChildrenCatalog("ROOT", content, url);

        var dialogObj = $.jDialog({id:"logonDialog",
            width:"400",
            height:'400',
            title:"本地分类选择",
            content:content,
            modal:true,
            needAction:true,
            bottomClass:"dcen",
            actions:[
                {
                    label:"确 定",
                    actionClasss:'popbtn popbtn01',
                    action:function (dialogObj) {
                        var currentNode = content.find(".currentSelected");
                        var catCode = currentNode.attr("catCode");
                        var catName = currentNode.attr("catName");
                        var catId = currentNode.attr("id");

                        if ($.isFunction(callback)) {
                            var flag = callback({catCode:catCode, catName:catName, catId:catId});
                            if (flag == undefined || flag == true) {
                                dialogObj.confirm();
                            }
                        } else {
                            dialogObj.confirm();
                        }
                    }
                },
                {
                    label:"清 空",
                    actionClasss:'popbtn popbtn02',
                    action:function (dialogObj) {
                        if ($.isFunction(callback)) {
                            var flag = callback({catCode:"", catName:"", catId:""});
                            if (flag == undefined || flag == true) {
                                dialogObj.confirm();
                            }
                        } else {
                            dialogObj.confirm();
                        }
                    }
                }
            ]

        });

    },

    //配送商本地分类选择
    showShopCatalogDialog:function(callback, url) {
        var contentStr = "<div id='localCatalogContent' class='appbox_list'></div>";
        var content = $(contentStr);
        /**
         * @param parentId 要获取的父分类ID
         * @param appendId 要将加载的子分类添加到那个节点id
         * @param content 内容
         */
        function getChildrenCatalog(parentId, appendNode, url) {
            if (!url) {
                url = "/distributor/shopCatalog/queryByParentId";
            }
            $.localAjax({
                url:url,
                data:{parentId:parentId},
                dataType:"json",
                type:"post",
                success:function (result) {
                    var localCats = result.data;
                    if (localCats.length < 1) {
                        var jiajian = appendNode.children("div").find("i");
                        jiajian.removeClass();
                        jiajian.addClass("appbox_td_spa04");
                        jiajian.attr("title", "");
                        return false;
                    }

                    var ulNode = $("<ul class='clearfix'></ul>");
                    for (var i = 0; i < localCats.length; i++) {
                        var cat = localCats[i];
                        var liNode = $("<li class='clearfix' catName='" + cat.catalogName + "' id='" + cat.catalogId + "'><div class='clearfix'><i class='app_jia' title='展开'></i><span class='fl' style='cursor: pointer;'>" + cat.catalogName + "</span></div></li>");
                        ulNode.append(liNode);

                        liNode.click(function (event) {
                            var currentNode = $(this);

                            $("li", content).removeClass("currentSelected");
                            currentNode.addClass("currentSelected");

                            $("li span", content).removeClass("cat_current");
                            currentNode.children("div").find("span").addClass("cat_current");

                            var jiajian = currentNode.children("div").find("i");
                            if (jiajian.hasClass("appbox_td_spa04")) {
                                return false;
                            }

                            var ul = currentNode.children("ul");
                            var isShow = currentNode.attr("isShow");

                            if (isShow == "show") {
                                ul.hide();
                                currentNode.attr("isShow", "hide");
                                jiajian.removeClass();
                                jiajian.addClass("app_jia");
                                jiajian.attr("title", "展开");
                                return false;
                            } else if (isShow == "hide") {
                                ul.show();
                                currentNode.attr("isShow", "show");
                                jiajian.removeClass();
                                jiajian.addClass("app_jian");
                                jiajian.attr("title", "收起");

                                return false;
                            }
                            if (!isShow) {
                                currentNode.attr("isShow", "show");
                                jiajian.removeClass();
                                jiajian.addClass("app_jian");
                                jiajian.attr("title", "收起");
                            } else {
                                return false;
                            }

                            getChildrenCatalog(currentNode.attr("id"), currentNode, url);
                            //阻止事件冒泡
                            event.preventDefault();
                            return false;
                        });

                        /*//////////如果该分类为图书分类，则默认点击
                         if (cat.catalogId == "64e924aa-8449-11e1-aa0a-000b2f35ecf4") {
                         liNode.click();
                         }
                         //////////*/

                        liNode.dblclick(function (event) {
                            var currentNode = $(this);
                            var catName = currentNode.attr("catName");
                            var catId = currentNode.attr("id");
                            var dialogObj = $.getActiveDialog();
                            if ($.isFunction(callback)) {
                                var flag = callback({catalogName:catName, catalogId:catId});
                                if (flag == undefined || flag == true) {
                                    dialogObj.confirm();
                                }
                            } else {
                                dialogObj.confirm();
                            }
                            //阻止事件冒泡
                            event.preventDefault();
                            return false;
                        });

                    }
                    appendNode.append(ulNode);
                }
            });

        }


        getChildrenCatalog("ROOT", content, url);

        var dialogObj = $.jDialog({id:"logonDialog",
            width:"400",
            height:'400',
            title:"本地分类选择",
            content:content,
            modal:true,
            needAction:true,
            bottomClass:"dcen",
            actions:[
                {
                    label:"确 定",
                    actionClasss:'popbtn popbtn01',
                    action:function (dialogObj) {
                        var currentNode = content.find(".currentSelected");
                        var catName = currentNode.attr("catName");
                        var catId = currentNode.attr("id");
                        debugger;
                        if(catId=="" ||catId==undefined){
                            alert("请选择商品分类！");
                            return;
                        }
                        if ($.isFunction(callback)) {
                            var flag = callback({catalogName:catName, catalogId:catId});
                            if (flag == undefined || flag == true) {
                                dialogObj.confirm();
                            }
                        } else {
                            dialogObj.confirm();
                        }
                    }
                }
            ]

        });

    } ,

    //商品分类查询补全
    autoCompleteGoodsCatalog:function (catCodeInput, catNameInput, cat_name_div, flag) {

        //创建自动完成的下拉列表，用于显示服务器返回的数据,插入在搜索按钮的后面，等显示的时候再调整位置
        var autoComplete = $('<div class="autocomplete" style=""></div>').hide().insertAfter(cat_name_div);

        //清空下拉列表的内容并且隐藏下拉列表区
        var clear = function () {
            autoComplete.empty().hide();
        };
        //注册事件，当输入框失去焦点的时候清空下拉列表并隐藏
        catNameInput.blur(function () {
            setTimeout(clear, 500);
        });

        //下拉列表中高亮的项目的索引，当显示下拉列表项的时候，移动鼠标或者键盘的上下键就会移动高亮的项目
        var selectedItem = null;
        //timeout的ID
        var timeoutid = null;
        //设置下拉项的高亮背景
        var setSelectedItem = function (item) {
            //更新索引变量
            selectedItem = item;
            //按上下键是循环显示的，小于0就置成最大的值，大于最大值就置成0
            if (selectedItem < 0) {
                selectedItem = autoComplete.find('li').length - 1;
            }
            else if (selectedItem > autoComplete.find('li').length - 1) {
                selectedItem = 0;
            }
            //首先移除其他列表项的高亮背景，然后再高亮当前索引的背景
            autoComplete.find('li').removeClass('autocomplete_cur').eq(selectedItem).addClass('autocomplete_cur');
        };
        var ajax_request = function () {
            var catName = catNameInput.val();
            if (catName != "" && catName != null) {
                $.localAjax({
                    url:"/sys/queryByName",
                    data:{catalogName:catName},
                    dataType:'json',
                    type:'post',
                    success:function (result) {
                        if (result && result.success == 1) {
                            $.each(result.data, function (index, term) {
                                var li = $('<li></li>').text(term.name);
                                autoComplete.append(li);
                                li.attr("catCode", term.catCode);
                                li.addClass("autocomplete_cur").hover(
                                    function () {
                                        $(this).siblings().removeClass('autocomplete_cur');
                                        $(this).addClass('autocomplete_cur');
                                        selectedItem = index;
                                    },
                                    function () {
                                        //下拉列表每一项的事件，鼠标离开的操作
                                        $(this).removeClass('autocomplete_cur');
                                        //当鼠标离开时索引置-1，当作标记
                                        selectedItem = -1;
                                    }).click(function () {
                                        //鼠标单击下拉列表的这一项的话，就将这一项的值添加到输入框中
                                        var name = term.name;
                                        var catCode = term.catCode;
                                        judgeCatCodeExist(name, catCode);
                                        //清空并隐藏下拉列表
                                        autoComplete.empty().hide();
                                    });
                            });
                            setSelectedItem(0);
                            //显示下拉列表
                            autoComplete.show();
                        }
                    }

                });
            }

        }
        catNameInput.keyup(function (event) {
            if (!flag.is(":checked")) {
                return false;
            }

            //字母数字，退格，空格
            if (event.keyCode > 40 || event.keyCode == 8 || event.keyCode == 32) {
                autoComplete.empty().hide();
                clearTimeout(timeoutid);
                timeoutid = setTimeout(ajax_request, 100);
            } else if (event.keyCode == 38) {
                //上
                //selectedItem = -1 代表鼠标离开
                if (selectedItem == -1) {
                    setSelectedItem(autoComplete.find('li').length - 1);
                } else {
                    setSelectedItem(selectedItem - 1);
                }
                event.preventDefault();
            } else if (event.keyCode == 40) {
                //下
                //selectedItem = -1 代表鼠标离开
                if (selectedItem == -1) {
                    setSelectedItem(0);
                }
                else {
                    //索引加1
                    setSelectedItem(selectedItem + 1);
                }
                event.preventDefault();

            }
        })
        catNameInput.keypress(function (event) {
            //enter键
            if (event.keyCode == 13) {
                //列表为空或者鼠标离开导致当前没有索引值
                if (autoComplete.find("li").length == 0 || selectedItem == -1) {
                    return;
                }
                var catName = autoComplete.find('li').eq(selectedItem).text();
                var catLibCode = autoComplete.find('li').eq(selectedItem).attr("catCode");
                judgeCatCodeExist(catName, catLibCode);
                autoComplete.empty().hide();
                event.preventDefault();
            }
        });
        catNameInput.keydown(function (event) {
            //Esc键
            if (event.keyCode == 27) {
                autoComplete.empty().hide();
                event.preventDefault();
            }
        });
        function judgeCatCodeExist(catName, catLibCode) {
            if (catLibCode != catCodeInput.val()) {
                $.localAjax({
                    url:"/shopadmin/goodsCatalog/judgeCatCodeExist",
                    data:{catLibCode:catLibCode},
                    type:"POST",
                    dataType:"json",
                    async:false,
                    success:function (result) {
                        if (result && result.success == "false") {
                            alert("该标准模板分类已被匹配！");
                            catNameInput.focus();
                            return false;
                        } else {
                            catNameInput.val(catName);
                            catCodeInput.val(catLibCode);
                        }
                    }
                });
            }
        }
    },


    //有登录的ajax请求
    localAjaxWithLogon:function (callback) {
        $.localAjax({
            url:"/member/checkCall",
            data:{},
            dataType:"json",
            type:"post",
            success:function (result) {
                if ($.isFunction(callback)) {
                    callback();
                }
            }
        });

    },

    //判断该用户是否已经激活了
    isMemberValdated:function (callback) {
        if ($.isFunction(callback)) {
            callback();
        }
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

    /**
     * 在购买或加入购物前验证商品是否删除或下架
     * @param goodsId
     * @param callback
     */
    checkGoodsState:function (goodsId, callback) {
        $.localAjax({
            url:"/goods/checkGoodsState",
            data:{goodsId:goodsId},
            dataType:'json',
            type:'post',
            success:function (result) {
                if (result && result.success == "1") {
                    if (result.errorState == "0") {
                        if ($.isFunction(callback)) {
                            callback();
                        }
                    } else if (result.errorState == "1") {
                        alert("对不起，该商品已删除！");
                    } else if (result.errorState == "2") {
                        alert("该商品已下架，无法进行此操作！");
                    }
                }
            }
        });
    },


    /**
     * 验证该团购是否已经被删除了
     * @param goodsId
     * @param callback
     */
    checkTuanGouState:function (tuanGouId, callback, noExistCallBack) {
        $.localAjax({
            url:"/tuangou/checkTuanGouState",
            data:{tuanGouId:tuanGouId},
            dataType:'json',
            type:'post',
            success:function (result) {
                if (result && result.exist == "true") {
                    if ($.isFunction(callback)) {
                        callback();
                    }
                } else {
                    alert("该团购已经被删除，不能购买！");
                    if ($.isFunction(noExistCallBack)) {
                        noExistCallBack();
                    }
                }
            }
        });
    },


    /**
     * js获取登memberId
     */
    getFrontMemberId:function () {
        return $.trim($("#frontMemberId").val());
    },


    getDomain:function () {
        var reg = new RegExp("^(((https|http)://)?[A-Za-z0-9.]*(:\\d*)?/?)");
        var mainUrl = "";
        if (reg.test(location.href)) {
            mainUrl = (RegExp.$1);
        }
        return mainUrl;
    },
    /*计算购物车商品数量*/
    countCartAmount:function () {
        $.localAjax({
            url:"/member/cart/countAmount",
            data:{},
            dataType:"json",
            type:"post",
            success:function (result) {
                if (result.totalAmount != "") {
                    $("[name=cartAmountA] b").html(result.totalAmount);
                    $("[name=cartAmountA] span").html("(" + result.totalAmount + ")");
                } else {
                    $("[name=cartAmountA] b").html('0');
                    $("[name=cartAmountA] span").html('(0)');
                }
            },
            error:function () {
                $("[name=cartAmountA] b").html('0');
                $("[name=cartAmountA] span").html('(0)');
            }
        });
    },

    /*收藏该商品
     * goodsId 商品ID
     * price 商品价格
     * callBack 收藏成功后回调函数
     */
    collectGoods:function (goodsId, salePropVals, callBack) {
        var data = {};
        data.goodsId = goodsId;
        data.salePropVals = salePropVals;

        $.localAjax({
            url:"/member/collect/addCollectGoods",
            data:data,
            dataType:"json",
            type:"post",
            success:function (result) {
                if (callBack && typeof callBack == "function") {
                    callBack(result);
                }
            },
            error:function () {
                alert("系统繁忙，请稍后重试");
            }
        });
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

    selectGoods:function (opt) {
        var defaultOpt = {
            success:function (data) {
            },
            ajaxdata:{
                isRealTime:"0"
            }
        };
        var _opt = $.extend(defaultOpt, opt);
        top.selectGoodsOpt = _opt;
        $.jDialog({
            id:"searchGoodsForUpdate",
            width:"800",
            height:'430',
            url:"/shopadmin/goods/showGoodsSearch",
            title:"选择商品",
            _ajax:true,
            _ajaxdata:_opt.ajaxdata,
            needAction:true,
            modal:true,
            bottomClass:"dcen",
            actions:[
                {
                    label:"确 定",
                    actionClasss:'popbtn popbtn01',
                    action:function (dialogObj) {
                        var data = top.goodsSearchGrid.getCheckBoxValues();
                        if ($.isFunction(_opt.success)) {
                            var result = _opt.success(data);
                            if (result == true || result == undefined) {
                                dialogObj.confirm();
                            }
                        }


                    }
                }
            ]
        });
    },


    selectMembers:function (opt) {
        var defaultOpt = {
            success:function (data) {
            },
            ajaxdata:{
                isRealTime:"0"
            }
        };
        var _opt = $.extend(defaultOpt, opt);
        top.selectMembersOpt = _opt;
        $.jDialog({
            id:"searchMembersForUpdate",
            width:"1000",
            height:'430',
            url:"/shopadmin/member/showMemberSearch",
            title:"选择会员",
            _ajax:true,
            _ajaxdata:_opt.ajaxdata,
            needAction:true,
            modal:true,
            bottomClass:"dcen",
            actions:[
                {
                    label:"确 定",
                    actionClasss:'popbtn popbtn01',
                    action:function (dialogObj) {
                        var data = top.membersSearchGrid.getCheckBoxValues();
                        if ($.isFunction(_opt.success)) {
                            var result = _opt.success(data);
                            if (result == true || result == undefined) {
                                dialogObj.confirm();
                            }
                        }


                    }
                }
            ]
        });
    },


    selectActivity:function (opt) {
        var defaultOpt = {
            success:function (data) {
            },
            ajaxdata:{
                isRealTime:"0"
            }
        };
        var _opt = $.extend(defaultOpt, opt);
        var type = $.trim(opt.type);
        top.selectActivityOpt = _opt;
        $.jDialog({
            id:"searchActivity",
            width:"1000",
            height:'430',
            url:"/shopadmin/activity/showActivitySearch?type=" + type,
            title:"选择活动",
            _ajax:true,
            _ajaxdata:_opt.ajaxdata,
            needAction:true,
            modal:true,
            bottomClass:"dcen",
            actions:[
                {
                    label:"确 定",
                    actionClasss:'popbtn popbtn01',
                    action:function (dialogObj) {
                        var data = top.activitySearchGrid.getCheckBoxValues();
                        if ($.isFunction(_opt.success)) {
                            var result = _opt.success(data);
                            if (result == true || result == undefined) {
                                dialogObj.confirm();
                            }
                        }


                    }
                }
            ]
        });
    },


    selectDistributorGoods:function (opt) {
        var defaultOpt = {
            success:function (data) {
            },
            ajaxdata:{
                isRealTime:"0"
            }
        };
        var _opt = $.extend(defaultOpt, opt);
        top.selectGoodsOpt = _opt;
        $.jDialog({
            id:"searchGoodsForUpdate",
            width:"800",
            height:'430',
            url:"/distributor/goods/showGoodsSearch",
            title:"选择商品",
            _ajax:true,
            _ajaxdata:_opt.ajaxdata,
            needAction:true,
            modal:true,
            bottomClass:"dcen",
            actions:[
                {
                    label:"确 定",
                    actionClasss:'popbtn popbtn01',
                    action:function (dialogObj) {
                        var data = top.goodsSearchGrid.getCheckBoxValues();
                        if ($.isFunction(_opt.success)) {
                            var result = _opt.success(data);
                            if (result == true || result == undefined) {
                                dialogObj.confirm();
                            }
                        }


                    }
                }
            ]
        });
    },
    selectDistributor:function (opt) {
        var defaultOpt = {
            success:function (data) {
            }
        };
        var _opt = $.extend(defaultOpt, opt);
        top.selectDistributorOpt = _opt;
        $.jDialog({
            id:"searchDistributorForAssigned",
            width:"800",
            height:'430',
            url:"/shopadmin/distributor/showDistributorSearch",
            title:"选择配送商",
            _ajax:true,
            needAction:true,
            modal:true,
            bottomClass:"dcen",
            actions:[
                {
                    label:"确 定",
                    actionClasss:'popbtn popbtn01',
                    action:function (dialogObj) {
                        var data = top.distributorSearchGrid.getCheckBoxValues();
                        if ($.isFunction(_opt.success)) {
                            var result = _opt.success(data);
                            if (result == true || result == undefined) {
                                dialogObj.confirm();
                            }
                        }
                    }
                }
            ]

        })

    },
//    从第三方系统登录
    logonFromThirdSystem:function (memberName, realName) {
        var pwd = hex_sha1("xmbookegou123");
        var data = {};
        data.memberName = memberName;
        data.realName = realName;
        data.pwd = pwd;
        $.localAjax({
            url:"/member/logonFromThirdSystem",
            data:data,
            dataType:'json',
            type:'post',
            success:function (result) {
                if (result && result.resultCode == "1") {
                    var logon_returnUrl = $.cookie("logon_returnUrl");
                    if (logon_returnUrl != undefined) {
                        window.location = logon_returnUrl;
                    } else {
                        window.location = "/";
                    }
                }
            }
        });
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
    },
    loadProvinceCity:function (targetDivId, callback, initAreaObj) {

        function showProvinces() {
            var _areaDiv = $('#allProvince');
            _areaDiv.children().remove();
            var cur_province = $.trim($("#curProvince b:first").html());
            var areaGroups = [
                {areaGroup:"1", groupName:"华东地区："},
                {areaGroup:"2", groupName:"华北地区："},
                {areaGroup:"3", groupName:"华中地区："},
                {areaGroup:"4", groupName:"华南地区："},
                {areaGroup:"5", groupName:"东北地区："},
                {areaGroup:"6", groupName:"西北地区："},
                {areaGroup:"7", groupName:"西南地区："},
                {areaGroup:"8", groupName:"港澳台："},
                {areaGroup:"9", groupName:"海外："}
            ];
            $.each(areaGroups, function (idx, group) {
                _areaDiv.append(makeAreaGroup(group.areaGroup, group.groupName, cur_province));
            });
        }

        function makeAreaGroup(areaGroup, groupName, cur_province) {
            var _li = $("<li></li>");
            var _areaGroupName = $('<em class="fgray9">' + groupName + '</em>').appendTo(_li);
            $.localAjax({
                url:"/area/showArea",
                data:{areaGroup:areaGroup},
                dataType:"json",
                async:true,
                type:"POST",
                success:function (result) {
                    $.each(result.areas, function (idx, area) {
                        _li.append("&nbsp;&nbsp;");
                        var a = $("<a href='javascript:void(0)' class='item' areaId='" + area.areaId + "'>" + area.areaName + "</a>");
                        a.click(function () {
                            var areaName = $(this).text();
                            var areaId = $(this).attr("areaId");
                            $("#areaName").text(areaName);
                            $("#areaName").attr("areaId", areaId);
                            if ($.isFunction(callback)) {
                                callback({areaId:areaId, areaName:areaName});
                            }
                            $(".city_pop").hide();
                            $(".city").removeClass("city2");
                        });
                        a.appendTo(_li);
                    });
                }
            });
            return _li;
        }

        var target = $("#" + targetDivId);
        var provinceDivStr =
            '<div class="citybox">' +
                '<div class="city" id="curProvince"><a href="javascript:void(0)"><span id="areaName" areaId="' + initAreaObj.areaId + '">' + initAreaObj.areaName + '</span>&nbsp;<b></b></a>' +
                '<input type="text"  id="curProvinceId" name="curProvinceId" style="display: none;"  />' +
                '</div>' +
                '<div class="city_pop" style="display: none;">' +
                '<span class="city_close"></span>' +
                '<ul id="allProvince">' +
                '</ul>' +
                '</div>' +
                '</div>';
        var provinceDiv = $(provinceDivStr);
        target.append(provinceDiv);

        $("#curProvince").click(function () {
            if ($(this).attr("isLoaded") != "true") {
                showProvinces();
                $(this).attr("isLoaded", "true");
            }
            $(".city_pop").show();
            $(".city").addClass("city2");
        });

        $(".city_close").click(function () {
            $(".city_pop").hide();
            $(".city").removeClass("city2");
        });

        $("#curProvince").click(function (e) {
            e ? e.stopPropagation() : event.cancelBubble = true;
        });
        $(".city_pop").click(function (e) {
            e ? e.stopPropagation() : event.cancelBubble = true;
        });
        $(document).click(function () {
            $(".city_pop").hide();
            $(".city").removeClass("city2");
        });


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
    $.fn.extend({
        /**
         *title:按钮显示名称
         * className：基类名（完全限定名）
         * fileName：导出excel时的文件名
         * width：宽度
         * height：高度
         * @param opt
         */
        exportData:function (opt) {
            var $this = $(this);
            opt = $.extend({
                title:"导出EXCEL",
                className:"Member",
                fileName:"会员信息",
                width:"300",
                height:"250",
                field:"shopadmin"
            }, opt);

            var btn = $('<a class="btn_add02 btnbg"><span class="btnbg">' + opt.title + '</span></a>');
            btn.click(function () {
                $.localAjax({
                    url:"/" + opt.field + "/export/queryTableConfig",
                    data:opt,
                    dataType:'json',
                    type:'post',
                    success:function (result) {
                        var data = result.data;
                        if (data && data.length > 0) {
                            var content = $("<div></div>");
                            var form = $("<form name='myform' id='myform' action='/" + opt.field + "/export/exportToExcel' method='post'></form>");
                            content.append(form);
                            var checkAll = $("<div style='background: #eee; padding: 5px 10px; '><label><input class='cbox' name='checkAll' type='checkbox' />全选</label></div>").appendTo(content);
                            checkAll.click(function () {
                                if (checkAll.find("[name=checkAll]").attr("checked")) {
                                    content.find("[name=tableConfig]").attr("checked", true);
                                } else {
                                    content.find("[name=tableConfig]").attr("checked", false);
                                }
                            });
                            var ul = $("<ul style='padding-left: 25px;' class='clearfix mart_15'></ul>").appendTo(content);
                            for (var i = 0; i < data.length; i++) {
                                $('<li class="fl marb_15" style="width: 33.3%"><label><input class="cbox" name="tableConfig" type="checkbox" value="' + data[i].configId + '"/>' + data[i].columnName + '</label></li>').appendTo(ul);
                            }
                            var dialog = $.jDialog({id:"dataExportDialog",
                                width:opt.width,
                                height:opt.height,
                                title:"请选择导出项",
                                content:content,
                                modal:true,
                                needAction:true,
                                bottomClass:"dcen",
                                actions:[
                                    {
                                        label:"导  出",
                                        actionClasss:'popbtn popbtn01',
                                        action:function (dialogObj) {
                                            var exportData = {};
                                            var configIds = [];
                                            content.find("[name=tableConfig]:checked").each(function () {
                                                configIds.push($(this).val());
                                            });
                                            if (configIds.length == 0) {
                                                alert("请选择要导出的列!");
                                                return false;
                                            }
                                            var param = $.param(exportData);
                                            dialogObj.confirm();
                                            form.append($("<input name='configIds' type='hidden' value='" + configIds.join(",") + "'/>"));
                                            form.append($("<input name='fileName' type='hidden' value='" + opt.fileName + "'/>"));
                                            form.append($("<input name='className' type='hidden' value='" + opt.className + "'/>"));
                                            content.find("#myform").submit();
                                        }
                                    }
                                ]});
                        } else {
                            alert("还未配置导出数据表");
                        }
                    }
                });
            });
            btn.appendTo($this);
        },
        /**
         * 模块位置
         * @param opt
         */
        selectModulePosition:function (opt) {
            var $this = $(this);
            var opt = $.extend({
                areaId:"",
                channelId:"",
                moduleId:"19", //调用模块ID 必需
                needChannel:true
            }, opt);
            var channelSelect = $("<select></select>");
            var areaTplSelect = $("<select></select>");

            this.getSelectArea = function () {
                return areaTplSelect.val();
            }

            this.getSelectChannel = function () {
                return channelSelect.val();
            }

            this.checkSelect = function () {
                if (areaTplSelect.val() == "") {
                    alert("请选择区域");
                    return false;
                }
                if (opt.needChannel) {
                    if (channelSelect.val() == "") {
                        alert("请选择频道");
                        return false;
                    }
                }
                return true;
            }
            $.localAjax({
                url:"/shopadmin/areatpl/queryModuleAreaTplAndChannel",
                data:{moduleId:opt.moduleId},
                dataType:'json',
                type:'post',
                success:function (result) {
                    var channels = result.channels;
                    var areaTpls = result.areaTpls;
                    if (channels && channels.length > 0) {
                        channelSelect.append("<option value=''>请选择频道...</option>");
                        for (var i = 0; i < channels.length; i++) {
                            var channelItem = $("<option value='" + channels[i].channelLibId + "'>" + channels[i].channelName + "</option>");
                            if (channels[i].channelLibId == opt.channelId) {
                                channelItem.attr("selected", true);
                            }
                            channelSelect.append(channelItem)
                        }
                    }
                    if (areaTpls && areaTpls.length > 0) {
                        areaTplSelect.append("<option value=''>请选择区域...</option>");
                        for (var i = 0; i < areaTpls.length; i++) {
                            var areaTplItem = $("<option value='" + areaTpls[i].areaId + "'>" + areaTpls[i].areaName + "</option>");
                            if (areaTpls[i].areaId == opt.areaId) {
                                areaTplItem.attr("selected", true);
                            }
                            areaTplSelect.append(areaTplItem);
                        }
                    }
                    $this.append(areaTplSelect);
                    if (opt.needChannel) {
                        $this.append(channelSelect);
                    }
                }
            });
            return this;
        }
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
