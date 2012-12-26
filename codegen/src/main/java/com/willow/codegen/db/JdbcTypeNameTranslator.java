/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.codegen.db;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class JdbcTypeNameTranslator {
    public static final Map<String, String> typeMap;

    public static String getJdbcType(String columnType) {
        return typeMap.get(columnType);
    }

    static {
        typeMap = new HashMap<String, String>();
        typeMap.put("VARCHAR", "String");
        typeMap.put("CHAR", "String");
        typeMap.put("INT", "Integer");
        typeMap.put("INT UNSIGNED", "Integer");
        typeMap.put("TEXT", "String");

    }

}
