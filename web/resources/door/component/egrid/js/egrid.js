(function ($) {

    /**
     * 表格
     * @param opt
     */
    function EGrid(opt) {

        var defaultOpt = {
            container:"",
            keyColumn:"",
            tableCss:"cmp_egrid",
            url:"",
            pageSize:10,
            needPageBar:true,
            showTableIndex:false,
            showCheckBox:true,
            checkBoxCss:"",
            autoLoad:true,
            isDataCache:false,
            specialGrid:false,
            needMessage:true,
            autoLoadNoMessage:"您好，该项没有任何记录。",
            noMessage:"没有符合查询条件的数据。",
            params:{},
            events:{},
            toolbars:{},
            columns:[],
            row:{},
            button:null
        };
        this.opt = $.extend({}, defaultOpt, opt);
        if (this.opt.specialGrid) {
            this.row = new Row(this.opt.row, this);
        } else {
            this.columns = [];
            for (var i = 0; i < opt.columns.length; i++) {
                var column = new Column(opt.columns[i], this);
                this.columns.push(column);
            }
        }
        this.dataProvider = new DataProvider(this);
        this.pagebar = new PageBar(this);
        this.checkBoxManager = new CheckBoxManager(this);

    }

    EGrid.prototype.render = function () {
        this.$container = $('#' + this.opt.container);
        if (this.opt.specialGrid) {
            this.renderSpecialGrid();
        } else {
            this.renderTable();
        }
    }


    EGrid.prototype.init = function () {

        this.render();

        var grid = this;

        /*对外接口,屏蔽内部变量，规范调用方法。*/
        var method = new Method();

        method.query = function (opt) {
            if (!opt) {
                opt = {};
            }
            opt.pageNum = 1;
            if (opt.params) {
                if (opt.params.pageNo) {
                    opt.pageNum = opt.params.pageNo;
                    delete opt.params.pageNo;
                }
                if (opt.params.pageSize) {
                    opt.pageSize = opt.params.pageSize;
                    delete opt.params.pageSize;
                }
            }
            grid.query(opt);
            return method;
        }

        method.refresh = function () {//刷新列表  add by zhuxj 2011-12-09
            grid.pagebar.pageParams.pageNum = this.getCurrentPageNo();
            grid.dataProvider.load();
        }

        method.getAllData = function () {
            return grid.getAllData();
        }

        method.getCheckBoxValues = function () {
            return grid.getCheckBoxValues();
        }

        method.getCurrentPageNo = function () {
            return grid.pagebar.pageParams.pageNum;
        }

        method.renderData = function (data) {
            if (data) {
                grid.renderData(data);
            }
            return method;
        }

        method.getCacheDataKeys = function () {
            return grid.checkBoxManager.selectData;
        }

        method.setCacheDataKeys = function (data) {
            if (data) {
                grid.checkBoxManager.selectData = data;
            }
            return method;
        }


        grid.method = method;

        return method;
    }


    EGrid.prototype.renderTable = function () {
        var $table = $('<table></table>');
        var $messageDiv = $('<div></div>');
        $table.addClass(this.opt.tableCss);
        $table.attr("id", this.opt.container + "table");
        this.$container.after($messageDiv);
        this.$container.after($table);

        // 构造表格标题行
        if (this.columns) {
            var $thead = $('<thead></thead>').appendTo($table);
            var $tr = $('<tr></tr>').appendTo($thead);

            if (this.opt.showTableIndex) {
                $('<th class="cmp_egrid_order_th"></th>').appendTo($tr);
            }

            if (this.opt.showCheckBox) {
                this.checkBoxManager.renderHeader($tr);
            }

            for (var i = 0; i < this.columns.length; i++) {
                this.columns[i].renderHeader($tr);
            }

            $($tr).children('th:first').addClass("cmp_egrid_bor_l");
            $($tr).children('th:last').addClass("cmp_egrid_bor_r");
        }
        var $tbody = $('<tbody></tbody>').appendTo($table);
        if (this.pagebar.needPageBar || !$.isEmptyObject(this.pagebar.toolbars)) {
            var $pagebar = $('<div class="cmp_egrid_toolbar clearfix"></div>');
            $table.after($pagebar);
            this.$pagebar = $pagebar;
            this.pagebar.render();
        }

        if (this.opt.autoLoad) {
            this.dataProvider.load();
        } else {
            this.pagebar.pageCount = 0;
        }
        this.$tbody = $tbody;
        this.$messageDiv = $messageDiv;
    }

    EGrid.prototype.renderSpecialGrid = function () {
        if (this.pagebar.needPageBar || !$.isEmptyObject(this.pagebar.toolbars)) {
            var $pagebar = $('<div class="cmp_egrid_toolbar clearfix"></div>');
            this.$container.after($pagebar);
            this.$pagebar = $pagebar;
            this.pagebar.render();
        }

        if (this.opt.autoLoad) {
            this.dataProvider.load();
        } else {
            this.pagebar.pageCount = 0;
        }

        var $messageDiv = $('<div></div>');
        this.$container.after($messageDiv);

        this.$messageDiv = $messageDiv;
    }

    EGrid.prototype.getCheckBoxValues = function () {
        return this.checkBoxManager.getValues();
    }

    EGrid.prototype.turnPage = function (pageNum) {
        if (pageNum) {
            this.pagebar.pageParams.pageNum = pageNum;
        }

        this.dataProvider.load();
    }

    EGrid.prototype.query = function (opt) {
        if (opt) {
            if (opt.params) {
                this.dataProvider.params = opt.params;
            }
            if (opt.pageSize) {
                this.pagebar.pageParams.pageSize = opt.pageSize
            }
            if (opt.pageNum) {
                this.pagebar.pageParams.pageNum = opt.pageNum
            }
        }
        this.dataProvider.load();
    }

    EGrid.prototype.getAllData = function () {
        return this.dataProvider.data;
    }

    EGrid.prototype.renderData = function (data) {
        this.dataProvider.data = data;
        var isFirst = false;
        if (this.opt.autoLoad) {
            isFirst = true;
            delete this.opt.autoLoad;
        }
        if (data.length == 0) {
            if (this.opt.needMessage) {
                var noMessageDiv = $('<div style="text-align:center; padding:20px 0;"><b></b></div>');
                if (isFirst) {
                    noMessageDiv.find("b").text(this.opt.autoLoadNoMessage);
                    this.$messageDiv.html(noMessageDiv);
                } else {
                    noMessageDiv.find("b").text(this.opt.noMessage);
                    this.$messageDiv.html(noMessageDiv);
                }
            }


            if (this.$pagebar) {
                this.$pagebar.hide();
            }
            if (!this.opt.specialGrid) {
                this.$tbody.hide();
            }
            if (this.opt.specialGrid) {
                this.$container.empty();
            }
        } else {
            if (this.opt.specialGrid) {
                this.renderSpecialGridData(data);
            } else {
                this.renderTableData(data);
            }
            if (this.pagebar.needPageBar || !$.isEmptyObject(this.pagebar.toolbars)) {
                this.pagebar.render();
            }
        }
    }

    EGrid.prototype.renderTableData = function (data) {
        if (this.$pagebar) {
            this.$pagebar.show();
        }
        this.$messageDiv.empty();
        this.$tbody.empty();
        this.$tbody.show();
        if (this.opt.showCheckBox) {
            this.checkBoxManager.resetHeader();
            if (!this.opt.isDataCache) {
                this.checkBoxManager.clearCacheData();
            }
        }
        if (data != null && data) {
            for (var c = 0; c < data.length; c++) {
                var record = data[c];

                var $tr = $('<tr></tr>').appendTo(this.$tbody);
                $tr.hover(function () {
                    $(this).toggleClass("hover")
                }, function () {
                    $(this).toggleClass("hover")
                });
                if (c % 2 != 0) {
                    $tr.addClass("cmp_egrid_even_tr")
                }

                if (this.opt.showTableIndex) {
                    var index = 1 + parseInt(c);
                    if (this.pagebar.needPageBar) {
                        index += ((this.pagebar.pageParams.pageNum - 1) * this.pagebar.pageParams.pageSize);
                    }
                    $tr.append('<td class="cmp_egrid_order_th" align="center" index="' + index + '">' + index + '</td>');
                }

                var keyField = getValue(this.opt.keyColumn, record);
                if (this.opt.showCheckBox) {
                    var $td = $('<td align="center"></td>').appendTo($tr);
                    this.checkBoxManager.render(keyField, c, $td);
                }

                for (var d = 0; d < this.columns.length; d++) {
                    var title = record[this.columns[d].column];

                    var $td = $('<td title="' + (title ? title : "") + '"></td>').appendTo($tr);
                    $td.css("width", this.columns[d].width);
                    $td.addClass(this.columns[d].cellCss);
                    var displayCell = this.columns[d].renderer(record, keyField, this.method, d, c, $td);
                    if (displayCell != undefined) {
                        $td.append(displayCell);
                    }
                }
            }
            if (this.$tbody.find(":checkbox:checked").length == this.pagebar.pageParams.pageSize) {
                this.checkBoxManager.$checkAllBox.attr("checked", true);
            }
        }
    }
    EGrid.prototype.renderSpecialGridData = function (data) {
        this.$container.empty();
        this.$messageDiv.empty();
        if (data != null && data) {
            for (var c = 0; c < data.length; c++) {
                var record = data[c];
                var keyField = getValue(this.opt.keyColumn, record);
                var displayRow = this.row.renderer(record, keyField, this.method, c);
                if (displayRow) {
                    this.$container.append(displayRow);
                }
            }
        }
        this.$pagebar.show();
    }


    /**
     * 数据供应者.进行数据获取请求.
     * @param opt
     * @param grid
     */
    function DataProvider(grid) {
        $.extend(this, {
            url:grid.opt.url,
            params:grid.opt.params,
            grid:grid,
            events:grid.opt.events,
            data:[]
        });
    }

    DataProvider.prototype.getDataByIndex = function (index) {
        return this.data[index];
    }

    DataProvider.prototype.load = function () {
        var gridParam = {
            pageNo:this.grid.pagebar.pageParams.pageNum,
            pageSize:this.grid.pagebar.pageParams.pageSize
        };
        gridParam.pageNo = parseInt(this.grid.pagebar.pageParams.pageCount) == -1 ? gridParam.pageNo : (parseInt(this.grid.pagebar.pageParams.pageCount) >= parseInt(gridParam.pageNo) ? gridParam.pageNo : this.grid.pagebar.pageParams.pageCount);
        $.localAjax({
            url:this.url,
            data:$.extend(this.params, gridParam),
            dataType:'json',
            type:'post',
            context:this,
            beforeSend:function () {
                if (this.grid.opt.button && this.grid.opt.button.hasClass("disabled")) {
                    return false;
                }
                disabledButton(this.grid);
                $('#' + this.grid.opt.container + "_loadingDiv").html("正在加载数据...");
            },
            success:function (result) {
                if (result) {
                    if (!result.data) {
                        result.data = [];
                    }
                } else {
                    return;
                }
                $('#' + this.grid.opt.container + "_loadingDiv").html("");
                if (this.grid.opt.needPageBar) {
                    if (!result.pageInfo || typeof(result.pageInfo.pageCount) == "undefined") {
                        alert("必须返回翻页参数");
                        return;
                    }
                    if (result.pageInfo.pageNum > 1 && result.data.length == 0) {
                        this.grid.pagebar.pageParams = { pageNum:1,
                            pageCount:-1,
                            pageSize:10}
                        removeDisabledButton(this.grid);
                        this.grid.query();
                        return;
                    }
                    this.grid.pagebar.pageParams = result.pageInfo;
                }

                this.grid.renderData(result.data);
                if (this.events && this.events.onLoad) {
                    this.events.onLoad(result);
                }
            },
            error:function (jqXHR, statusText, error) {
                $('#' + this.grid.opt.container + "_loadingDiv").html("");
                this.grid.$messageDiv.html('<div style="text-align:center; padding:20px 0;color:red;"><b>服务器繁忙，请稍候再试！</b></div>');
                this.grid.$pagebar.hide();
            },
            complete:function (jqXHR, textStatus) {
                removeDisabledButton(this.grid);
            }
        });
    }

    /**
     * 表格列.
     * @param opt
     * @param grid
     */
    function Column(opt, grid) {
        $.extend(this, {
            column:"",
            label:"",
            width:"auto",
            headerCss:"cmp_tanle_tdc",
            cellCss:"cmp_tanle_tdc",
            displayLength:100,
            actions:"",
            actionsSplit:"&nbsp;&nbsp;"
        }, opt);
        this.actions = opt.actions;
        if (opt.actionsSplit) {
            this.actionsSplit = opt.actionsSplit;
        }
        this.grid = grid;
    }

    Column.prototype.renderHeader = function (tr) {
        var $th = $('<th>' + this.label + '</th>').appendTo(tr);
        $th.css("width", this.width);
        $th.addClass(this.headerCss);
    }

    Column.prototype.renderer = function (record, keyField, method, colNo, rowNo, cell) {
        if (this.actions) {
            var urls = [];
            var split = "";
            for (var i = 0; i < this.actions.length; i++) {
                cell.append(split);
                var act = $("<a href='javascript:return false;'>" + this.actions[i].label + "</a>").appendTo(cell);
                split = this.actionsSplit;
                act.click($.proxy(function (e) {
                    this.actions[this.index].action.call(e.target, record, keyField, method, colNo, rowNo, cell);
                    return false;
                }, {index:i, actions:this.actions}));
                urls.push(act);
            }
            return;
        } else {
            var result = getValue(this.column, record);
            if (result != null && this.displayLength != null && this.displayLength > 0 && result.length > this.displayLength) {
                result = result.substring(0, this.displayLength) + "<span>...</span>";
            } else if (result != null) {
                result = getValue(this.column, record);
            } else {
                result = "";
            }
        }
        return result;
    }

    /**
     * 特殊表格行元素
     * @param opt
     * @param grid
     */
    function Row(opt, grid) {
        $.extend(this, opt);
        this.grid = grid;
    }

    Row.prototype.renderer = function (record, keyField, grid, rowNo) {
        return "";
    }
    /**
     * 翻页条.
     * @param opt
     * @param grid
     */
    function PageBar(grid) {
        var opt = grid.opt;
        this.pageParams = $.extend({
            pageNum:1,
            pageCount:-1,
            pageSize:10
        }, {pageSize:opt.pageSize});
        this.grid = grid;
        this.needPageBar = opt.needPageBar;
        this.toolbars = opt.toolbars;
    }

    PageBar.prototype.renderToolBar = function () {
        var $toolbar = $("<div class='cmp_egrid_toolbar_left'></div>").appendTo(this.grid.$pagebar);

        if ($.type(this.toolbars) == 'array') {
            for (var i = 0; i < this.toolbars.length; i++) {
                var btn = $('<button class="btn_01" type="button">' + this.toolbars[i].label + '</button>').appendTo($toolbar);
                btn.click(this.toolbars[i].action);
            }
        } else {
            $toolbar.append(this.toolbars);
        }
    }

    PageBar.prototype.renderPagination = function () {
        var pageToopHTML =
            "<div class='cmp_egrid_toolbar_right'><div class='cmp_egrid_pagination'>" +
                "<div class='cmp_egrid_inpagination'>" +
                '<span class="cmp_egrid_page_loading" id="' + this.grid.opt.container + '_loadingDiv"></span>' +
                '<a class="cmp_egrid_page_prev" href="#" id="' + this.grid.opt.container + '_prev_page">上一页</a>' +
                '<span class="cmp_egrid_page_current">' + this.pageParams.pageNum + '</span>' +
                '<a class="cmp_egrid_page_next" href="#" id="' + this.grid.opt.container + '_next_page">下一页</a>' +
                '<span class="cmp_egrid_page_much">共' + this.pageParams.pageCount + '页</span>' +
                '<span class="cmp_egrid_page_jump">到第<input id="' + this.grid.opt.container + '_pageNum" value="' + this.pageParams.pageNum + '" type="text" />页</span>' +
                '<input value="确定" id="' + this.grid.opt.container + '_turnPageBtn" type="button" />' +
                "</div>" +
                "</div></div>";

        // 构造表格翻页工具栏
        this.grid.$pagebar.append(pageToopHTML);

        var $prevPage = $("#" + this.grid.opt.container + "_prev_page");
        var $nextPage = $("#" + this.grid.opt.container + "_next_page");
        var $turnPageBtn = $('#' + this.grid.opt.container + "_turnPageBtn");
        var $pageInput = $('#' + this.grid.opt.container + "_pageNum");

        if (parseInt(this.pageParams.pageSize) < 1) {
            $prevPage.hide();
            $nextPage.hide();
        }

        if (parseInt(this.pageParams.pageNum) >= parseInt(this.pageParams.pageCount)) {
            $nextPage.addClass("disabled");
            $nextPage.addClass("next_disabled");
        }

        if (parseInt(this.pageParams.pageNum) == 1) {
            $prevPage.addClass("disabled");
            $prevPage.addClass("prev_disabled");
        }

        //-------------------触发分页按钮事件----------------------

        $prevPage.click($.proxy(function (e) {
            if ($prevPage.hasClass("disabled") || $prevPage.hasClass("prev_disabled")) {
                return false;
            }
            this.grid.turnPage(this.pageParams.pageNum - 1);
            return false;
        }, this));

        $nextPage.click($.proxy(function (e) {
            if ($nextPage.hasClass("disabled") || $nextPage.hasClass("next_disabled")) {
                return false;
            }
            this.grid.turnPage(parseInt(this.pageParams.pageNum) + 1);
            return false;
        }, this));

        $turnPageBtn.click($.proxy(function (e) {
            if ($turnPageBtn.hasClass("disabled")) {
                return false;
            }
            var pageNum = $pageInput.val();
            if (pageNum == null || pageNum == '') {
                alert("请输入跳转的页数");
            }
            if (this.pageParams.pageNum == pageNum) {
                return false;
            } else {
                this.grid.turnPage(pageNum);
            }
            return false;
        }, this));

        $pageInput.mouseover(
            function () {
                $pageInput.select();
            }).keydown(function (e) {
                if (e.keyCode == 13) {
                    $turnPageBtn.click();
                }
            });


        this.$prevPage = $prevPage;
        this.$nextPage = $nextPage;
        this.$turnPageBtn = $turnPageBtn;
    }

    PageBar.prototype.render = function () {
        this.grid.$pagebar.empty();
        if (!$.isEmptyObject(this.toolbars)) {
            this.renderToolBar();
        }
        if (this.needPageBar) {
            this.renderPagination();
        }
    }

    /**
     * 复选框管理器.
     * @param opt
     * @param grid
     */
    function CheckBoxManager(grid) {
        this.grid = grid;
        var opt = grid.opt;
        if (opt.events && opt.events.onCheckBoxClick) {
            this.onCheckBoxClick = opt.events.onCheckBoxClick;
        }
        this.selectData = {};
        this.keyField = opt.keyColumn;
    }

    CheckBoxManager.prototype.clearCacheData = function () {
        this.selectData = {};
    }

    CheckBoxManager.prototype.getValues = function () {
        var selectDatas = [];
        var i = 0;
        for (var key in this.selectData) {
            var data = this.selectData[key];
            if (data != null) {
                selectDatas[i] = data;
                i++;
            }
        }
        return selectDatas;
    }

    CheckBoxManager.prototype.resetHeader = function () {
        this.$checkAllBox.removeAttr("checked");
    }

    CheckBoxManager.prototype.renderHeader = function (tr) {
        var th = $('<th width="15"></th>').appendTo(tr);
        var $checkAllBox = $('<input type="checkbox"/>').appendTo(th);
        $checkAllBox.click($.proxy(function (e) {
            var checked = $(e.target).is(':checked');
            this.grid.$tbody.find(":checkbox").each($.proxy(function (index, e) {
                var othis = $(e);
                var record = this.grid.dataProvider.getDataByIndex(othis.attr('index'));
                var keyField = getValue(this.grid.opt.keyColumn, record);
                var $tr = othis.parent().parent();
                if (checked && !othis.is(":checked")) {
                    $tr.css("background-color", "#ffffd5");
                    othis.attr("checked", true);
                    this.selectData[keyField] = record;
                    if (this.onCheckBoxClick) {
                        this.onCheckBoxClick.call(e.target, record, $tr, true);
                    }
                } else if (!checked && othis.is(":checked")) {
                    $tr.attr("style", "");
                    othis.attr("checked", false);
                    delete this.selectData[keyField];
                    if (this.onCheckBoxClick) {
                        this.onCheckBoxClick.call(e.target, record, $tr, false);
                    }
                }
            }, this));
        }, this));
        this.$checkAllBox = $checkAllBox;
    }

    CheckBoxManager.prototype.render = function (keyField, index, td) {
        var $checkbox = $('<input type="checkbox" index="' + index + '" />').appendTo(td);
        td.addClass(this.grid.opt.checkBoxCss);

        if (this.selectData[keyField]) {
            $checkbox.attr('checked', true);
        }
        var record = this.grid.dataProvider.getDataByIndex(index);

        $checkbox.click($.proxy(function (e) {
            var othis = $(e.target);
            var tr = othis.parent().parent();
            if (!othis.is(':checked')) {

            } else {

            }
            var data = this.grid.dataProvider.getDataByIndex(othis.attr('index'));
            if (othis.is(':checked')) {
                if (!this.selectData[keyField]) {
                    this.selectData[keyField] = data;
                }
            } else {
                tr.removeAttr("style");
                delete this.selectData[keyField];
            }
            if (this.onCheckBoxClick) {
                this.onCheckBoxClick.call(e.target, record, tr, othis.is('checked'));
            }
        }, this));
    }


//特殊处理类.
    function disabledButton(grid) {
        if (grid.opt.button) {
            grid.opt.button.addClass("disabled");
        }
        if (grid.opt.needPageBar) {
            grid.pagebar.$prevPage.addClass("disabled");
            grid.pagebar.$nextPage.addClass("disabled");
            grid.pagebar.$turnPageBtn.addClass("disabled");
        }
    }

    function removeDisabledButton(grid) {
        if (grid.opt.button) {
            grid.opt.button.removeClass("disabled");
        }
        if (grid.opt.needPageBar) {
            grid.pagebar.$prevPage.removeClass("disabled");
            grid.pagebar.$nextPage.removeClass("disabled");
            grid.pagebar.$turnPageBtn.removeClass("disabled");
        }
    }

    function getValue(column, record) {

        /*计算keyField的值*/
        var property = column.split(".");
        var value = record;
        for (var i = 0; i < property.length; i++) {
            value = value[property[i]];
        }
        return value;
    }


    $.egrid = function (opt) {
        if (opt && opt.container) {
            var table = $("#" + opt.container + "table");
            if (table.length > 0) {
                var pageBar = table.next(".cmp_egrid_toolbar");
                table.remove();
                pageBar.remove();
            }
        }
        var grid = new EGrid(opt);
        return grid.init();
    }

    function Method() {
        //just empty;
    }

})(jQuery);