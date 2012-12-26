/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen.model.codegenconfig;

/**
 * <pre>
 *  数据库配置信息
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class DatabaseConfig {
    private String url;

    private String userName;

    private String password;

    public DatabaseConfig() {
    }

    public DatabaseConfig(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
