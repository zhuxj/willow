/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen.model.codegenconfig;

/**
 * <pre>
 * 开发者信息配置
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class DeveloperConfig {
    /**
     * 开发者
     */
    private String developer;
    /**
     * 公司名称
     */
    private String company;

    /**
     * 版本
     */
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
