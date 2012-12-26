/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.codegen.model;

/**
 * <pre>
 *  列信息对象
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class FieldColumn {
    /**
     * 字段名
     */
    private String columnName;

    /**
     * 字段长度
     */
    private Integer columnLength;

    /**
     * 类属性
     */
    private String javaProperty;
    /**
     * 类属性名
     */
    private String propName;

    /**
     * 数据库类型
     */
    private String columnType;

    /**
     * java类型
     */
    private String jdbcType;

    /**
     * 是否必填
     */
    private Boolean required;

    private String gsJavaProperty;

    /**
     * 是否包含排除的属性
     *
     * @return
     */
    public Boolean getIsIncludeField() {
        boolean result = false;
        if ("objId".equals(javaProperty) || "createTime".equals(javaProperty) || "updateTime".equals(javaProperty) || "userId".equals(javaProperty)) {
            result = true;
        }
        return result;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getGsJavaProperty() {
        return gsJavaProperty;
    }

    public void setGsJavaProperty(String gsJavaProperty) {
        this.gsJavaProperty = gsJavaProperty;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(Integer columnLength) {
        this.columnLength = columnLength;
    }

    public String getJavaProperty() {
        return javaProperty;
    }

    public void setJavaProperty(String javaProperty) {
        this.javaProperty = javaProperty;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }
}
