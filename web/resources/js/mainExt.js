/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-12-5
 *作    者： 朱贤俊
 */
$(document).ready(function () {
    var menuTabbar = null;
    var viewport = new Ext.Viewport({
        id:'border-main',
        layout:'border',
        items:[
            {
                id:'north-pannel',
                region:'north',
                contentEl:'header',
                margins:'0 0 0 0',
                collapsible:true,
                animCollapse:false,
                collapseMode:'mini',
                titleCollapse:true,
                hideCollapseTool:true,
                split:true,
                minSize:70,
                maxSize:70
            },
            {
                region:'south',
                contentEl:'south',
                height:30,
                minSize:30,
                maxSize:30,
                split:false,
                collapsible:false,
                collapsed:false,
                margins:'0 0 0 0'
            },
            {
                id:'west-panel',
                region:'west',
                stateId:'navigation-panel',
                title:'所有菜单',
                split:true,
                width:200,
                minWidth:175,
                maxWidth:400,
                collapsible:true,
                collapseMode:'mini',
                animCollapse:true,
                margins:'0 0 0 0',
                layout:{
                    type:'accordion',
                    titleCollapse:true,
                    animate:false
                },
                autoScroll:true,
                items:[
                    {
                        contentEl:'west',
                        title:'Navigation',
                        iconCls:'nav'
                    },
                    {
                        title:'Settings',
                        html:'<p>Some settings in here.</p>',
                        iconCls:'settings'
                    },
                    {
                        title:'Information',
                        html:'<p>Some info in here.</p>',
                        iconCls:'info'
                    }
                ]
            },
            menuTabbar = new Ext.TabPanel({
                region:'center',
                plugins:new Ext.ux.TabCloseMenu({
                    closeTabText:'关闭当前页面',
                    closeOtherTabsText:'关闭其他页面',
                    closeAllTabsText:'关闭所有页面'
                }),
                deferredRender:false,
                activeTab:0,
                frame:true,
                items:[
                    {
                        title:'系统桌面',
                        closable:false,
                        html:'<iframe src="index.jsp" frameBorder="0" scrolling="auto" top="0" width="100%" height="100%"></iframe>',
                        autoScroll:true
                    },
                    {
                        contentEl:'center1',
                        title:'流程启动',
                        closable:true,
                        autoScroll:true
                    },
                    {
                        contentEl:'center1',
                        title:'待办工作',
                        closable:true,
                        autoScroll:true
                    },
                    {
                        contentEl:'center1',
                        title:'我的日常',
                        closable:true,
                        autoScroll:true
                    }
                ]
            })
        ]
    });
    Ext.getCmp("west-panel").add({
        title:'test',
        html:'<p>Some info in here.</p>',
        iconCls:'info'
    });
    Ext.getCmp("west-panel").doLayout();
})