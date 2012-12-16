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
            {column:'telphone', label:'电话', width:'50px'}
        ]
    });
    $("#addUser").click(function(){
        top.jq.workgroundManager.openPage({url:"/admin/sysuser/addPage",
            onChanged:function () {
                myGrid.refresh();
            }
        });
    });

})