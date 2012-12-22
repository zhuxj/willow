/**
*版权声明：贤俊工作室 版权所有 违者必究
*日    期： 2012-12-21
*作    者： 朱贤俊
*/
$(document).ready(function () {
    var productGrid = $.egrid({
        container:'listProductDiv', //放置表格的DIV
        url:'/admin/product/query', //表格数据获取的URL
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
                {column:'productSn', label:'产品编号', width:'30px'},
                {column:'productName', label:'产品中文名称', width:'50px'},
                {column:'productNameEn', label:'产品英文名称', width:'50px'},
                {column:'productNorms', label:'产品中文规格', width:'50px'},
                {column:'productNormsEn', label:'产品英文规格', width:'50px'},
                {column:'productVersion', label:'产品型号', width:'50px'},
//                {column:'catalogId', label:'产品分类', width:'36px'},
//                {column:'productImage', label:'产品图片', width:'36px'},
                {column:'browseTime', label:'浏览次数', width:'10px'},
            {column:'func', label:'操作', align:'center', width:'50px', headerCls:"cmp_tanle_tdc", cellCss:"cmp_tanle_tdc", actions:[
            {label:'编辑', action:function (record, keyField, grid, colNo, rowNo, cell) {
                top.jq.workgroundManager.openPage({url:"/admin/product/updatePage?objId=" + record.objId,
                onChanged:function () {
                productGrid.refresh();
                }
                });
            }
            },
            {label:'查看', action:function (record, keyField, grid, colNo, rowNo, cell) {
                top.jq.workgroundManager.openPage({url:"/admin/product/detailPage?objId=" + record.objId,
                onChanged:function () {
                productGrid.refresh();
                }
                });
            }
            }
            ]}
        ],
        toolbars:[
        {label:'删除', action:function () {
            var values = productGrid.getCheckBoxValues();
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
                    url:"/admin/product/batchDel",
                    data:{objIds:objIdArr.join(",")},
                    dataType:"json",
                    type:"post",
                    success:function (result) {
                        if (result.success == "1") {
                        productGrid.refresh();
                    }
                }
                })
            }
          }
       }
        ]
    });
    $("#addProduct").click(function () {
        top.jq.workgroundManager.openPage({url:"/admin/product/addPage",
        onChanged:function () {
              productGrid.refresh();
        }
        });
    });
    $("#queryOk").click(function () {
        var formdata = $("#queryProductForm").serializeJson();
        productGrid.query({params:formdata});
    });

    $("#queryReset").click(function () {
        $("#queryProductForm").reSetForm();
    });


})