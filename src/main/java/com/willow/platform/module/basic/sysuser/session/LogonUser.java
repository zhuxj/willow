/**
 *  * Book软件公司, 版权所有 违者必究
 * Copyright 2009 
 * 2009-11-7
 */
package com.willow.platform.module.basic.sysuser.session;

import com.willow.platform.utils.LocalStringUtils;

/**
 * 登录用户
 *
 * @author 朱贤俊
 * @version 1.0
 */
public class LogonUser {
    private String userName;
    private String password;
    private String ip;
    private String sessionId;
    /**
     * 是否是ERP用户登录
     */
    private boolean erpLogon = false;

    /**
     * 是否强制登录
     */
    private String enforce;

    public LogonUser() {

    }

    /**
     * 登录用户构造函数
     *
     * @param userName
     * @param password
     * @param ip
     * @param enforce
     */
    public LogonUser(String userName, String password, String ip, String enforce) {
        this.userName = userName;
        this.password = password;
        this.ip = ip;
        this.enforce = enforce;
    }

    public LogonUser(String userName, String password, String ip, String sessionId, boolean erpLogon) {
        this.userName = userName;
        this.password = password;
        this.ip = ip;
        this.sessionId = sessionId;
        this.erpLogon = erpLogon;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getIp() {
        return ip;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isErpLogon() {
        return erpLogon;
    }

    public void setErpLogon(boolean erpLogon) {
        this.erpLogon = erpLogon;
    }

    public String getEnforce() {
        return enforce;
    }

    public void setEnforce(String enforce) {
        this.enforce = enforce;
    }

    public String getSessionId() {
        return sessionId;
    }

    /**
     * 是否强制登录
     *
     * @return true 是 false 否
     */
    public boolean isForceLogon() {
        return LocalStringUtils.isNotEmpty(this.getEnforce());
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
