/**
*版权声明：${codeGenConfig.developerConfig.company!} 版权所有 违者必究
*日    期： ${date!}
*作    者： ${codeGenConfig.developerConfig.developer!}
*/
$(document).ready(function () {
    var ${codeGenConfig.table.classVariable!}Grid = $.egrid({
        container:'list${codeGenConfig.table.classVar!}Div', //放置表格的DIV
        url:'/${codeGenConfig.codeGenFileConfig.pageType}/${codeGenConfig.table.simplePackageVar!}/query', //表格数据获取的URL
        params:{}, //数据查询条件
        sorts:{
        sortFieldName:'create_time',
        sortType:'desc'
        }, //排序条件
        pageSize:10, //每页展现数据数
        autoLoad:true, //是否表格创建完自动加载数据
        showTableIndex:false, //是否显示表格行索引
        keyColumn:'objId',
        columns:[
            <#list tableClass.fieldColumns as fieldColumn>
            <#if  !fieldColumn.isIncludeField>
                <#if fieldColumn.columnType!="TEXT">
                {column:'${fieldColumn.javaProperty!}', label:'${fieldColumn.propName!}', width:'${fieldColumn.columnLength!}px'},
                </#if>
            </#if>
            </#list>
            {column:'func', label:'操作', align:'center', width:'50px', headerCls:"cmp_tanle_tdc", cellCss:"cmp_tanle_tdc", actions:[
            {label:'编辑', action:function (record, keyField, grid, colNo, rowNo, cell) {
                top.jq.workgroundManager.openPage({url:"/${codeGenConfig.codeGenFileConfig.pageType}/${codeGenConfig.table.simplePackageVar!}/updatePage?objId=" + record.objId,
                onChanged:function () {
                ${codeGenConfig.table.classVariable!}Grid.refresh();
                }
                });
            }
            },
            {label:'查看', action:function (record, keyField, grid, colNo, rowNo, cell) {
                top.jq.workgroundManager.openPage({url:"/${codeGenConfig.codeGenFileConfig.pageType}/${codeGenConfig.table.simplePackageVar!}/detailPage?objId=" + record.objId,
                onChanged:function () {
                ${codeGenConfig.table.classVariable!}Grid.refresh();
                }
                });
            }
            }
            ]}
        ],
        toolbars:[
        {label:'删除', action:function () {
            var values = ${codeGenConfig.table.classVariable!}Grid.getCheckBoxValues();
            if (values.length == 0) {
            alert("请先选择记录!");
            return false;
            }
            var objIdArr = [];
            $.each(values, function (idx, obj) {
            objIdArr.push(obj.objId);
            })
            if (confirm("确定要删除？")) {
                $.localAjax({
                    url:"/${codeGenConfig.codeGenFileConfig.pageType}/${codeGenConfig.table.simplePackageVar!}/batchDel",
                    data:{objIds:objIdArr.join(",")},
                    dataType:"json",
                    type:"post",
                    success:function (result) {
                        if (result.success == "1") {
                        ${codeGenConfig.table.classVariable!}Grid.refresh();
                    }
                }
                })
            }
          }
       }
        ]
    });
    $("#add${codeGenConfig.table.classVar!}").click(function () {
        top.jq.workgroundManager.openPage({url:"/${codeGenConfig.codeGenFileConfig.pageType}/${codeGenConfig.table.simplePackageVar!}/addPage",
        onChanged:function () {
              ${codeGenConfig.table.classVariable!}Grid.refresh();
        }
        });
    });
    $("#queryOk").click(function () {
        var formdata = $("#query${codeGenConfig.table.classVar!}Form").serializeJson();
        ${codeGenConfig.table.classVariable!}Grid.query({params:formdata});
    });

    $("#queryReset").click(function () {
        $("#query${codeGenConfig.table.classVar!}Form").reSetForm();
    });


})