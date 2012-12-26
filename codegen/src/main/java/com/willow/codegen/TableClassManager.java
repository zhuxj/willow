/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen;

import com.willow.codegen.db.DataSourceFactory;
import com.willow.codegen.db.JdbcTypeNameTranslator;
import com.willow.codegen.model.FieldColumn;
import com.willow.codegen.model.TableClass;
import com.willow.codegen.FieldPropertyConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class TableClassManager {
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(TableClassManager.class);

    /**
     * 获得数据库表信息以及列属性信息
     *
     * @param tableClass
     * @return
     * @throws Exception
     */
    public TableClass getTableClass(TableClass tableClass) throws Exception {
        if (tableClass == null) {
            throw new CodegenException("tableClass can not be null");
        }
        List<FieldColumn> fieldColumns = this.getFieldColumnList(tableClass.getTableCode());
        tableClass.setFieldColumns(fieldColumns);
        logger.info("读取" + tableClass.getTableCode() + "表信息成功");
        return tableClass;
    }


    /**
     * 根据数据库获取数据库列以及属性信息
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public List<FieldColumn> getFieldColumnList(String tableName) throws Exception {
        List<FieldColumn> fieldColumns = new ArrayList<FieldColumn>();
        Connection connection = getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getColumns(null, null,
                tableName, null);
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            if (!FieldExcluder.isContainField(columnName)) {
                FieldColumn fieldColumn = new FieldColumn();
                String javaProperty = FieldPropertyConvert.fieldToProperty(columnName);
                String propName = rs.getString("REMARKS");
                Integer columnLength = rs.getInt("COLUMN_SIZE");
                String columnType = rs.getString("TYPE_NAME");
                String isNullable = rs.getString("IS_NULLABLE");
                String jdbcType = JdbcTypeNameTranslator.getJdbcType(columnType);
                fieldColumn.setColumnName(columnName);
                fieldColumn.setJavaProperty(javaProperty);
                fieldColumn.setGsJavaProperty(FieldPropertyConvert.fieldToGsProperty(columnName));
                fieldColumn.setPropName(propName);
                fieldColumn.setColumnLength(columnLength);
                fieldColumn.setColumnType(columnType);
                fieldColumn.setJdbcType(jdbcType);
                fieldColumns.add(fieldColumn);
                fieldColumn.setRequired("YES".equals(isNullable) ? false : true);
                logger.info(isNullable + " " + fieldColumn.getRequired());
            }
        }
        return fieldColumns;
    }


    /**
     * 获取连接
     *
     * @return
     * @throws Exception
     */
    private Connection getConnection() throws Exception {
        DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
        DataSource dataSource = dataSourceFactory.createDataSource();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        return connection;
    }
}
