/**
 * 版权声明：软件公司 版权所有 违者必究 2011
 * 日    期：11-5-24
 */
package com.willow.platform.utils.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 陈文炎
 * @version 1.0
 *          功能说明：
 */
public class RequestUtils {
    /**
     * 获取客户端的IP信息.
     *
     * @param request HttpServletRequest对象.
     * @return
     */
    public static final String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取服务器域名信息
     *
     * @param request
     * @return
     */

    public static final String getServerName(HttpServletRequest request) {
        String serverName = request.getHeader("x-forwarded-host");
        if (serverName == null || serverName.length() == 0 || "unknown".equalsIgnoreCase(serverName)) {
            serverName = request.getServerName();
        }

        return serverName;
    }

    /**
     * 判断是否为Ajax请求.
     *
     * @param request HttpServletRequest对象.
     * @return
     */
    public static final boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

}
