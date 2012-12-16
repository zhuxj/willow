/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-3
 */
package com.willow.platform.core.sys;

import com.willow.platform.core.context.BooterAddon;
import com.willow.platform.core.context.WebSiteContext;
import com.willow.platform.utils.EasyApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  系统初始化
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
@Component
public class SysInitializer implements ApplicationContextAware, ServletContextAware {
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(SysInitializer.class);
    private static boolean isLoaded = false;

    private ApplicationContext applicationContext;

    //启动插件
    @Autowired(required = false)
    private List<BooterAddon> booterAddons;

    @Autowired
    private WebSiteContext webSiteContext;

    public void setServletContext(ServletContext servletContext) {
        servletContext.setAttribute("resourceRoot", webSiteContext.getResourceRoot());
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        EasyApplicationContextUtils.setApplicationContext(applicationContext);
    }

    @PostConstruct
    public void init() {
        if (!isLoaded) {
            logger.info("系统初始化开始");
            if (booterAddons != null) {
                for (BooterAddon booterAddon : booterAddons) {
                    booterAddon.init();
                }
            }
            logger.info("系统初始化结束");
            isLoaded = true;
        }
    }

}
