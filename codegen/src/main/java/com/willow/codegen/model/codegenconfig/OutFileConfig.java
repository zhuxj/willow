/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen.model.codegenconfig;

/**
 * <pre>
 *  输出文件信息
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class OutFileConfig {
    private String refTemplate;
    private String fileName;
    private String dir;
    private Boolean override;

    public Boolean getOverride() {
        return override;
    }

    public void setOverride(Boolean override) {
        this.override = override;
    }

    public String getRefTemplate() {
        return refTemplate;
    }

    public void setRefTemplate(String refTemplate) {
        this.refTemplate = refTemplate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
