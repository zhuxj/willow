/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-16
 */
package com.willow.platform.codegen.ant;

import com.willow.platform.codegen.CodegenManager;
import org.apache.tools.ant.Project;

/**
 * <pre>
 *  代码生成器ant任务
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class CodegenTask {
    private String codeGenConfig;
    private Project project;

    public void execute() {
        String baseDir = project.getBaseDir().getPath();
        System.out.println(baseDir);
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
