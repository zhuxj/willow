/**
 * 分割从cookie中取出的字符串，转化为包含json格式数据的数组
 * @param data  从cookie中取出的字符串
 * 字符串的格式为：[{newid:"abc",newName:"fds"},{newid:"abc1",newName:"fdsf2"}]
 */
function splitData(data) {
    if (data == undefined || data == null) {
        return [];
    }
    return eval(data);
}

function toJsonStr(arr) {
    var i = 0;
    var str = '[';
    for (i = 0; i < arr.length - 1; i++) {
        str += JSON.stringify(arr[i]) + ',';
    }
    str += JSON.stringify(arr[i]);
    str += ']';
    return str;
}

function containJsonKey(arr, keyName1, keyValue1, keyName2, keyValue2, keyName3, keyValue3) {
    var i = 0;
    for (i = 0; i < arr.length; i++) {
        if (keyName2 != null && keyValue2 != null) {
            if (arr[i][keyName1] == keyValue1 && arr[i][keyName2] == keyValue2 && arr[i][keyName3] == keyValue3) {
                return true;
            }
        }
        else {
            if (arr[i][keyName1] == keyValue1) {
                return true;
            }
        }
    }
    return false;
}

/**
 * 以JSON格式添加cookie
 * @param cookieName    cookie名称
 * @param key1
 * @param value1
 * @param key2
 * @param value2
 * @param length 最大长度
 * @param options
 */
function addKeyValue(cookieName, key1, value1, key2, value2, key3, value3, length, options) {
    if (typeof(cookieName) == "undefined") {
        return;
    }
    value1 = $.trim(value1);
    if (value1 == "") {
        return;
    }
    var value = $.cookie(cookieName);
    if (value == null) {
        value = "[]";
    }

    var valObj = eval(value);
    var newItemStr = "";
    if (key1 != null || value1 != null) {
        value1 = value1.replace(new RegExp("\"", "gm"), "'");
        if (key2 != null || value2 != null) {
            value2 = value2.replace(new RegExp("\"", "gm"), "'");
            if (key3 != null || value3 != null) {
                value3 = value3.replace(new RegExp("\"", "gm"), "'");
                newItemStr += '{"' + key1 + '":"' + value1 + '", "' + key2 + '":"' + value2 + '", "' + key3 + '":"' + value3 + '"}';
            }
            else {
                newItemStr += '{"' + key1 + '":"' + value1 + '", "' + key2 + '":"' + value2 + '"}';
            }
        }
        else {
            newItemStr += '{"' + key1 + '":"' + value1 + '"}';
        }
    }
    var newItem = $.parseJSON(newItemStr);
    if (!containJsonKey(valObj, key1, value1, key2, value2, key3, value3)) {
        valObj.unshift(newItem);
        if (valObj.length > length) {
            valObj.pop();
        }
        var arrStr = $.toJsonString(valObj);
        $.cookie(cookieName, arrStr, options);
    }
}

/**
 * 浏览图书历史，最多保存8本，先进先出。
 * @param goodsId
 * @param goodsName
 */
function addViewGoods(goodsId, goodsName, imageId) {
    addKeyValue("viewGoods", "goodsId", goodsId, "goodsName", goodsName, "imageId", imageId, 8, {expires:30, path:'/'});
}

/**
 *
 */
function removeViewGoods() {
    $.cookie("viewGoods", null, {path:'/'});
}


/**
 *  获取浏览图书历史，以数组形式返回，数组内的格式为JSON格式
 */
function getViewGoods() {
    var value = $.cookie("viewGoods");
    return splitData(value);
}

/**
 * 商品标准分类选择历史
 * @param cid
 * @param catName
 * @param catPath
 */
function addChooseItemCat(cid, catName, catPath) {
    addKeyValue("chooseItemCats", "cid", cid, "catName", catName, "catPath", catPath, 8, {expires:30, path:'/shopadmin'});
}

function removeChooseItemCat() {
    $.cookie("chooseItemCats", null, {path:'/shopadmin'});
}

function getChooseItemCat() {
    var value = $.cookie("chooseItemCats");
    return splitData(value);
}

function addChooseItemCatDis(cid, catName, catPath) {
    addKeyValue("chooseItemCats", "cid", cid, "catName", catName, "catPath", catPath, 8, {expires:30, path:'/distributor'});
}

function removeChooseItemCatDis() {
    $.cookie("chooseItemCats", null, {path:'/distributor'});
}

function getChooseItemCatDis() {
    var value = $.cookie("chooseItemCats");
    return splitData(value);
}

//获取是否在登录的时候自动显示用户名
function getRememberMemberName() {
    var rememberMemberName = $.cookie("rememberMemberName");
    if (rememberMemberName && rememberMemberName == "Y") {
        return true;
    }
    return false;

}

//获取是否在登录的时候自动显示用户名
function getRememberDistributorUserName() {
    var rememberDistributorUserName = $.cookie("rememberDistributorUserName");
    if (rememberDistributorUserName && rememberDistributorUserName == "Y") {
        return true;
    }
    return false;

}

//获取存放在cookie中的会员登录名
function getLogonMemberName() {
    var memberName = $.cookie("memberName");
    if (memberName != "undefined") {
        return memberName;
    }
    return "";
}

//获取存放在cookie中的会员登录名
function getLogonDistributorUserName() {
    var distributorUserName = $.cookie("distributorUserName");
    if (distributorUserName != "undefined") {
        return distributorUserName;
    }
    return "";
}

//获取存放再cookie中的后台用户登录名
function getLogonUserName() {
    var userName = $.cookie("userName");
    if (userName != "undefined") {
        return userName;
    }
    return "";
}


function getLastOrderPayCode() {
    var value = $.cookie("lastOrderPayCode");
    return value;
}

function addLastOrderPayCode(payCode) {
    $.cookie("lastOrderPayCode", payCode, {expires:30, path:'/member/order'});
}

function setLastSelectProvince(curProvinceId, curProvinceName) {
    $.cookie("lastSelectProvinceId", curProvinceId, {expires:30, path:'/'});
    $.cookie("lastSelectProvinceName", curProvinceName, {expires:30, path:'/'});
    setGoodsDetailLastSelectArea("", "", "");
}

function getlastLastSelectProvinceName() {
    return  $.cookie("lastSelectProvinceName");
}
function getlastLastSelectProvinceId() {
    return  $.cookie("lastSelectProvinceId");
}

/**
 * 商品详细页选择省市区缓存
 */
function setGoodsDetailLastSelectArea(provinceName, cityName, districtName) {
    $.cookie("goodsDetailLastSelectProvince", provinceName, {expires:30, path:'/goods/showGoodsDetailPage/'});
    $.cookie("goodsDetailLastSelectCity", cityName, {expires:30, path:'/goods/showGoodsDetailPage/'});
    $.cookie("goodsDetailLastSelectDistrict", districtName, {expires:30, path:'/goods/showGoodsDetailPage/'});
}
function getGoodsDetailLastSelectProvince() {
    return  $.cookie("goodsDetailLastSelectProvince");
}
function getGoodsDetailLastSelectCity() {
    return  $.cookie("goodsDetailLastSelectCity");
}
function getGoodsDetailLastSelectDistrict() {
    return  $.cookie("goodsDetailLastSelectDistrict");
}

/*记录当前的功能菜单*/
function setCurrentMenu(fl, sl, tl) {
    if (fl != null) {
        $.cookie("first_level_menu", fl, {expires:30, path:'/shopadmin'});
    }
    if (sl != null) {
        $.cookie("first_second_menu", sl, {expires:30, path:'/shopadmin'});
    }
    if (tl != null) {
        $.cookie("first_third_menu", tl, {expires:30, path:'/shopadmin'});
    }
}
function getCurrentMenu() {
    var current = {};
    current.fl = $.cookie("first_level_menu");
    current.sl = $.cookie("first_second_menu");
    current.tl = $.cookie("first_third_menu");
    return current;
}

/**
 * 设置语言
 * @param locale
 */
function setLocale(locale) {
    $.cookie("clientLanguage", locale, {expires:30, path:'/'});
}







