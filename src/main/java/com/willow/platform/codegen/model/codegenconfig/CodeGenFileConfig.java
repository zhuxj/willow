/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.platform.codegen.model.codegenconfig;

import java.util.List;

/**
 * <pre>
 * 生成文件配置
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class CodeGenFileConfig {
    private String baseDir;
    private List<OutFileConfig> outFileConfigs;

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public List<OutFileConfig> getOutFileConfigs() {
        return outFileConfigs;
    }

    public void setOutFileConfigs(List<OutFileConfig> outFileConfigs) {
        this.outFileConfigs = outFileConfigs;
    }
}
