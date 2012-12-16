/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.platform.codegen;

import com.willow.platform.codegen.model.TableClass;
import com.willow.platform.codegen.model.codegenconfig.CodeGenConfig;
import com.willow.platform.codegen.model.codegenconfig.FtlConfig;
import com.willow.platform.codegen.model.codegenconfig.TemplateConfig;
import com.willow.platform.core.WillowException;
import com.willow.platform.core.context.WebSiteContext;
import com.willow.platform.utils.DateUtils;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <pre>
 *  模板解析
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class CodeGenTemplateParser {
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(CodeGenTemplateParser.class);

    /**
     * 解析模板内容
     *
     * @param codeGenConfig
     * @param tableClass
     * @return
     */
    public Map<String, String> parserFtlContent(CodeGenConfig codeGenConfig, TableClass tableClass) {
        Map<String, String> map = new HashMap<String, String>();
        FtlConfig ftlConfig = codeGenConfig.getFtlConfig();
        String basePath = ftlConfig.getBasePath();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("codeGenConfig", codeGenConfig);
        dataMap.put("tableClass", tableClass);
        for (TemplateConfig tc : ftlConfig.getTemplateConfigs()) {
            String ftlBaseName = basePath + tc.getName();
            String ftlContent = formatContent(ftlBaseName, dataMap);
            map.put(tc.getId(), ftlContent);
            logger.info("解析" + ftlBaseName + "模板成功");
        }
        return map;
    }

    /**
     * 根据FreeMarker模板格式化数据模型
     *
     * @param ftlBaseName 基于classpath:ftl/下的模板基名，如你的模板位于
     *                    ftl/mail/aaa.ftl,则对应的ftpBaseName为mail/aaa
     * @param map
     * @return
     */
    private String formatContent(String ftlBaseName, Map map) {
        Configuration configuration = buildConfiguration();
        String date = DateUtils.getCurrDateStr(DateUtils.HYPHEN_DISPLAY_DATE);
        map.put("date", date);
        try {
            Template tpl = configuration.getTemplate(ftlBaseName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
        } catch (IOException e) {
            logger.error("在加载" + ftlBaseName + "模板时，发生IO异常..");
            throw new WillowException(e);
        } catch (TemplateException e) {
            logger.error("在解析" + ftlBaseName + "模板时，发生解析异常..");
            throw new WillowException(e);
        }
    }


    /**
     * 构建模板Configuration
     *
     * @return
     */
    public Configuration buildConfiguration() {
        Configuration cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        try {
            cfg.setDirectoryForTemplateLoading(new ClassPathResource("ftl/").getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        return cfg;
    }


    /**
     * 构建 FreeMarkerConfigurer
     *
     * @return
     */
    public FreeMarkerConfigurer buildFreeMarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:ftl/");
        freeMarkerConfigurer.setDefaultEncoding("UTF-8");
        Properties properties = new Properties();
        properties.put("template_update_delay", 5);
        properties.put("locale", "zh_CN");
        freeMarkerConfigurer.setFreemarkerSettings(properties);
        return freeMarkerConfigurer;
    }
}
