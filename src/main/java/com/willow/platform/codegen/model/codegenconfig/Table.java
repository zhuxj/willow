/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.platform.codegen.model.codegenconfig;

import com.willow.platform.utils.FieldPropertyConvert;

/**
 * <pre>
 * 表信息
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class Table {
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
     * jsp路径
     */
    private String jspDir;
    /**
     * js路径
     */
    private String jsDir;


    /**
     * 完整类包名
     */
    private String packageVar;

    /**
     * 类包名
     */
    private String simplePackageVar;

    public String getJspDir() {
        return jspDir;
    }

    public void setJspDir(String jspDir) {
        this.jspDir = jspDir;
    }

    public String getJsDir() {
        return jsDir;
    }

    public void setJsDir(String jsDir) {
        this.jsDir = jsDir;
    }

    public String getSimplePackageVar() {
        return simplePackageVar;
    }

    public void setSimplePackageVar(String simplePackageVar) {
        this.simplePackageVar = simplePackageVar;
    }

    public String getClassVariable() {
        return FieldPropertyConvert.classVarToFirstDown(classVar);
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
}
