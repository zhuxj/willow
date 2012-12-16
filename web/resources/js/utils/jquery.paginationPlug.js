/**
 *版权声明：中图一购网络科技有限公司 版权所有 违者必究 2011
 *日    期： 11-7-28
 *作    者： 陈文炎
 *功能说明：分页插件
 * 参数说明：
 * pageDivId:分页div的id,
 * maxPages:最大显示页数,
 * itemsPerPage:每页显示条数,
 * showPageNos:分页框显示个数,
 * showJump：是否显示跳转,true:是,false：否
 * pageCallback：分页回调函数
 */
(function ($) {
    $.paginationPlug = function (settings) {
        var defaultSetting = {pageDivId:null, maxPages:null, itemsPerPage:10, showPageNos:3, showJump:true, cssStyle:"1", pageCallback:null}
        var s = $.extend({}, defaultSetting, settings);
        var cssStyle = $("<link/>");
//        $("head").append(cssStyle);
        if ($.browser.msie) {
            cssStyle.attr("type", "");
        }
        cssStyle.attr("href", "/skins/default/css/pagination/pagination_0" + s.cssStyle + ".css");
        cssStyle.attr("type", "text/css");
        cssStyle.attr("rel", "stylesheet");
        var othis = this;
        if (s.pageDivId == null || s.pageCallback == null || !$.isFunction(s.pageCallback)) {
            return othis;
        }
        var callbackFunc = s.pageCallback;
        var pageDiv = $("#" + s.pageDivId);
        var inPageDiv = {};
        var prevPage = {};
        var nextPage = {};
        var omitPage = {};
        var jumpSpan = {};
        var jumpTxtBox = {};
        var jumpBtn = {};
        var pageItems = [];
        var currPage = 1;
        var totalPages = 1;
        var totalCounts = 1;
        this.showPagination = function (totalCount, currentPage) {
            if (currentPage > 1 && totalCount == 0) {
                callbackFunc(currentPage - 1);
            }
            initPage();
            totalCounts = totalCount;
            currPage = currentPage;
            pageItems = [];
            if (totalCounts == 0 || $.type(totalCounts) != "number" || currentPage == 0 || $.type(currentPage) != "number") {
                return othis;
            }
            totalPages = totalCounts % s.itemsPerPage == 0 ? totalCounts / s.itemsPerPage : parseInt(totalCounts / s.itemsPerPage) + 1;
            if (s.maxPages != null && $.type(s.maxPages) == "number") {
                if (totalPages > s.maxPages) {
                    totalPages = s.maxPages;
                }
            }

            var showPageCount = s.showPageNos;

            if (showPageCount > totalPages) {
                showPageCount = totalPages;
            }
            var prePageCount = parseInt(s.showPageNos / 2);
            var firstPageNo = currentPage > prePageCount + 1 ? currentPage - prePageCount : currentPage - prePageCount > 1 ? currentPage - prePageCount : 1;
            if ((totalPages - firstPageNo + 1) < showPageCount) {
                firstPageNo = totalPages - showPageCount + 1;
            }
            for (var i = 0; i < showPageCount; i++) {
                var item = $("<a href='#'>" + firstPageNo + "</a>");
                if (currPage == parseInt(item.text())) {
                    item = $("<span class='paging_current'>" + firstPageNo + "</span>");
                } else {
                    item.click(function () {
                        currPage = parseInt($(this).text());
                        if (currPage == 1) {
                            prevPage.hide();
                        }
                        if (currPage == totalPages) {
                            nextPage.hide();
                        }
                        othis.showPagination(totalCounts, currPage)
                        callbackFunc(currPage);
                        return false;
                    });
                }
                pageItems.push(item);
                if (firstPageNo == totalPages) {
                    omitPage.hide();
                    break;
                } else {
                    omitPage.show();
                    firstPageNo++;
                }
            }
            showPage();
            return othis;
        }

        function initPage() {
            pageDiv.html("");
            pageDiv.removeClass().addClass("paging clearfix");
            inPageDiv = $("<div class='paging_inner'></div>");
            prevPage = $("<a class='page_on'>上一页</a>");
            nextPage = $("<a class='page_next'>下一页</a>");
            omitPage = $("<span class='paging_break'>...</span>");
            jumpSpan = $("<span class='paging_skip'></span>");
            jumpTxtBox = $("<input type='text'/>");
            jumpSpan.append("到第");
            jumpSpan.append(jumpTxtBox);
            jumpSpan.append("页");
            jumpBtn = $("<button type='button'>确定</button>");
        }

        function showPage() {
            if (currPage == 1) {
                prevPage.hide();
            } else {
                prevPage.click(function () {
                    nextPage.show();
                    currPage--;
                    if (currPage == 1) {
                        prevPage.hide();
                    } else {
                        prevPage.show();
                    }
                    othis.showPagination(totalCounts, currPage);
                    callbackFunc(currPage);
                    return false;
                });
            }

            if (currPage == totalPages) {
                nextPage.hide();
            } else {
                nextPage.click(function () {
                    prevPage.show();
                    currPage++;
                    if (currPage == totalPages) {
                        nextPage.hide();
                    } else {
                        nextPage.show();
                    }
                    othis.showPagination(totalCounts, currPage);
                    callbackFunc(currPage);
                    return false;
                });
            }
            inPageDiv.append(prevPage);
            for (var i = 0; i < pageItems.length; i++) {
                inPageDiv.append(pageItems[i]);
            }
            if (totalPages > s.showPageNos) {
                inPageDiv.append(omitPage);
            }
            inPageDiv.append(nextPage);
            inPageDiv.append("<span class='page_much'>共" + totalPages + "页</span>");
            if (s.showJump) {
                jumpTxtBox.val(currPage);
                jumpTxtBox.mouseover(function () {
                    jumpTxtBox.select();
                });
                jumpTxtBox.keyup(function (e) {
                    if (e.keyCode == 13) {
                        jumpToPage($(this).val());
                    }
                });
                jumpTxtBox.bind("keypress",
                    function () {
                        if (event.keyCode == 46) {
                            if (this.value.indexOf(".") != -1) {
                                return false;
                            }
                        } else {
                            return event.keyCode >= 46 && event.keyCode <= 57;
                        }
                    }).bind("blur",
                    function () {
                        if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
                            this.value = this.value.substr(0, this.value.length - 1);
                        } else if (isNaN(this.value)) {
                            this.value = "";
                        }
                    }).bind("dragenter", function () {
                        return false;
                    });
                inPageDiv.append(jumpSpan);
                jumpBtn.click(function () {
                    jumpToPage(jumpTxtBox.val());
                });
                inPageDiv.append(jumpBtn);
            }
            pageDiv.append(inPageDiv);
        }

        function jumpToPage(jumpNo) {
            jumpNo = parseInt(jumpNo);
            if (isNaN(jumpNo)) {  //判断是否NaN，表示不为数值
                alert("请输入数值");
                return false;
            }
            if (jumpNo == 0) {
                alert("输入错误");
                return false;
            }
            if (jumpNo == currPage) {
                alert("当前是第" + currPage + "页");
                return false;
            }
            if (jumpNo > totalPages) {
                alert("页数不能超过" + totalPages + "页");
                return false;
            }
            othis.showPagination(totalCounts, jumpNo);
            callbackFunc(jumpNo);
        }

        return othis;
    }
})(jQuery)