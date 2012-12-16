/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.platform.codegen.config;

import com.willow.platform.codegen.model.codegenconfig.CodeGenConfig;
import com.willow.platform.core.WillowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *  代码生成器配置解析
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class CodeGenConfigParser {
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(CodeGenConfigParser.class);

    /**
     * 解析代码生成器配置文件
     *
     * @param configPath 代码生成器配置文件，相对类路径
     * @return
     */
    public CodeGenConfig parserCodeGenConfig(String configPath) {
        Yaml yaml = new Yaml();
        InputStream input = null;
        try {
            input = new ClassPathResource(configPath).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new WillowException("can not load codegen config", e.getCause());
        }
        CodeGenConfig config = (CodeGenConfig) yaml.load(input);
        logger.info("读取" + configPath + "成功");
        return config;
    }

}
