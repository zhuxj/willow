/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-22
 */
package com.willow.platform.module.basic.sysuser.session;

import com.willow.platform.module.basic.sysuser.domain.SysUser;
import com.willow.platform.utils.CodeGenerator;

import java.io.Serializable;

/**
 * <pre>
 *  用户上下文对象，当用户登录系统后，生成UserSessionContext并放到本地缓存和全局缓存中
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class UserSessionContext implements Serializable {
    private long serialVersionUID = 1L;
    private SysUser sysUser;
    //登录时间yyyymmddhhmiss
    private String logonTime;

    //登录IP
    private String logonIp;

    //上次请求时间
    private long lastRequestTime = System.currentTimeMillis();

    //会话令牌
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(long lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

    /**
     * 获取会话令牌
     *
     * @param sessionId
     * @param logonIp
     * @return
     */
    public static String getSessionToken(String sessionId, String logonIp) {
        return CodeGenerator.getSHADigest(sessionId + logonIp);
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public String getLogonTime() {
        return logonTime;
    }

    public void setLogonTime(String logonTime) {
        this.logonTime = logonTime;
    }

    public String getLogonIp() {
        return logonIp;
    }

    public void setLogonIp(String logonIp) {
        this.logonIp = logonIp;
    }
}
