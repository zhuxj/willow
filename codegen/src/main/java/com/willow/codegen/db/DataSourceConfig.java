/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.codegen.db;

import com.willow.codegen.model.codegenconfig.DatabaseConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class DataSourceConfig {

    private String url;

    private String userName;

    private String password;

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.url = databaseConfig.getUrl();
        this.userName = databaseConfig.getUserName();
        this.password = databaseConfig.getPassword();
        DataSourceConfigUtil.setDataSourceConfig(this);
    }


    public DataSourceConfig() {

    }

    public DataSourceConfig(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    /**
     * 获取数据源的配置属性信息
     *
     * @return
     */
    public Properties getDataSourceProps() {
        DataSourceConfig dataSourceConfig = this;
        Properties properties = new Properties();
        properties.setProperty("driverClassName", "com.mysql.jdbc.Driver");
        properties.setProperty("url", dataSourceConfig.getUrl());
        properties.setProperty("username", dataSourceConfig.getUserName());
        properties.setProperty("password", dataSourceConfig.getPassword());

        if (dataSourceConfig.getDsProps() != null) {
            for (Map.Entry<String, String> entry : dataSourceConfig.getDsProps().entrySet()) {
                properties.setProperty(entry.getKey(), entry.getValue());
            }
        }
        return properties;
    }


    private static final Map<String, String> DEFAULT_DSPROPS = new HashMap<String, String>();

    static {
        DEFAULT_DSPROPS.put("initialSize", "1");
        DEFAULT_DSPROPS.put("maxActive", "200");
        DEFAULT_DSPROPS.put("minIdle", "2");
        DEFAULT_DSPROPS.put("maxIdle", "10");
        DEFAULT_DSPROPS.put("maxWait", "60000");
        DEFAULT_DSPROPS.put("minEvictableIdleTimeMillis", "3600000");
        DEFAULT_DSPROPS.put("removeAbandoned", "true");
        DEFAULT_DSPROPS.put("removeAbandonedTimeout", "300");
        DEFAULT_DSPROPS.put("testOnBorrow", "true");
        DEFAULT_DSPROPS.put("testOnReturn", "false");
        DEFAULT_DSPROPS.put("testWhileIdle", "true");
        DEFAULT_DSPROPS.put("validationQuery", "select 1");
        DEFAULT_DSPROPS.put("validationQueryTimeout", "2");
        DEFAULT_DSPROPS.put("timeBetweenEvictionRunsMillis", "60000");
        DEFAULT_DSPROPS.put("numTestsPerEvictionRun", "200");
    }

    public Map<String, String> getDsProps() {
        return DEFAULT_DSPROPS;
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
