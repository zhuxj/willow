/**
 * 2009-5-25
 * Copyright 2009 
 * 软件公司, 版权所有 违者必究
 */
package com.willow.platform.utils;

import com.willow.platform.core.WillowException;
import com.willow.platform.module.basic.sysuser.session.UserSessionContext;
import com.willow.platform.module.basic.sysuser.session.UserSessionContextHolder;
import com.willow.platform.module.basic.sysuser.session.UserSessionCookieName;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;


/**
 * 使用必看：使用该工具的时候要先确保response对象中的方法
 * response.setCharacterEncoding("GBK");
 * response.setContentType("text/html;charset=UTF-8");
 * 中设置的编码格式是否与该工具类中的编码格式是否一致
 * 如果不一致，则会导致设置的cookies成为会话cookies，而不会成为永久cookies
 *
 * @author 朱贤俊
 * @version 1.0
 */
public class CookieUtil {

    public static final String LAST_SELECT_PROVINCE_ID = "lastSelectProvinceId";


    /**
     * 根据cookies名称获取值
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookieList[] = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        try {
            for (Cookie cookie : cookieList) {
                if (cookie.getName().equalsIgnoreCase(cookieName)) {
                    return URLDecoder.decode(cookie.getValue(), "UTF-8");
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 设置cookies值
     *
     * @param response
     * @param cookieName
     * @param cookieValue
     */
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            }
            Cookie theCookie = new Cookie(java.net.URLEncoder.encode(cookieName,
                    "UTF-8"), java.net.URLEncoder.encode(cookieValue, "UTF-8"));
//            String domain = SysConfig.WEBSITE.getDomain();
//            if (!"localhost".equals(domain) && StringUtils.isNotBlank(domain)) {
//                theCookie.setDomain(domain);
//            }
            theCookie.setPath("/");
            theCookie.setMaxAge(3600 * 24 * 14);
            response.addCookie(theCookie);
        } catch (UnsupportedEncodingException e) {
            throw new WillowException(e);
        }
    }


    /**
     * 设置cookies值,不设定过期时间，浏览器一关闭过期
     *
     * @param response
     * @param cookieName
     * @param cookieValue
     */
    public static void setCookieWithoutAge(HttpServletResponse response, String cookieName, String cookieValue) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            }
            Cookie theCookie = new Cookie(java.net.URLEncoder.encode(cookieName,
                    "UTF-8"), java.net.URLEncoder.encode(cookieValue, "UTF-8"));
//            String domain = SysConfig.WEBSITE.getDomain();
//            if (!"localhost".equals(domain) && StringUtils.isNotBlank(domain)) {
//                theCookie.setDomain(domain);
//            }
            theCookie.setPath("/");
            //theCookie.setMaxAge(3600 * 24 * 14);
            response.addCookie(theCookie);
        } catch (UnsupportedEncodingException e) {
            throw new WillowException(e);
        }
    }


    /**
     * 设置cookies值
     *
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     */
    public static void setCookie(HttpServletResponse response,
                                 String cookieName, String cookieValue, int cookieMaxage) {
        try {
            Cookie theCookie = new Cookie(java.net.URLEncoder.encode(cookieName,
                    "UTF-8"), java.net.URLEncoder.encode(cookieValue, "UTF-8"));
            theCookie.setPath("/");
//            String domain = SysConfig.WEBSITE.getDomain();
//            if (!"localhost".equals(domain) && StringUtils.isNotBlank(domain)) {
//                theCookie.setDomain(domain);
//            }
            theCookie.setMaxAge(cookieMaxage);
            response.addCookie(theCookie);
        } catch (UnsupportedEncodingException e) {
            throw new WillowException(e);
        }
    }

    /**
     * 这个方法有点问题，domain属性设置不成功，在客户端取不到值
     *
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param domain
     */
    public static void setCookie(HttpServletResponse response,
                                 String cookieName, String cookieValue, int cookieMaxage,
                                 String domain) {
        try {
            Cookie theCookie = new Cookie(java.net.URLEncoder.encode(cookieName,
                    "UTF-8"), java.net.URLEncoder.encode(cookieValue, "UTF-8"));
            theCookie.setDomain(domain);
            theCookie.setPath("/");
            theCookie.setMaxAge(cookieMaxage);
            response.addCookie(theCookie);
        } catch (UnsupportedEncodingException e) {
            throw new WillowException(e);
        }
    }

    /**
     * 删除Cookie
     *
     * @param response
     * @param cookieName
     * @throws java.io.UnsupportedEncodingException
     *
     */
    public static void deleteCookie(HttpServletResponse response, String cookieName) {
        try {
            Cookie theCookie = new Cookie(java.net.URLEncoder.encode(cookieName,
                    "UTF-8"), null);
            theCookie.setMaxAge(0);
            theCookie.setPath("/");
            response.addCookie(theCookie);
        } catch (Exception e) {
            throw new WillowException(e);
        }

    }

    /**
     * 将Map<String,String>对象转换成cookie
     *
     * @param response
     * @param cookieMap
     * @param cookieMaxage
     * @throws java.io.UnsupportedEncodingException
     *
     */
    public static void setCookieByMap(HttpServletResponse response, Map<String, String> cookieMap, int cookieMaxage) {
        try {
            if (cookieMap == null) {
                return;
            }
            for (String cookieName : cookieMap.keySet()) {
                Cookie theCookie = new Cookie(java.net.URLEncoder.encode(cookieName, "UTF-8"),
                        java.net.URLEncoder.encode(cookieMap.get(cookieName), "UTF-8"));
                theCookie.setMaxAge(cookieMaxage);
                theCookie.setPath("/");
                response.addCookie(theCookie);
            }
        } catch (UnsupportedEncodingException e) {
            throw new WillowException(e);
        }
    }


    /**
     * 将用户的上下文信息保存在cookies中
     *
     * @param response
     */
    public static void setUserInfoCookies(HttpServletResponse response) {
        UserSessionContext userSessionContext = UserSessionContextHolder.getUserSessionContext();
        /*解决iframe在ie6中的cookies读写问题*/
        setCookie(response, UserSessionCookieName.USER_NAME, userSessionContext.getSysUser().getUserName());
        setCookieWithoutAge(response, UserSessionCookieName.SESSION_ID, userSessionContext.getSysUser().getSessionId());
        setCookie(response, UserSessionCookieName.DISPLAY_USER_NAME, userSessionContext.getSysUser().getRealName());
    }

    public static String getLastSelectProvince(HttpServletRequest request) {
        return getCookieValue(request, LAST_SELECT_PROVINCE_ID);
    }

}

