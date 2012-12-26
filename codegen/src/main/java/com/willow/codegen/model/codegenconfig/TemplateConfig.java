/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen.model.codegenconfig;

/**
 * <pre>
 * ftl模板配置信息
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class TemplateConfig {
    private String id;
    private String name;

    public TemplateConfig(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public TemplateConfig() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
