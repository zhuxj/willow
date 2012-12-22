/**
 * 版权声明：贤俊工作室 版权所有 违者必究
 * 日    期：2012-12-16
 */
package com.willow.platform.module.basic.sysuser.service;

import com.willow.platform.core.base.dao.BaseDao;
import com.willow.platform.core.base.service.BaseService;
import com.willow.platform.module.basic.sysuser.dao.SysUserDao;
import com.willow.platform.module.basic.sysuser.domain.SysUser;
import com.willow.platform.module.basic.sysuser.session.*;
import com.willow.platform.utils.CodeGenerator;
import com.willow.platform.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <pre>
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


    /**
     * 退出登陆
     *
     * @param sessionId
     */
    public void logout(String sessionId) {
        //更新用户登录日志中会话的退出时间
        UserSessionContext userSessionContext = UserSessionContextManager.createInstance().getUserSessionContext(sessionId);
        if (userSessionContext == null) {
            return;
        }
        SysUser user = userSessionContext.getSysUser();
        if (user != null) {
            //清除用户缓存
            UserSessionContextManager.createInstance().removeUserSessionContext(user.getSessionId());
        }
    }


    /**
     * 管理员用户登陆
     *
     * @param logonUser
     * @return
     */
    public LogonResult logon(LogonUser logonUser) {
        Assert.notNull(logonUser, "登录用户不能为空");
        Assert.notNull(logonUser.getUserName(), "用户名不能为空");
        Assert.notNull(logonUser.getPassword(), "密码不能为空");
        Assert.notNull(logonUser.getIp(), "IP不能为空");

        SysUser filter = new SysUser();
        filter.setUserName(logonUser.getUserName());
        filter.setPassword(logonUser.getPassword());
        SysUser sysUser = sysUserDao.selectByCondition(filter);
        if (sysUser == null) {
            return LogonResult.USER_OR_PASSWORD_ERROR;
        }
        buildUserSession(logonUser, sysUser);

        return LogonResult.SUCCESS;
    }

    /**
     * 为用户创建会话
     *
     * @param logonUser
     * @param user
     */
    public void buildUserSession(LogonUser logonUser, SysUser user) {
        UserSessionContext userSessionContext = new UserSessionContext();

        //生成一个sessionId
        String sessionId = CodeGenerator.getUUID();
        String token = UserSessionContext.getSessionToken(sessionId, logonUser.getIp());

        userSessionContext.setLastRequestTime(System.currentTimeMillis());
        userSessionContext.setLogonIp(logonUser.getIp());
        userSessionContext.setLogonTime(DateUtils.getCurrTimeStrOfStoreFormat());
        userSessionContext.setToken(token);

        user.setLastLogonIp(userSessionContext.getLogonIp());
        user.setSessionId(sessionId);

        updateByIdSelective(user);

        userSessionContext.setSysUser(user);
        //将登录后的用户信息放到二级缓存里面去
        UserSessionContextManager.createInstance().putIntoCache(userSessionContext);

        //将用户会话绑定到线程中
        UserSessionContextHolder.mountUserSessionContext(userSessionContext);
    }


    @Override
    public BaseDao<SysUser> getDao() {
        return sysUserDao;
    }

    public void setSysUserDao(SysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }

}
