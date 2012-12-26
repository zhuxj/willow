/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen.model.codegenconfig;

/**
 * <pre>
 * 代码生成器配置类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class CodeGenConfig {
    private DatabaseConfig databaseConfig;
    private DeveloperConfig developerConfig;
    private FtlConfig ftlConfig;
    private CodeGenFileConfig codeGenFileConfig;

    private Table table;

    public CodeGenConfig() {
    }

    public CodeGenFileConfig getCodeGenFileConfig() {
        return codeGenFileConfig;
    }

    public void setCodeGenFileConfig(CodeGenFileConfig codeGenFileConfig) {
        this.codeGenFileConfig = codeGenFileConfig;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public FtlConfig getFtlConfig() {
        return ftlConfig;
    }

    public void setFtlConfig(FtlConfig ftlConfig) {
        this.ftlConfig = ftlConfig;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public DeveloperConfig getDeveloperConfig() {
        return developerConfig;
    }

    public void setDeveloperConfig(DeveloperConfig developerConfig) {
        this.developerConfig = developerConfig;
    }
}
