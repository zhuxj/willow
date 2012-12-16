/**
 * 2009-6-16
 *Copyright 2009
 *Book软件公司, 版权所有 违者必究
 *Author 陈少春
 */
/**
 * checkbox 全选操作
 * @author     shaoyun(若水老人) <shaoyun at yeah.net>
 * @copyright  Copyright (c) 2008 (http://www.devjs.com)
 * @example $('input[@type=checkbox][@name=checkAll]').checkbox();
 * 反选 : .toggle()
 * 全选 : .checked()
 * 全不选 : .unchecked()
 * 获取字符串值 : .val()
 */
$.fn.checkbox = function() {
    //根据事件源的选中状态，同步选择器中的其他的checkbox的状态
    this.toggle = function(ele) {
        var srcElement = ele.srcElement || ele.target;
        var flag = srcElement.checked;
        $(this).each(function() {
            if (!flag) {
                $(this).attr('checked', false);
            } else {
                $(this).attr('checked', true);
            }
        });
    };

    //    反选
    this.toggleTrCheckBox = function(ele) {
        var srcElement = ele.srcElement || ele.target;
        if (srcElement.tagName == "TD") {
            srcElement = srcElement.parentNode;
        }
        if (srcElement.tagName == "TR") {
            var flag = srcElement.cells[1].children[0].checked;
            $(this).each(function() {
                if (this.parentNode.parentNode.rowIndex != srcElement.rowIndex) {
                    $(this).attr('checked', false);
                }
            });
        }

    };

    //    全选
    this.checked = function() {
        $(this).attr('checked', true);
    };
    //    全不选
    this.unchecked = function(ele) {
        $(this).attr('checked', false);
    };
    //    获取已选中值,并以字符串返回数据
    this.val = function() {
        var string = '';
        $(this).each(function() {
            if (this.checked && $(this).val()) {
                if (string) {
                    string += ',';
                }
                string += $(this).val();
            }
            ;
        });
        return string;
    };

    this.attr = function(key) {
        var string = '';
        $(this).each(function() {
            if (this.checked && $(this).attr(key)) {
                if (string) {
                    string += ',';
                }
                string += $(this).attr(key);
            }
        });
        return string;
    };

    // 获得选择的ISBN
    this.isbn = function() {
        var string = '';
        $(this).each(function() {
            if (this.checked && $(this).get("0").isbn) {
                if (string) {
                    string += ',';
                }
                string += $(this).get("0").isbn;
            }
            ;
        });
        return string;
    };
    return this;
};