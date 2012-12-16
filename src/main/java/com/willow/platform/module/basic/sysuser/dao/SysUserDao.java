/**
* 版权声明：贤俊工作室 版权所有 违者必究
* 日    期：2012-12-16
*/
package com.willow.platform.module.basic.sysuser.dao;

import com.willow.platform.core.base.dao.BaseDao;
import com.willow.platform.module.basic.sysuser.domain.SysUser;
import com.willow.platform.module.basic.sysuser.mapper.SysUserMapper;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *   系统用户持久层
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.0
 */
@Repository
public class SysUserDao extends BaseDao<SysUser> {
    @Override
    public Class getMapperClass() {
    return SysUserMapper.class;
    }
}
