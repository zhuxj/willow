/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen.db;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * <pre>
 * DataSource的生产库
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class DataSourceFactory {
    private static DataSourceFactory dataSourceFactory;

    private DataSourceFactory() {

    }

    public static DataSourceFactory getInstance() {
        if (dataSourceFactory == null) {
            dataSourceFactory = new DataSourceFactory();
        }
        return dataSourceFactory;
    }

    public DataSource createDataSource() throws Exception {
        Properties properties = DataSourceConfigUtil.getDataSourceConfig().getDataSourceProps();
        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        return dataSource;
    }

}
