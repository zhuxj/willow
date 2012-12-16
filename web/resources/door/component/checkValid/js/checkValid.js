/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-2-8
 *作    者： 朱贤俊
 *使用规则：
 *  var checkValid = $.checkValid(config);
 *  $("#ok").click(function () {
 *        if (checkValid.checkAll()) {
 *            alert("验证通过");
 *        }
 *    })
 *  config配置说明：
 *   var config = {
 *         reportMode:"hint", //可选值为 alert|hint,默认为alert；前者为弹出对话框的方式报告错误，后者为DIV的方式，需要表单页面中存在错误报表的DIV ，如果没有这则使用弹出对话框
 *         props:[
 *         {
 *           name:"[name]", //表单对用的name或id的值
 *           label:"[名称]",//表单对应的中文名.
 *           trim:true|false, //在检验长度时，是否需要先去除前后的空格，默认为true
 *           required:true|false,  //是否必填，默认为false;
 *           lengthRange:{min:1, max:3, unit:"char"}  //长度校验规则，有4个属性：min,max,fixlen,unit,如果是范围可以使用min和max，支持开区间，如
 *                                                    //长度必须小于 100，仅设置max:100就可以了，如果必须大于10，则仅需设置min:10。
 *                                                    //如果需要固定长度,可以通过fixlen属性来设置,unit为长度测量的单位：byte|char，默认为byte（一个中文对应2个字节长度）
 *           valueRange:{min:1, max:150}  //如果是数字，则可以指定范围，min和max至少要设置一个属性。
 *           dataType:"[int|email|tel|postcode|url|en|cn|number|date]", //数据的类型，支持 email：电子邮件|tel：电话| postcode:邮政编码|url：URL|an:数英及下划线|en:英文字符（ASCII字符）|cn:中文字符
 *                                  //|int:整数|number 数字类型:(支持标准数据表示形式，如number(4,2),number(4)等。)|date:日期,格式为:yyyy-MM-dd
 *           pattern:{regExp:"/^([0-9])*-?([0-9])*$/", errorMsg:"正则表达式验证不通过"}, //支持正则表达式验证表单数据的合法性
 *           jsFunction:function (property, propValue, validResult) { //自定义验证函数
 *                                                                    //property:校验配置对象，参见上面配置JSON格式的说明
 *                                                                    //propValue：当前表单的值
 *                                                                    //validResult：校验的结果对象，如果验证失败，必须调用该对象的setError()方法
 *                          if (!/^([0-9])*-?([0-9])*$/.test(propValue)) {
 *                           validResult.setError(property.formatLabel+"不正确");
 *                          return false;
 *                          }
 *                          return true;
 *                          }
 *          }
 *         ]
 *         }
 *
 */
(function ($) {

    function CheckValid(config) {
        var defaultConfig = {
            requiredCheckIgnore:false,
            reportMode:"alert",
            formDiv:null
        }
        this.config = $.extend({}, defaultConfig, config);
        this.validResultMap = {};
    }

    CheckValid.prototype = {
        checkAll:function () {
            var _cv = this;
            var props = this.config.props;
            $.each(props, function (index, property) {
                _cv.initProp(property);
                _cv.checkProperty(property);
            });
            return this.getCheckResult();
        },
        initProp:function (prop) {
            var _cv = this;
            var hintId = null;
            var propElem = null;
            var propName = prop.name;
            var propLabel = prop.label || propName;
            prop.formatLabel = "[" + propLabel + "]";
            var validResult = new ValidResult(propName, propLabel);
            if (_cv.config.reportMode == 'hint') {
                hintId = prop.hintId ? prop.hintId : propName + "Hint";
                validResult.setHintId(hintId);
            }
            this.validResultMap[propName] = validResult;

        },
        getCheckResult:function () {
            var validResult = null;
            var errorMsg = null;
            var _cv = this;
            for (var propName in this.validResultMap) {
                validResult = this.validResultMap[propName];
                if (validResult.status == -1) {
                    errorMsg = validResult.msg;
                    if (_cv.config.reportMode == 'alert') {
                        alert(errorMsg);
                    } else {//hint
                        var hint = _cv.getPropElem(validResult.hintId);
                        if (hint[0]) {
                            hint.html(errorMsg);
                        } else {
                            alert(errorMsg);
                        }
                    }
                    try {
                        _cv.getPropElem(validResult.propName).focus();
                    } catch (e) {
                    }
                    return false;
                } else {
                    if (_cv.config.reportMode == 'hint') {
                        var hint = _cv.getPropElem(validResult.hintId);
                        if (hint[0]) {
                            hint.html("");
                        }
                    }
                }
            }

            return true;
        },

        checkProperty:function (property) {
            var trim = property.trim || true;
            var propElem = this.getPropElem(property.name);
            //如果该元素找不到，则不进行验证
            if (!propElem[0]) {
                return true;
            }
            var propValue = propElem.val();
            if (trim && propValue) {
                propValue = propValue.trim();
                propElem.val(propValue);
            }

            //验证是否必填
            var required = property.required || false;
            if (!this.config.requiredCheckIgnore && required) {
                if (!this.checkRequired(property, propValue)) {
                    return false;
                }
            } else if (!propValue || propValue == "") {//不强制且无值，无需继续判断
                return true;
            }

            //验证数据类型
            if (property.dataType) {
                if (!this.checkDataType(property, propValue)) {
                    return false;
                }
            }

            //验证模式匹配
            if (property.pattern) {
                if (!this.checkPattern(property, propValue)) {
                    return false;
                }
            }

            //验证长度
            if (property.lengthRange) {
                if (!this.checkLen(property, propValue)) {
                    return false;
                }
            }

            //验证值域
            if (property.valueRange) {
                if (!this.checkValue(property, propValue)) {
                    return false;
                }
            }

            //自定义函数
            var fun = property.jsFunction;
            if (fun) {
                try {
                    if (typeof fun == "string") {
                        eval("fun=" + fun);
                    }
                } catch (e) {
                    alert("自定义函数配置有错误：\n" + fun);
                    return false;
                }
                var validResult = this.validResultMap[property.name];
                return fun(property, propValue, validResult);
            }
            this.setCheckRight(property.name);
        },
        checkValue:function (property, value) {
            var valueRange = property.valueRange;
            var min = valueRange.min || Number.MIN_VALUE;
            var max = valueRange.max || Number.MAX_VALUE;
            var range = (min == Number.MIN_VALUE || max == Number.MAX_VALUE ) ? true : false;
            var _value = parseFloat(value);

            var errorMsg = property.formatLabel + "的值必须在" + min + "~" + max + "之间";
            var isValid = true;

            if (_value < min)//小于最小值
            {
                isValid = false;
                if (!range) {
                    errorMsg = property.formatLabel + "的值必须大于(含等于)" + min;
                }
            }
            if (_value > max)//大于最大值
            {
                isValid = false;
                if (!range) {
                    errorMsg = property.formatLabel + "的值必须小于(含等于)" + max;
                }
            }
            if (!isValid) {
                this.setCheckError(property, errorMsg);
            } else {
                return true;
            }
        },
        checkLen:function (property, value) {
            var lengthRange = property.lengthRange;
            var unit = lengthRange.unit || 'byte';
            var len = getLength(value);
            if (lengthRange.fixlen) {//固定长度
                if (len != lengthRange.fixlen) {

                    var errorMsg = property.formatLabel + "的长度必须为" + lengthRange.fixlen + "个" + (unit == 'byte' ? "字节" : "字符") + "长度";
                    if (unit == 'byte') {
                        errorMsg += "\n(注:中文字符为2个字节长度，英文、数字及普通的符号为1个字节长度)";
                    }
                    this.setCheckError(property, errorMsg);
                    return false;
                } else {
                    return true;
                }
            } else {//范围
                var min = parseInt(lengthRange.min, 10) || Number.MIN_VALUE;
                var max = parseInt(lengthRange.max, 10) || Number.MAX_VALUE;
                var errorMsg = property.formatLabel + "的长度必须在" + min + "~" + max + "个" + (unit == 'byte' ? "字节" : "字符") + "之间";
                var isValid = true;
                if (len < min)//小于最小长度
                {
                    isValid = false;
                    if (lengthRange.min) {
                        errorMsg = property.formatLabel + "的长度必须大于(含等于)" + min + "个" + (unit == 'byte' ? "字节" : "字符");
                    }
                }
                if (len > max)//大于最大长度
                {
                    isValid = false;
                    if (lengthRange.max) {
                        errorMsg = property.formatLabel + "的长度必须小于(含等于)" + max + "个" + (unit == 'byte' ? "字节" : "字符");
                    }
                }
                if (unit == 'byte') {
                    errorMsg += "\n(注:中文字符为2个字节长度，英文、数字及普通的符号为1个字节长度)";
                }
                if (!isValid) {
                    this.setCheckError(property, errorMsg);
                } else {
                    return true;
                }
            }
            function getLength(val) {
                if (!val) return 0;
                else {
                    return unit == 'byte' ? val.getByteLen() : val.getCharLen();
                }
            }
        },
        checkPattern:function (property, value) {
            var pattern = property.pattern;
            var regExp = pattern.regExp;
            /*
             * 如果传入的是字符串，则转换成正则表达式对象
             * 如果是使用使用perl风格的语法：/expression/gi;则不需要转换
             */
            if ((typeof regExp) == "string") {
                if (regExp.charAt(0) == "/" && regExp.charAt(regExp.length - 1) == "/") { //删除字符串头尾"/"
                    regExp = regExp.substring(1, regExp.length - 1);
                }
                regExp = new RegExp(regExp);
            }
            if (!regExp.test(value)) {
                this.setCheckError(property, pattern.errorMsg);
                return false;
            }
            return true;
        },
        checkDataType:function (property, value) {
            var _cv = this;

            if (property.dataType.toUpperCase() == 'EMAIL' && !checkEmail(value)) {
                var errorMsg = property.formatLabel + "必须为合法的Email格式，例如tom@163.com，zhangsan@yahoo.com.cn等。";
                this.setCheckError(property.name, errorMsg);
                return false;
            } else if (property.dataType.toUpperCase() == 'TEL' && !checkTel(value)) {
                var errorMsg = property.formatLabel + "必须为合法的电话格式，只能包括数字和-，如010-2412312,8765432等。";
                this.setCheckError(property.name, errorMsg);
                return false;
            } else if (property.dataType.toUpperCase() == 'POSTCODE' && !checkPostcode(value)) {
                var errorMsg = property.formatLabel + "必须为合法的邮政编码格式，即6位数字，如010010,361005等。";
                this.setCheckError(property.name, errorMsg);
                return false;
            } else if (property.dataType.toUpperCase() == 'URL' && !checkURL(value)) {
                var errorMsg = property.formatLabel + "必须为合法的URL格式，如http://www.google.com等。";
                this.setCheckError(property.name, errorMsg);
                return false;
            } else if (property.dataType.toUpperCase() == 'AN' && !checkPwd(value)) {
                var errorMsg = property.formatLabel + "只能由数字，英文字符和_组成。";
                this.setCheckError(property.name, errorMsg);
                return false;
            } else if (property.dataType.toUpperCase() == 'EN' && !checkEnglish(value)) {
                var errorMsg = property.formatLabel + "只能由英语字符组成，不能包含中文字符，如123adf,abad#等。";
                this.setCheckError(property.name, errorMsg);
                return false;
            } else if (property.dataType.toUpperCase() == 'CN' && !checkChinese(value)) {
                var errorMsg = property.formatLabel + "只能输入中文字符，如：中国，人民等";
                this.setCheckError(property.name, errorMsg);
                return false;
            } else if (property.dataType.toUpperCase().indexOf("NUMBER") > -1) {
                var propResult = checkNumber(property);
                if (!propResult.result) {
                    this.setCheckError(property.name, propResult.msg);
                    return false;
                }
            } else if (property.dataType.toUpperCase().indexOf("INT") > -1) {
                var propResult = checkInteger(property);
                if (!propResult.result) {
                    this.setCheckError(property.name, propResult.msg);
                    return false;
                }
            } else if (property.dataType.toUpperCase().indexOf("DATE") > -1) {
                var propResult = checkDate(property);
                if (!propResult.result) {
                    this.setCheckError(property.name, propResult.msg);
                    return false;
                }
            }
            return true;

            //电子邮件地址校验
            function checkEmail(value) {
                var reg = new RegExp("^([\.a-zA-Z0-9_-]){1,}@([a-zA-Z0-9_-]){1,}(\.([a-zA-Z0-9]){2,4})*$");
                if (!reg.test(value)) {
                    return false;
                }
                return true;
            }

            //电话号码校验
            function checkTel(value) {
                if (!/^([0-9])*-?([0-9])*$/.test(value)) {
                    return false;
                }
                return true;
            }

            //邮编校验
            function checkPostcode(value) {
                if (!/^([0-9])*([0-9])*$/.test(value)) {
                    return false;
                }
                return true;
            }

            //URL校验
            function checkURL(value) {
                if (!/^HTTP|HTTPS|FTP|MAILTO/i.test(value)) {
                    return false;
                }
                return true;
            }

            //密码校验
            function checkPwd(value) {
                if (!/^[a-zA-Z0-9_]+$/.test(value)) {
                    return false;
                }
                return true;
            }

            //英文字符
            function checkEnglish(value) {
                if (!/^[\x00-\xff]+$/.test(value)) {
                    return false;
                }
                else {
                    return true;
                }
            }

            //中文字符
            function checkChinese(value) {
                if (!/^[^\x00-\xff]+$/.test(value)) {
                    return false;
                }
                else {
                    return true;
                }
            }

            //整数校验
            function checkInteger(property) {
                var title = property.label;
                var dataType = property.dataType;
                var val = _cv.getPropElem(property.name).val();

                if (/^(-|\+)+/.test(val)) {
                    var msg = "输入" + title + "不能带有正负(+，-)符号。";
                    return new PropResult(false, msg);
                }

                if (/(\.)+/.test(val)) {
                    var msg = "输入" + title + "不能带有小数点(.)符号。";
                    return new PropResult(false, msg);
                }
                dataType = (new String(dataType)).toUpperCase();
                dataType = dataType.replace("INT", "NUMBER");
                return checkNumber(property, dataType);
            }

            //数字格式
            function checkNumber(property, dataType) {
                var value = _cv.getPropElem(property.name).val();
                var dataType = dataType || property.dataType;
                var msg = null;
                //处理输入值是否包含有单引号或双引号,如果包含就不进行以下处理啦,数据中是不可能有单引号或双引号的
                if (/\'|\"/.test(value)) {
                    msg = "输入" + property.formatLabel + "非法，数字中包含有单引号或双引号。";
                    return new PropResult(false, msg);
                }

                var fuc = dataType;

                fuc = (new String(fuc)).toUpperCase();
                /*try{*/
                if (/^NUMBER$/i.test(fuc)) {// number
                    fuc = "NUMBER(-1,-1,property)";
                }
                else if (/\(\)/.test(fuc)) { // number()
                    fuc = "NUMBER(-1,-1,property)";
                }
                else if (/\(([0-9])*\)/.test(fuc)) {// number(5)
                    fuc = fuc.replace(")", ",-1,property)");
                }
                else {// number(5,2)
                    fuc = fuc.replace(")", ",property)");
                }
                return eval(fuc);
                /*}
                 catch(ex){
                 alert('checkNumber()函数运行时，发生异常，异常信息为:'+ex.description);
                 return PropResult(false);
                 }*/
                return new PropResult(true);
            }

            function NUMBER(L1, L2, property) {
                var Num = _cv.getPropElem(property.name).val();
                var msg = null;
                if (!/^(-|\+|([0-9]))([0-9])*\.?([0-9])*$/.test(Num)) {
                    msg = property.formatLabel + "含有不是数字的字符。";
                    return new PropResult(false, msg);
                }
                var Numstr = Num;
                Numstr = Numstr.replace("-", "");
                Numstr = Numstr.replace("+", "");

                if (/^00/.test(Numstr)) {
                    msg = msg = property.formatLabel + "不合理。";
                    ;
                    return new PropResult(false, msg);
                }

                Numstr = Numstr.replace(".", "");

                if (L1 > 0) {
                    if (Numstr.length > L1) {
                        msg = "输入数字" + property.formatLabel + "的长度太长，长度应不超过" + L1 + "位。";
                        return new PropResult(false, msg);
                    }
                    else {
                        if (L2 > 0) {
                            if (!/\./.test(Num)) {
                                if (Num.length > L1 - L2) {
                                    msg = "输入数字" + property.formatLabel + "的整数位太多，长度应不超过" + (L1 - L2) + "位。";
                                    return new PropResult(false, msg);
                                }
                            }
                            else {
                                var res = /\./.exec(Num);
                                try {
                                    var pos = res.index;
                                    if (pos > L1 - L2) {
                                        msg = "输入数字" + property.formatLabel + "的整数位太多，长度应不超过" + (L1 - L2) + "位。";
                                        return new PropResult(false, msg);
                                    }
                                    if (Numstr.length - pos > L2) {
                                        msg = "输入数字" + property.formatLabel + "的小数位太多，长度应不超过" + L2 + "位小数。";
                                        return new PropResult(false, msg);
                                    }
                                }
                                catch (ex) {
                                }
                            }
                        }
                    }
                }
                return new PropResult(true);
            }

            //时间类型校验
            function checkDate(property) {
                var value = _cv.getPropElem(property.name).val();
                var dataType = property.dataType;

                var r = /^\d{4}-\d{1,2}-\d{1,2}$/;
                var originFormat = "yyyy-MM-dd"
                if (!r.test(value)) {
                    var msg = property.formatLabel + "的格式不正确，正确的格式必须为" + originFormat + ",请更正。";
                    return new PropResult(false, msg);
                }
                return new PropResult(true);
            }
        },
        checkRequired:function (property, value) {
            if (property.required) {
                if (!value || value == "") {
                    var errorMsg = property.formatLabel + "必须填写，不能为空。";
                    this.setCheckError(property, errorMsg);
                    return false;
                }
                return true;
            }
            return true;
        },

        //设置错误信息
        setCheckError:function (prop, msg) {
            var validResult = this.validResultMap[prop.name];
            if (typeof(validResult) == "undefined") {
                validResult = this.validResultMap[prop];
            }
            validResult.setError(msg);
        },
        //设置检验正确
        setCheckRight:function (propName) {
            var validResult = this.validResultMap[propName];
            validResult.setPassed();
        },
        getPropElem:function (name) {
            var formDivTemp = this.config.formDiv;
            if (formDivTemp != null && formDivTemp != "") {
                var _elem = $("#" + formDivTemp + " #" + name);
                if (!_elem[0]) {
                    _elem = $("#" + formDivTemp + " [name=" + name + "]")
                }
                return _elem;
            } else {
                var _elem = $("#" + name);
                if (!_elem[0]) {
                    _elem = $("[name=" + name + "]")
                }
                return _elem;
            }

        }

    }
    function ValidResult(propName, propLabel) {
        this.propName = propName;
        this.propLabel = propLabel;
        this.status = this.UNCHECK;
    }

    $.checkValid = function (config) {
        return new CheckValid(config);
    }

    ValidResult.prototype = {
        //status -1 验证失败 0 未填值，初始态 1 验证通过
        ERROR:-1,
        UNCHECK:0,
        PASSED:1,
        setHintId:function (hintId) {
            this.hintId = hintId;
        },
        setError:function (msg) {
            this.status = this.ERROR;
            this.msg = msg;
        },
        setPassed:function () {
            this.status - this.PASSED;
        }
    }
    //用于保存一个属性的校验结果
    function PropResult(result, msg) {
        this.result = result;
        this.msg = msg;
    }

})(jQuery)
//扩展String对象
$.extend(String.prototype,
    {
        getCharLen:function () {
            return this.length;
        },
        getByteLen:function () {
            var len = 0;
            for (var i = 0; i < this.length; i++) {
                if (this.charCodeAt(i) > 255) len += 2;
                else len++;
            }
            return len;
        },
        trim:function () {
            var blank = /^\s+|\s+$|　/g;
            if (this instanceof String) {
                return this.replace(blank, "");
            } else {
                return this;
            }
        }
    });