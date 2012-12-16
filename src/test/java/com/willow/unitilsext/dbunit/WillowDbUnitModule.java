/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-13
 */
package com.willow.unitilsext.dbunit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.springframework.util.Assert;
import org.unitils.dbunit.DbUnitModule;
import org.unitils.dbunit.util.DbUnitDatabaseConnection;

import java.sql.SQLException;

/**
 * <pre>
 * 重新指定unitils.module.dbunit.className，否则一直表没有字段
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class WillowDbUnitModule extends DbUnitModule {

    private static Log logger = LogFactory.getLog(WillowDbUnitModule.class);

    @Override
    protected DbUnitDatabaseConnection createDbUnitConnection(String schemaName) {
        Assert.notNull(schemaName, "数据集的模式名不能为空，且必须规范。");
        // 获取数据连接
        DbUnitDatabaseConnection connection = super.createDbUnitConnection(schemaName);

        //没有根据数据库类型使用特订数据源的MetadataHandler，以下代码防止取不到表的元数据信息
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        boolean isMySQL = false;
        try {
            String databaseProductName = connection.getConnection().getMetaData().getDatabaseProductName();
            if ("MySQL".equals(databaseProductName)) {
                isMySQL = true;
                logger.info("Database product name = " + databaseProductName + ", set DatabaseConfig.PROPERTY_METADATA_HANDLER to new MySqlMetadataHandler()");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // if database is MySQL, reset PROPERTY_METADATA_HANDLER to use MySQL specific Handler
        if (isMySQL) {
            DatabaseConfig config = connection.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return connection;
    }
}
