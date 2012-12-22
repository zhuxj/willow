/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-22
 */
package com.willow.platform.module.basic.sysuser.session;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class UserSessionContextHolder {
    private static final ThreadLocal<UserSessionContext> userContextThreadLocal = new ThreadLocal<UserSessionContext>();

    /**
     * 将用户会话绑定到当前的请求线程中
     *
     * @param userSessionContext
     */
    public static void mountUserSessionContext(UserSessionContext userSessionContext) {
        userContextThreadLocal.set(userSessionContext);
    }

    /**
     * 将用户会话从当前请求线程中解除绑定
     */
    public static void unmountUserSessionContext() {
        userContextThreadLocal.set(null);
    }

    /**
     * 获取当前线程的绑定的SessionContext
     *
     * @return
     */
    public static UserSessionContext getUserSessionContext() {
        return userContextThreadLocal.get();
    }
}
