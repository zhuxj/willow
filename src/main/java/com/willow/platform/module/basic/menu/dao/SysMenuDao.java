/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-12
 */
package com.willow.platform.module.basic.menu.dao;

import com.willow.platform.core.base.dao.BaseDao;
import com.willow.platform.module.basic.menu.domain.SysMenu;
import com.willow.platform.module.basic.menu.mapper.SysMenuMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * 系统菜单持久层
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
@Repository
public class SysMenuDao extends BaseDao<SysMenu> {
    public List<SysMenu> querySysMenusBuParentMenuId(String parentMenuId) {
        SysMenuMapper sysMenuMapper = getMapper(SysMenuMapper.class);
        return sysMenuMapper.querySysMenusBuParentMenuId(parentMenuId);
    }

    @Override
    public Class getMapperClass() {
        return SysMenuMapper.class;
    }
}
