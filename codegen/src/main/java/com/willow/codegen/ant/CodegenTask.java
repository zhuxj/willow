/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-16
 */
package com.willow.codegen.ant;

import com.willow.codegen.CodegenManager;
import org.apache.tools.ant.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 *  代码生成器ant任务
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class CodegenTask {
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(CodegenTask.class);
    /**
     * 代码生成器配置文件，相对于工程目录
     */
    private String codeGenConfig;
    /**
     * ant工程对象
     */
    private Project project;

    /**
     * ant任务的执行方法
     */
    public void execute() {
        String baseDir = project.getBaseDir().getPath();
        logger.info(baseDir);
        CodegenManager codegenManager = new CodegenManager();
        String codegenConfigPath = baseDir + codeGenConfig;
        try {
            codegenManager.generateFromAnt(baseDir, codegenConfigPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCodeGenConfig(String codeGenConfig) {
        this.codeGenConfig = codeGenConfig;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
