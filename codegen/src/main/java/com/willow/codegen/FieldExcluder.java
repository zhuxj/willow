/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  排除字段
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class FieldExcluder {
    //排除字段集
    private static Map<String, String> fieldExcludeMap;

    static {
        fieldExcludeMap = new HashMap<String, String>();
        fieldExcludeMap.put("id", "id");
    }

    /**
     * 是否包含该字段
     *
     * @param field
     * @return
     */
    public static boolean isContainField(String field) {
        return fieldExcludeMap.containsKey(field);
    }

}
