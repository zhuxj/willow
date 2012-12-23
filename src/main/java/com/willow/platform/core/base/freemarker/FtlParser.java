/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-2-15
 */
package com.willow.platform.core.base.freemarker;

import com.willow.platform.core.WillowException;
import com.willow.platform.core.context.WebSiteContext;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
@Component
public class FtlParser {
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(FtlParser.class);
    /**
     * freemarker模板配置
     */
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    private CustomDirectives customDirectives;


    /**
     * 根据FreeMarker模板格式化数据模型
     *
     * @param ftlBaseName 基于classpath:ftl/下的模板基名，如你的模板位于
     *                    ftl/mail/aaa.ftl,则对应的ftpBaseName为mail/aaa
     * @param map
     * @return
     */
    public String formatContent(String ftlBaseName, Map map) {
        ftlBaseName += ".ftl";
        try {
            freeMarkerConfigurer.getConfiguration().setSharedVariable("resourceRoot", WebSiteContext.ctx().getResourceRoot());
            HttpServletRequest request = (HttpServletRequest) map.get("request");
            customDirectives.setRequest(request);
            freeMarkerConfigurer.getConfiguration().setSharedVariable("customDirectives", customDirectives);

            Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(ftlBaseName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
        } catch (IOException e) {
            logger.error("在加载" + ftlBaseName + "模板时，发生IO异常..");
            throw new WillowException(e);
        } catch (TemplateException e) {
            logger.error("在解析" + ftlBaseName + "模板时，发生解析异常..");
            throw new WillowException(e);
        }
    }
}
