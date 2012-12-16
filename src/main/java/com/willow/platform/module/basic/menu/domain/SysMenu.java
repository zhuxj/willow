/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-12
 */
package com.willow.platform.module.basic.menu.domain;

import com.willow.platform.core.base.domian.BaseObject;

import java.util.List;

/**
 * <pre>
 * 系统菜单对象
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class SysMenu extends BaseObject {
    //菜单名称
    private String menuName;
    //菜单编码
    private String menuCode;
    //父菜单
    private String parentMenuId;
    //排序号
    private Integer orderNo;
    //菜单类型，1：门户 2：企业
    private String menuType;
    //节点类型 1：分支节点 2：叶子节点
    private String nodeType;
    //菜单链接
    private String url;
    //节点图标
    private String icon;

    //子菜单列表
    private List<SysMenu> childMenuList;

    public List<SysMenu> getChildMenuList() {
        return childMenuList;
    }

    public void setChildMenuList(List<SysMenu> childMenuList) {
        this.childMenuList = childMenuList;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
