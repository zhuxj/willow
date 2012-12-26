/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen.model.codegenconfig;

import java.util.List;

/**
 * <pre>
 * 模板配置信息
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class FtlConfig {
    private String basePath;
    private List<TemplateConfig> templateConfigs;

    public FtlConfig() {
    }

    public FtlConfig(String basePath, List<TemplateConfig> templateConfigs) {
        this.basePath = basePath;
        this.templateConfigs = templateConfigs;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public List<TemplateConfig> getTemplateConfigs() {
        return templateConfigs;
    }

    public void setTemplateConfigs(List<TemplateConfig> templateConfigs) {
        this.templateConfigs = templateConfigs;
    }
}
