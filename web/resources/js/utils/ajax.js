jQuery.extend({
    localAjax:function (a) {
        var o = $.extend({
            cache:false,
            type:'get',
            format:"json"
        }, a);

        if (o.type.toLocaleLowerCase() == "post" && !o.contentType) {
            o.contentType = "application/x-www-form-urlencoded; charset=utf-8";
        }
        o.data.format = o.format;
        function doRefresh(a) {

        }

        o.beforeSend = function () {
            if (a.beforeSend) {
                return a.beforeSend.call(this);
            }
        }
        o.success = function (data) {
            if (a.success) {
                return a.success.call(this, data);
            }
        }
        o.error = function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status != 0) {
                //检查是否未登录
                if (jqXHR.status == 406 && jqXHR.responseText.indexOf('NOLOGON') > -1) {
                    var result = $.evalJSON(jqXHR.responseText);
                    var uuid = result.uuid;
                    var orignUrl = result.orignUrl;
                    if (result.type == "admin") {
                        $.showLogonDialog(uuid, orignUrl, function () {
                            $.localAjax(a);
                        });
                    }

                    return;
                }
                if (jqXHR.status == 403 && jqXHR.responseText.indexOf('NOPRIVILEGE') > -1) {
                    alert("对不起，您没有操作该功能的权限！");
                    return;
                }
                if (a.error) {
                    alert("服务器繁忙，请稍后重试");
                    return  a.error.call(this, jqXHR, textStatus, errorThrown);
                }
            }
        }

        $.ajax(o);
    }
});