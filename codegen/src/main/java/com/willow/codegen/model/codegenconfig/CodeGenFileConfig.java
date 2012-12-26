/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen.model.codegenconfig;

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

    /**
     * jsp路径
     */
    private String jspDir;
    /**
     * js路径
     */
    private String jsDir;

    /**
     * 页面类型，shopadmin，member等
     */
    private String pageType;

    private List<OutFileConfig> outFileConfigs;

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getJspDir() {
        return jspDir;
    }

    public void setJspDir(String jspDir) {
        this.jspDir = jspDir;
    }

    public String getJsDir() {
        return jsDir;
    }

    public void setJsDir(String jsDir) {
        this.jsDir = jsDir;
    }

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
