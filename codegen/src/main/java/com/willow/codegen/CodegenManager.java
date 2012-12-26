/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.codegen;


import com.willow.codegen.config.CodeGenConfigParser;
import com.willow.codegen.db.DataSourceConfig;
import com.willow.codegen.model.TableClass;
import com.willow.codegen.model.codegenconfig.CodeGenConfig;
import com.willow.codegen.model.codegenconfig.DatabaseConfig;
import com.willow.codegen.model.codegenconfig.Table;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.util.Map;

/**
 * <pre>
 * 代码生成管理器
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class CodegenManager {
    /**
     * 使用ant生成
     *
     * @param configPath
     * @throws Exception
     */
    public void generateFromAnt(String baseDir, String configPath) throws Exception {
        //1.解析代码生成器配置，获取表名，类名，模板路径，生成文件路径
        CodeGenConfigParser codeGenConfigParser = new CodeGenConfigParser();
        CodeGenConfig codeGenConfig = codeGenConfigParser.parserCodeGenConfigFromFileSystemPath(configPath);
//        codeGenConfig.getCodeGenFileConfig().setBaseDir(baseDir);
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
        CodeGenTemplateParser codeGenTemplateParser = new CodeGenTemplateParser(new FileSystemResource(baseDir + "/src/main/resources/ftl"));
        Map<String, String> contentMap = codeGenTemplateParser.parserFtlContent(codeGenConfig, tableClass);

        //4.根据模板生成后的模板生成对于的文件，包括xxxMapper.java,xxxMapper.xml,xxxDao,xxxService,xxxController,list.jsp,
        //add.jsp,update.jsp,detail.jsp和对于的js文件
        CodegenFileGenerater codegenFileGenerater = new CodegenFileGenerater(codeGenConfig, contentMap);
        codegenFileGenerater.generateFiles();
    }


    /**
     * java运行生成
     *
     * @throws Exception
     */
    public void generateFromJava(String configPath) throws Exception {
        //1.解析代码生成器配置，获取表名，类名，模板路径，生成文件路径
        CodeGenConfigParser codeGenConfigParser = new CodeGenConfigParser();
        CodeGenConfig codeGenConfig = codeGenConfigParser.parserCodeGenConfigFromClassPath(configPath);
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
        CodeGenTemplateParser codeGenTemplateParser = new CodeGenTemplateParser(new ClassPathResource("ftl/"));
        Map<String, String> contentMap = codeGenTemplateParser.parserFtlContent(codeGenConfig, tableClass);

        //4.根据模板生成后的模板生成对于的文件，包括xxxMapper.java,xxxMapper.xml,xxxDao,xxxService,xxxController,list.jsp,
        //add.jsp,update.jsp,detail.jsp和对于的js文件
        CodegenFileGenerater codegenFileGenerater = new CodegenFileGenerater(codeGenConfig, contentMap);
        codegenFileGenerater.generateFiles();
    }

}
