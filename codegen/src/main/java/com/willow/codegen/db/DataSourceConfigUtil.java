/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen.db;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class DataSourceConfigUtil {
    private static DataSourceConfig dataSourceConfig;

    public static DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    public static void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
        DataSourceConfigUtil.dataSourceConfig = dataSourceConfig;
    }
}
