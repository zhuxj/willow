/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-12-16
 *作    者： 朱贤俊
 */
$(document).ready(function () {
    var myGrid = $.egrid({
        container:'listDiv', //放置表格的DIV
        url:'/admin/sysuser/query', //表格数据获取的URL
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
            {column:'userName', label:'用户名', width:'50px'},
            {column:'realName', label:'真实姓名', width:'50px'},
            {column:'email', label:'邮箱', width:'100px'},
            {column:'mobile', label:'手机', width:'50px'},
            {column:'telphone', label:'电话', width:'50px'},
            {column:'func', label:'操作', align:'center', width:'50px', headerCls:"cmp_tanle_tdc", cellCss:"cmp_tanle_tdc", actions:[
                {label:'编辑', action:function (record, keyField, grid, colNo, rowNo, cell) {
                    top.jq.workgroundManager.openPage({url:"/admin/sysuser/updatePage?objId=" + record.objId,
                        onChanged:function () {
                            myGrid.refresh();
                        }
                    });
                }
                },
                {label:'查看', action:function (record, keyField, grid, colNo, rowNo, cell) {
                    top.jq.workgroundManager.openPage({url:"/admin/sysuser/detailPage?objId=" + record.objId,
                        onChanged:function () {
                            myGrid.refresh();
                        }
                    });
                }
                }
            ]}
        ],
        toolbars:[
            {label:'删除', action:function () {
                var values = myGrid.getCheckBoxValues();
                if (values.length == 0) {
                    alert("请先选择记录!");
                    return false;
                }
                var objIdArr = [];
                $.each(values, function (idx, user) {
                    objIdArr.push(user.objId);
                })
                if (confirm("确定要删除？")) {
                    $.localAjax({
                        url:"/admin/sysuser/batchDel",
                        data:{objIds:objIdArr.join(",")},
                        dataType:"json",
                        type:"post",
                        success:function (result) {
                            if (result.success == "1") {
                                myGrid.refresh();
                            }
                        }
                    })
                }
            }
            }
        ]
    });
    $("#addUser").click(function () {
        top.jq.workgroundManager.openPage({url:"/admin/sysuser/addPage",
            onChanged:function () {
                myGrid.refresh();
            }
        });
    });

})