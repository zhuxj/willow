/**
*版权声明：${codeGenConfig.developerConfig.company!} 版权所有 违者必究
*日    期： ${date!}
*作    者： ${codeGenConfig.developerConfig.developer!}
*/
$(document).ready(function () {
    $("#_back").click(function () {
        top.jq.workgroundManager.returnPage(false);
    });
})