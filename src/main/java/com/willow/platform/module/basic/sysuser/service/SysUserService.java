/**
* 版权声明：贤俊工作室 版权所有 违者必究
* 日    期：2012-12-16
*/
package com.willow.platform.module.basic.sysuser.service;

import com.willow.platform.core.base.dao.BaseDao;
import com.willow.platform.core.base.service.BaseService;
import com.willow.platform.module.basic.sysuser.dao.SysUserDao;
import com.willow.platform.module.basic.sysuser.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
<pre>
 * 系统用户业务类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.0
 */
@Service
public class SysUserService extends BaseService<SysUser> {
    @Autowired
    private SysUserDao sysUserDao;
    @Override
    public BaseDao<SysUser> getDao() {
        return sysUserDao;
    }

    public void setSysUserDao(SysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }
}
