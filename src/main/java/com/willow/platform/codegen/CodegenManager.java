/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.platform.codegen;


import com.willow.platform.codegen.config.CodeGenConfigParser;
import com.willow.platform.codegen.db.DataSourceConfig;
import com.willow.platform.codegen.db.DataSourceConfigUtil;
import com.willow.platform.codegen.model.TableClass;
import com.willow.platform.codegen.model.codegenconfig.CodeGenConfig;
import com.willow.platform.codegen.model.codegenconfig.DatabaseConfig;
import com.willow.platform.codegen.model.codegenconfig.Table;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.internal.db.ConnectionFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * <pre>
 * 代码生成管理器
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class CodegenManager {
    private String configPath;

    public void generate() throws Exception {
        //1.解析代码生成器配置，获取表名，类名，模板路径，生成文件路径
        CodeGenConfigParser codeGenConfigParser = new CodeGenConfigParser();
        CodeGenConfig codeGenConfig = codeGenConfigParser.parserCodeGenConfig(configPath);
        // 数据库配置信息
        DatabaseConfig databaseConfig = codeGenConfig.getDatabaseConfig();
        //表配置信息
        Table table = codeGenConfig.getTable();

        //2.获取表信息，解析内容包括字段名、字段中文名称、字段类型，字段对于的java类型
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDatabaseConfig(databaseConfig);
        TableClassManager tableClassManager = new TableClassManager();

        TableClass tableClass = new TableClass();
        tableClass.setTable(table);
        TableClass resultTable = tableClassManager.getTableClass(tableClass);
        //3.解析根据表信息、类名等信息和模板，获得内容
        CodeGenTemplateParser codeGenTemplateParser = new CodeGenTemplateParser();
        Map<String, String> contentMap = codeGenTemplateParser.parserFtlContent(codeGenConfig, tableClass);

        //4.根据模板生成后的模板生成对于的文件，包括xxxMapper.java,xxxMapper.xml,xxxDao,xxxService,xxxController,list.jsp,
        //add.jsp,update.jsp,detail.jsp和对于的js文件
        CodegenFileGenerater codegenFileGenerater = new CodegenFileGenerater(codeGenConfig, contentMap);
        codegenFileGenerater.generateFiles();
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
}
