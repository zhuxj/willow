/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen;

import com.willow.codegen.model.TableClass;
import com.willow.codegen.model.codegenconfig.CodeGenConfig;
import com.willow.codegen.model.codegenconfig.FtlConfig;
import com.willow.codegen.model.codegenconfig.TemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private Resource resource;

    public CodeGenTemplateParser(Resource resource) {
        this.resource = resource;
    }

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        map.put("date", date);
        try {
            Template tpl = configuration.getTemplate(ftlBaseName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
        } catch (IOException e) {
            logger.error("在加载" + ftlBaseName + "模板时，发生IO异常..");
            throw new CodegenException(e);
        } catch (TemplateException e) {
            logger.error("在解析" + ftlBaseName + "模板时，发生解析异常..");
            throw new CodegenException(e);
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
            cfg.setDirectoryForTemplateLoading(resource.getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        return cfg;
    }
}
