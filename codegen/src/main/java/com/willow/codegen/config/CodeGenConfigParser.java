/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.codegen.config;

import com.willow.codegen.CodegenException;
import com.willow.codegen.model.codegenconfig.CodeGenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
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
     * @param configPath 代码生成器配置文件，类路径
     * @return
     */
    public CodeGenConfig parserCodeGenConfigFromClassPath(String configPath) {
        Yaml yaml = new Yaml();
        InputStream input = null;
        try {
            input = new ClassPathResource(configPath).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CodegenException("can not load codegen config", e.getCause());
        }
        CodeGenConfig config = (CodeGenConfig) yaml.load(input);
        logger.info("读取" + configPath + "成功");
        return config;
    }


    /**
     * 解析代码生成器配置文件
     *
     * @param fileConfigPath 系统文件路径
     * @return
     */
    public CodeGenConfig parserCodeGenConfigFromFileSystemPath(String fileConfigPath) {
        Yaml yaml = new Yaml();
        InputStream input = null;
        try {
            input = new FileSystemResource(fileConfigPath).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CodegenException("can not load codegen config", e.getCause());
        }
        CodeGenConfig config = (CodeGenConfig) yaml.load(input);
        logger.info("读取" + fileConfigPath + "成功");
        return config;
    }

}
