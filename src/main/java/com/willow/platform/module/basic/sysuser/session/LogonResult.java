/**
 *  * Book软件公司, 版权所有 违者必究
 * Copyright 2009 
 * 2009-11-7
 */
package com.willow.platform.module.basic.sysuser.session;

/**
 * @author 朱贤俊
 * @version 1.0
 */
public class LogonResult {
    /**
     * 登录结果代码
     */
    private int resultCode = -1;

    /**
     * 构造登录结果对象
     *
     * @param resultCode
     */
    private LogonResult(int resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * 获取登录的结果代码
     *
     * @return
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * 是否登录正确
     *
     * @return
     */
    public boolean isSuccessful() {
        return this.resultCode == LogonResultType.SUCCESS;
    }

    /**
     * 是否图片校验码验证出错
     *
     * @return
     */
    public boolean isImgCheckCodeError() {
        return this.resultCode == LogonResultType.IMG_CHECK_CODE_ERROR;
    }

    /**
     * 是否用户名或密码错误
     *
     * @return
     */
    public boolean isUserOrPasswordError() {
        return this.resultCode == LogonResultType.USER_OR_PASSWORD_ERROR;
    }


    /**
     * 是否是错误的用户
     *
     * @return
     */
    public boolean isErrorUser() {
        return this.resultCode == LogonResultType.ERROR_USER;
    }


    /**
     * 正确登录
     */
    public static final LogonResult SUCCESS = new LogonResult(LogonResultType.SUCCESS);
    /**
     * 错误的校验码
     */
    public static final LogonResult IMG_CHECK_CODE_ERROR = new LogonResult(LogonResultType.IMG_CHECK_CODE_ERROR);
    /**
     * 用户或密码错误
     */
    public static final LogonResult USER_OR_PASSWORD_ERROR = new LogonResult(LogonResultType.USER_OR_PASSWORD_ERROR);

    /**
     * 非法用户
     */
    public static final LogonResult ERROR_USER = new LogonResult(LogonResultType.ERROR_USER);

    /**
     * 登录结果类型
     */
    static class LogonResultType {

        //正确登录
        public static final int SUCCESS = 1;

        //错误的校验码
        public static final int IMG_CHECK_CODE_ERROR = 2;

        //用户或密码错误
        public static final int USER_OR_PASSWORD_ERROR = 3;

        public static final int ERROR_USER = 9;

        //端对端用户，不能从网站登陆
        public static final int NO_WEB_LOGON = 20;
    }
}
