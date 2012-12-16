/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-1-4
 *作    者： 朱贤俊
 */
(function ($) {
    function eJqGrid(opt) {
        var ejqGrid = this;
        var defaultOpt = {
            url:'',
            postData:{format:'json'},
            datatype:"json",
            ajaxGridOptions:{
                cache:false,
                contentType:"application/x-www-form-urlencoded; charset=UTF-8"
            },
            mtype:"POST",
            height:"200",
            colModel:[
            ],
            onSelectRow:function (id) {
            },
            ondblClickRow:function (rowid, iRow, iCol, event) {
            },
            onBeforeRequest:function () {

            },
            onComplete:function () {

            },
            loadError:function () {
                alert("系统繁忙，请稍后...");
            },
            rowNum:10,
            rowList:[10, 20, 30, 40,50,100],
            pager:'',
            recordtext:"从 {0} 到 {1} ，共 {2} 条记录",
            emptyrecords:'没有符合查询条件的数据',
            loadtext:"正在加载...",
            pgtext:"{0} 共{1}页",
            pagerpos:'center',
            recordpos:'right',
            sortname:'',
            sortorder:"desc",
            viewrecords:true,
            scrollrows:false,
            multiselect:true,
            multiboxonly:true,
            autowidth:true,
            rownumbers:false,
            altRows:true,
            altclass:'ui-jqgrid-tdbg',
            prmNames:{page:"pageNo", rows:"pageSize", sort:"sortFieldName", order:"sortType"},
            jsonReader:{
                root:"data",
                id:"",
                page:function (obj) {
                    var pageInfo = obj.pageInfo;
                    if (obj.pageInfo) {
                        return pageInfo.pageNum;
                    } else {
                        return 1;
                    }
                },
                total:"pageInfo.pageCount",
                records:"pageInfo.recordCount",
                repeatitems:false,
                userdata:"data"
            },
            caption:""
        };

        this.opt = $.extend(true, defaultOpt, opt);
        this.opt.gridComplete = function () {
            ejqGrid.initColOprs();
            ejqGrid.opt.onComplete();
        }

        this.opt.beforeRequest = function () {
            $(".loading").removeClass("ui-state-default ui-state-active");//loading的样式
            $(".loading").html('<img src="' + resourceRoot + '/skins/shopadmin/images/loading.gif" />正在加载...');
            //初始化列表内容区显示高度
            var gridPager = 29;
            var auHeight = ejqGrid.opt.height;
            auHeight = top.$(window).height() - (Math.abs(ejqGrid.resultListJqObj.parent().parent().prev().offset().top) + gridPager) - gridPager;
            ejqGrid.resultListJqObj.reSize(auHeight, null);
            ejqGrid.opt.onBeforeRequest();
        }
        //添加操作列
        var colOpers = this.opt.colOper; //数组
        if ($.isArray(colOpers)) {
            $.each(colOpers, function (idx, colOper) {
                singleColOper(idx, colOper);
            })
        } else {
            singleColOper(0, colOpers);
        }
        function singleColOper(index, colOper) {
            if (colOper && colOper.operItems && colOper.operItems.length > 0) {
                if (colOper.colNum == undefined) {
                    colOper.colNum = ejqGrid.opt.colModel.length;
                }
                var colOperLabel = colOper.colOperLabel;
                var defaultColOperLabel = "操作";
                if (colOperLabel && colOperLabel != "") {
                    defaultColOperLabel = colOperLabel;
                }
                if (!colOper.width) {
                    colOper.width = 90;
                }
                ejqGrid.opt.colModel = ejqGrid.opt.colModel.insertAt(colOper.colNum, {label:defaultColOperLabel, name:'oper' + index, width:colOper.width, sortable:false});
            }
        }
    }

    /**
     * 初始化列表
     */
    eJqGrid.prototype.init = function () {
        return this.resultListJqObj.jqGrid(this.opt)
            .jqGrid('navGrid', this.opt.pager,
            {
                edit:false,
                add:false,
                del:false,
                search:false,
                refresh:true,
                refreshtitle:"刷新"
            });
    }
    /**
     * 初始化操作列
     */
    eJqGrid.prototype.initColOprs = function () {
        var ejqGrid = this;
        var colOpers = this.opt.colOper;
        if ($.isArray(colOpers)) {
            $.each(colOpers, function (idx, colOper) {
                singleInitColOpr(colOper);
            });
        } else {
            singleInitColOpr(colOpers);
        }
        function singleInitColOpr(colOper) {
            if (colOper && colOper.operItems && colOper.operItems.length > 0) {
                $.each(colOper.operItems, function (index, item) {
                    var colNum = colOper.colNum;
                    if (ejqGrid.opt.rownumbers) {
                        colNum++;
                    }
                    if (ejqGrid.opt.multiselect) {
                        colNum++;
                    }
                    var cells = ejqGrid.resultListJqObj.find(".ui-widget-content").find("td:eq(" + colNum + ")");
                    var _renderer = item.renderer;
                    var _rendererFlag = false;
                    if (_renderer && $.isFunction(_renderer)) {
                        _rendererFlag = true;
                    }
                    cells.each(function () {
                        var cell = $(this);
                        var id = cell.parent().attr("id");
                        var rowData = ejqGrid.resultListJqObj.getGridParam('userData')[(ejqGrid.resultListJqObj.getInd(id) - 1)];
                        var itemLable = $("<a title='" + item.label + "' style='cursor: pointer;'>" + item.label + "</a>");
                        if (item.className) {
                            itemLable = $("<a class='iconbg " + item.className + "' title='" + item.label + "' style='cursor: pointer;'></a>");
                        }
                        if (index != 0) {
                            itemLable.addClass("marl_5");
                        }
                        itemLable.click(function () {
                            if ($.isFunction(item.action)) {
                                item.action(id, cell, rowData);
                            }
                        });
                        if (_rendererFlag) {
                            itemLable.empty();
                            var showLabel = _renderer(id, cell, rowData);
                            if (showLabel instanceof jQuery) {
                                itemLable.append(showLabel);
                            } else {
                                var _reSpan = $("<span>" + showLabel + "</span>");
                                itemLable.append(_reSpan);
                            }
                        }
                        $(this).append(itemLable);
                    })
                });
            }
        }
    }

    $.fn.ejqgrid = function (opt) {
        var ejqgrid = new eJqGrid(opt);
        ejqgrid.resultListJqObj = this;
        $(window).bind('resize',
            function () {
                var workground = $("#workground");
                if(workground.css("display")=='none'){
                    workground = $("#workgroundSec");
                }
                var width = $(window).width();
                if (workground[0]) {
                    width = workground.width() - 3;
                }
                ejqgrid.resultListJqObj.reSize(null, width); //自适应宽度
            });

        return ejqgrid.init();
    }


    /**
     * 动态设置列表宽度和高度
     * @param height
     * @param width
     */
    $.fn.reSize = function (height, width) {
        if (height) {
            this.jqGrid('setGridHeight', height);
        }
        if (width) {
            this.jqGrid('setGridWidth', width);
        }
    }

    /**
     * 刷新列表
     */
    $.fn.refresh = function (isDelOper, recordsSize) {
        if (!isDelOper) {
            isDelOper = false;
        }
        if (!recordsSize) {
            recordsSize = 1;
        }
        var pageIndex = this.jqGrid('getGridParam', "page");
        var rowNum = this.jqGrid('getGridParam', "rowNum");
        if (rowNum == recordsSize) {
            recordsSize = 0;
        }
        var records = this.jqGrid('getGridParam', "records");
        if (isDelOper && ((records % rowNum) - recordsSize) == 0) {
            this.trigger("reloadGrid", {page:pageIndex - 1});
        } else {
            this.trigger("reloadGrid");
        }
    }

    /**
     * 查询列表
     * @param formDataJson
     */
    $.fn.query = function (formDataJson) {
        var defaultData = {
            format:'json'
        };
        formDataJson = $.extend({}, defaultData, formDataJson);
        var postDataJson = {};
        postDataJson.postData = formDataJson;
        this.jqGrid('setGridParam', postDataJson).trigger("reloadGrid", [
            {page:1}
        ]);
    }
    /**
     * 获取选择行的值
     */
    $.fn.getMultiSelectValues = function () {
        var results = this.jqGrid('getGridParam', 'selarrrow');
        if (results.length == 0) {
            alert("至少选择一条记录!");
            return null;
        }
        return results;
    }

    $.fn.getRowData = function (key) {
        var results = this.jqGrid('getRowData', key);
        if ($.trim(key) == "") {
            alert("key不能为空!");
            return null;
        }
        return results;
    }

    /**
     * 获取单选值
     */
    $.fn.getSingleSelectValue = function () {
        var result = this.getMultiSelectValues();
        if (result) {
            if (result.length > 1) {
                alert("只能选择一条记录!");
                return null;
            }
        } else {
            return null;
        }

        return result;
    }


})(jQuery);