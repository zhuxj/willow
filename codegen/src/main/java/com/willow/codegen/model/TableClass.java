/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.codegen.model;


import com.willow.codegen.model.codegenconfig.Table;

import java.util.List;

/**
 * <pre>
 *  表类信息对象
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class TableClass {

    /**
     * 数据库表名
     */
    private String tableCode;
    /**
     * 数据库表中文名
     */
    private String tableName;
    /**
     * 类名
     */
    private String classVar;

    /**
     * 类包名
     */
    private String packageVar;

    /**
     * 类表信息
     */
    private List<FieldColumn> fieldColumns;

    public void setTable(Table table) {
        this.tableCode = table.getTableCode();
        this.tableName = table.getTableName();
        this.classVar = table.getClassVar();
        this.packageVar = table.getPackageVar();
    }


    public TableClass() {
    }

    public TableClass(String tableCode, String tableName, String classVar, String packageVar) {
        this.tableCode = tableCode;
        this.tableName = tableName;
        this.classVar = classVar;
        this.packageVar = packageVar;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassVar() {
        return classVar;
    }

    public void setClassVar(String classVar) {
        this.classVar = classVar;
    }

    public String getPackageVar() {
        return packageVar;
    }

    public void setPackageVar(String packageVar) {
        this.packageVar = packageVar;
    }

    public List<FieldColumn> getFieldColumns() {
        return fieldColumns;
    }

    public void setFieldColumns(List<FieldColumn> fieldColumns) {
        this.fieldColumns = fieldColumns;
    }
}
