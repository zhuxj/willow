/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-22
 */
package com.willow.platform.module.basic.sysuser.session;

import com.willow.platform.utils.EasyApplicationContextUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
@Component
public class UserSessionContextManager {
    @Autowired
    private CacheManager ehCacheManager;

    public static final String SESSION_CACHE = "sessionCache";

    public static UserSessionContextManager createInstance() {
        try {
            UserSessionContextManager userSessionContextManager = EasyApplicationContextUtils.getBeanByType(UserSessionContextManager.class);
            if (userSessionContextManager != null) {
                return userSessionContextManager;
            }
        } catch (Exception e) {
            Logger logger = Logger.getLogger(UserSessionContextManager.class);
            logger.error("未初始化容器，userSessionContextManager为null");
        }
        return null;
    }

    /**
     * 获取用户上下文。
     *
     * @param sessionId
     * @return
     */
    public UserSessionContext getUserSessionContext(String sessionId) {
        String key = sessionId;
        Cache ehCache = this.ehCacheManager.getCache(SESSION_CACHE);
        Element element = ehCache.get(key);
        if (element != null && !element.isExpired()) {
            return (UserSessionContext) element.getValue();
        }
        return null;
    }

    /**
     * 将用户上下文放入缓存中
     *
     * @param userSessionContext
     */
    public void putIntoCache(UserSessionContext userSessionContext) {
        Cache ehCache = this.ehCacheManager.getCache(SESSION_CACHE);
        String key = userSessionContext.getSysUser().getSessionId();
        Element element = new Element(key, userSessionContext, false,
                3600, 3600);
        ehCache.put(element);
    }

    /**
     * 从缓存中移除用户上下文
     *
     * @param sessionId
     */
    public void removeUserSessionContext(String sessionId) {
        String key = sessionId;
        Cache ehCache = this.ehCacheManager.getCache(SESSION_CACHE);
        ehCache.remove(key);
    }

}
