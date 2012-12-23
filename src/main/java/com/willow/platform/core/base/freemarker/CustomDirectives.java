/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-2-15
 */
package com.willow.platform.core.base.freemarker;

import com.willow.platform.utils.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  freemarker自定义指令
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
@Component
public class CustomDirectives implements ApplicationContextAware {
    private HttpServletRequest request;

    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(CustomDirectives.class);
    @Autowired
    private FtlParser ftlParser;
    private Map<String, ModuleContentParser> moduleParserMap = new HashMap<String, ModuleContentParser>();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 是否为英文
     *
     * @return
     */
    public Boolean getIsEnglish() {
        String clientLanguage = CookieUtil.getCookieValue(request, ClientLanguage.CLIENT_LANGUAGE);
        if (ClientLanguage.EN_US.equals(clientLanguage)) {
            return true;
        } else {
            return false;
        }
    }


    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
