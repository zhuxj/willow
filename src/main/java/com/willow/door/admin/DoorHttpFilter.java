/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-22
 */
package com.willow.door.admin;

import com.willow.platform.module.basic.sysuser.session.UserSessionContext;
import com.willow.platform.module.basic.sysuser.session.UserSessionContextHolder;
import com.willow.platform.module.basic.sysuser.session.UserSessionContextManager;
import com.willow.platform.module.basic.sysuser.session.UserSessionCookieName;
import com.willow.platform.utils.CodeGenerator;
import com.willow.platform.utils.CookieUtil;
import com.willow.platform.utils.LocalStringUtils;
import com.willow.platform.utils.web.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class DoorHttpFilter implements Filter {
    private static final String FILTER_APPLIED = "__session_context_filter_applied";
    private static final String[] ADMIN_NEED_LOGON_URLS = new String[]{"/admin"};
    private static final String[] ADMIN_INHERENT_ESCAPE_URIS = {"/onLogon", "/logon"};


    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(DoorHttpFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //获取用户连接信息，一遍登录后跳转到该页面
        String uri = httpRequest.getRequestURI();
        if (request.getAttribute(FILTER_APPLIED) != null || isEscapeFilter(uri)) {
            chain.doFilter(request, response);
        } else {
            try {
                //设置过滤标识，防止一次请求多次过滤
                request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                boolean needLogon = isNeedLogon(uri);
                if (isURIEscape(uri)) {
                    needLogon = false;
                }

                //处理后台管理员登录
                String sessionId = null;
                //对服务总线登录的用户会话，根据请求参数获取用户上下文信息，同时要将用户信息写到Cookie中,以便下次
                //可以按正常的方式获取用户上下文（Cookie）
                sessionId = (String) request.getParameter(UserSessionCookieName.SESSION_ID);

                if (request.getParameter(UserSessionCookieName.FORCE_NOT_USE_COOKIE) == null && LocalStringUtils.isEmpty(sessionId)) {
                    sessionId = CookieUtil.getCookieValue(httpRequest, UserSessionCookieName.SESSION_ID);
                }
                UserSessionContext userSessionContext = null;
                if (LocalStringUtils.isNotEmpty(sessionId)) {
                    userSessionContext = UserSessionContextManager.createInstance().getUserSessionContext(sessionId);
                }
                String ip = RequestUtils.getIpAddress(httpRequest);
                if (isUserLogoned(sessionId, userSessionContext)) {//用户已经登录
                    if (logger.isDebugEnabled()) {
                        logger.debug("将用户会话绑定到线程中");
                    }
                    UserSessionContextHolder.mountUserSessionContext(userSessionContext);
                    CookieUtil.setUserInfoCookies((HttpServletResponse) response);
                } else {//用户没有登录
                    //如果该地址是不需要登录的，即使用户没有登录，也能请求到
                    if (!needLogon) {
                        chain.doFilter(request, response);
                        return;
                    }

                    writeNotLogonCookie(response);
                    if (needLogon) {
                        if (RequestUtils.isAjax(httpRequest)) {
                            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                            String uuid = CodeGenerator.getUUID();
                            String jsonStr = "{'type':'admin','noLogon':'NOLOGON','orignUrl':'" + uri + "','uuid':'" + uuid + "'}";
                            response.getWriter().print(jsonStr);
                        } else {
                            forwardToLogonPage(request, response);
                        }
                        return;
                    }
                }
                chain.doFilter(request, response);
            } finally {
                UserSessionContextHolder.unmountUserSessionContext();
            }

        }

    }


    /**
     * 转向到后台登录的页面
     *
     * @param request
     * @param response
     */
    private void forwardToLogonPage(ServletRequest request, ServletResponse response) throws
            IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String logonPageUrl = httpRequest.getSession().getServletContext().getContextPath() + "/admin/logon";
        request.getRequestDispatcher(logonPageUrl).forward(request, response);
        return;
    }


    private void writeNotLogonCookie(ServletResponse response) {
        CookieUtil.setCookie((HttpServletResponse) response, UserSessionCookieName.LOGON_STATUS, "0");
    }


    /**
     * 判断用户是否已经登录
     *
     * @param sessionId          会话ID
     * @param userSessionContext
     * @return
     */
    private boolean isUserLogoned(String sessionId, UserSessionContext userSessionContext) {
        //暂时不考虑IP相同的问题，因为集群环境下可能无法获取客户端的IP，以后再考虑。
        return LocalStringUtils.isNotEmpty(sessionId) && userSessionContext != null;
    }


    /**
     * 判断当前的URL是不需要登录再访问
     *
     * @param currURI
     * @return
     */
    private boolean isNeedLogon(String currURI) {
        String[] needs = ADMIN_NEED_LOGON_URLS;

        for (String needLogonUrl : needs) {
            if (currURI.indexOf(needLogonUrl) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 访问的URI是否要不需要登录就可访问
     *
     * @param requestURI
     * @return
     */
    private boolean isURIEscape(String requestURI) {

        String[] unNeeds = ADMIN_INHERENT_ESCAPE_URIS;

        for (String uri : unNeeds) {
            //修改部署上下文不能为“/”的问题
            if (requestURI != null && requestURI.indexOf(uri) >= 0) {
                return true;
            }
        }
        return false;
    }


    private static final Set STATIC_RESOURCE_URIS = new HashSet();

    static {
        STATIC_RESOURCE_URIS.add(".JPG");
        STATIC_RESOURCE_URIS.add(".JPEG");
        STATIC_RESOURCE_URIS.add(".BMP");
        STATIC_RESOURCE_URIS.add(".GIF");
        STATIC_RESOURCE_URIS.add(".PNG");
        STATIC_RESOURCE_URIS.add(".BMP");

        STATIC_RESOURCE_URIS.add(".ZIP");
        STATIC_RESOURCE_URIS.add(".RAR");

        STATIC_RESOURCE_URIS.add(".JS");
        STATIC_RESOURCE_URIS.add(".CSS");

        STATIC_RESOURCE_URIS.add(".HTM");
    }

    private boolean isEscapeFilter(String uri) {
        int index = uri.lastIndexOf(".");
        if (index < 0 || (index > 0 && index == uri.length() - 1)) {
            return false;
        } else {
            String subfix = uri.substring(index);
            return STATIC_RESOURCE_URIS.contains(subfix.toUpperCase());
        }
    }

    @Override
    public void destroy() {

    }
}
