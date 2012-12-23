/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-12-23
 *作    者： 朱贤俊
 */
$(document).ready(function () {
    $("#Chinese").click(function () {
        setLocale("zh_CN");
        window.location.reload();
    });
    $("#English").click(function () {
        setLocale("en_US");
        window.location.reload();
    });
})