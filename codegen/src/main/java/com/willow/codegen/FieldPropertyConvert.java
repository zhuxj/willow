/**
 * 版权声明：软件公司 版权所有 违者必究 2012
 * 日    期：12-12-15
 */
package com.willow.codegen;

/**
 * <pre>
 * 数据库字段和类属性之间转换
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class FieldPropertyConvert {

    /**
     * 首字母小写
     *
     * @param className
     * @return
     */
    public static String classVarToFirstDown(String className) {
        if (null == className) {
            return "";
        }
        char[] chars = className.toCharArray();
        StringBuffer property = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (i == 0) {
                property.append(String.valueOf(chars[i]).toLowerCase());
            } else {
                property.append(c);
            }
        }
        return property.toString();
    }


    /**
     * 判断是否是大写字母
     *
     * @param c
     * @return
     */
    public static Boolean isUp(char c) {
        if (c >= 'A' && c <= 'Z') {
            return true;
        }
        return false;
    }

    /**
     * java对象属性转换为数据库字段，如userName-->user_name
     *
     * @param property
     * @return
     */
    public static String propertyToField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer field = new StringBuffer();
        for (char c : chars) {
            if (isUp(c)) {
                field.append("_" + String.valueOf(c).toLowerCase());
            } else {
                field.append(c);
            }
        }
        return field.toString();
    }

    /**
     * 将数据库字段转换为java属性，如user_name-->userName
     *
     * @param field 字段名
     * @return
     */
    public static String fieldToProperty(String field) {
        if (null == field) {
            return "";
        }
        char[] chars = field.toCharArray();
        StringBuffer property = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    property.append(String.valueOf(chars[j]).toUpperCase());
                    i++;
                }
            } else {
                property.append(c);
            }
        }
        return property.toString();
    }

    /**
     * 将数据库字段转换为java属性，如user_name-->UserName
     *
     * @param field 字段名
     * @return
     */
    public static String fieldToGsProperty(String field) {
        if (null == field) {
            return "";
        }
        char[] chars = field.toCharArray();
        StringBuffer property = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    property.append(String.valueOf(chars[j]).toUpperCase());
                    i++;
                }
            } else {
                if (i == 0) {
                    property.append(String.valueOf(chars[i]).toUpperCase());
                } else {
                    property.append(c);
                }
            }
        }
        return property.toString();
    }

}
