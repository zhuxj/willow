/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-22
 */
package com.willow.door.admin;

import com.willow.platform.core.base.web.BaseController;
import com.willow.platform.core.context.WebSiteContext;
import com.willow.platform.module.basic.sysuser.domain.SysUser;
import com.willow.platform.module.basic.sysuser.service.SysUserService;
import com.willow.platform.module.basic.sysuser.session.*;
import com.willow.platform.utils.CodeGenerator;
import com.willow.platform.utils.CookieUtil;
import com.willow.platform.utils.LocalStringUtils;
import com.willow.platform.utils.web.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
@RequestMapping(WebSiteContext.MANAGER + "admin")
@Controller
public class LogonController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("logon")
    public String logon(ModelMap modelMap) {
        //随机生成一个LogonId
        String logonId = CodeGenerator.getUUID();
        modelMap.put("logonId", logonId);
        return "/door/admin/logon";
    }

    @RequestMapping("onLogon")
    public Map<String, Object> onLogon(String userName, String pwd, String logonId, String verifyCode, String enforce, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> logonResultJsonMap = new HashMap<String, Object>();
        String logonIP = RequestUtils.getIpAddress(request);
        LogonResult logonResult = LogonResult.SUCCESS;
        LogonUser logonUser = new LogonUser(userName.trim(), pwd.trim(), logonIP,
                LocalStringUtils.getNullBlankStr(enforce));
        logonResult = sysUserService.logon(logonUser);
        //对登录结果进行处理
        if (logonResult.isSuccessful()) {//登录成功
            CookieUtil.setUserInfoCookies(response);
        }
        logonResultJsonMap.put("resultCode", logonResult.getResultCode());
        UserSessionContext context = UserSessionContextHolder.getUserSessionContext();
        if (context != null) {
            SysUser sysUser = context.getSysUser();
            logonResultJsonMap.put(UserSessionCookieName.SESSION_ID, sysUser.getSessionId());
        }
        return logonResultJsonMap;
    }

    /**
     * 用户注销方法(退出登录)
     *
     * @param response
     * @param sessionId
     */
    @RequestMapping("/logout")
    public void logout(HttpServletResponse response, @CookieValue(value = UserSessionCookieName.SESSION_ID, required = false) String sessionId) {
        UserSessionContext context = UserSessionContextHolder.getUserSessionContext();
        if (context != null) {
            if (StringUtils.isEmpty(sessionId)) {
                sessionId = context.getSysUser().getSessionId();
            }
        }

        if (LocalStringUtils.isNotEmpty(sessionId)) {
            sysUserService.logout(sessionId);
        }
        CookieUtil.setCookie(response, UserSessionCookieName.LOGON_STATUS, "0");
    }


}
