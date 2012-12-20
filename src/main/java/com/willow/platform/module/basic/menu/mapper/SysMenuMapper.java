/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-12
 */
package com.willow.platform.module.basic.menu.mapper;

import com.willow.platform.core.base.mapper.BaseMapper;
import com.willow.platform.module.basic.menu.domain.SysMenu;

import java.util.List;

/**
 * <pre>
 * 系统菜单映射类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    public List<SysMenu> querySysMenusBuParentMenuId(String parentMenuId);
}
