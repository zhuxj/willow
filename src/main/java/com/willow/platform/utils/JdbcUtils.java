/**
 * 版权声明：软件公司 版权所有 违者必究 2011
 * 日    期：11-6-13
 */
package com.willow.platform.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 朱贤俊 * @version 1.0
 *         功能说明：
 */
public class JdbcUtils {

    public static void appendCondition(String field, String value, List<String> queryClauses, Map<String, Object> map) {
        if (StringUtils.isNotBlank(value)) {
            queryClauses.add("(" + field + " = :" + field + ")");
            map.put(field, value);
        }
    }

    public static void appendPrefixCondition(String field, String value, List<String> queryClauses, Map<String, Object> map) {
        if (StringUtils.isNotBlank(value)) {
            queryClauses.add("(" + field + " like :" + field + ")");
            map.put(field, value + "%");
        }
    }

    public static void appendLikeCondition(String field, String value, List<String> queryClauses, Map<String, Object> map) {
        if (StringUtils.isNotBlank(value)) {
            queryClauses.add("(" + field + " like :" + field + ")");
            map.put(field, "%" + value + "%");
        }
    }

    /**
     * 条件多个OR关系条件.
     *
     * @param field        字段名
     * @param values       进行OR检索的字段列表.
     * @param queryClauses 查询表达式集合.
     */
    public static void appendOrConditions(String field, List<String> values, List<String> queryClauses, Map<String, Object> map) {
        if (CollectionUtils.isNotEmpty(values)) {
            List<String> clauses = new ArrayList<String>();
            int i = 0;
            for (String value : values) {
                if (StringUtils.isNotBlank(value)) {
                    clauses.add("(" + field + " = :" + field + i + ")");
                    map.put(field + i++, value);
                }
            }
            if (clauses.size() > 0) {
                queryClauses.add("(" + StringUtils.join(clauses, " OR ") + ")");
            }
        }
    }

    public static void appendBetweenCondition(String field, String startValue, String endValue, List<String> queryClauses, Map<String, Object> map) {
        if (StringUtils.isNotBlank(startValue)) {
            queryClauses.add("(" + field + " >= :" + field + "start)");
            map.put(field + "start", startValue);
        }
        if (StringUtils.isNotBlank(endValue)) {
            queryClauses.add("(" + field + " <= :" + field + "end)");
            map.put(field + "end", endValue);
        }
    }

    public static void appendBetweenCondition(String field, int startValue, int endValue, List<String> queryClauses, Map<String, Object> map) {
        if (startValue != -1) {
            queryClauses.add("(" + field + " >= :" + field + "start)");
            map.put(field + "start", startValue);
        }
        if (endValue != -1) {
            queryClauses.add("(" + field + " <= :" + field + "end)");
            map.put(field + "end", endValue);
        }
    }

    public static void appendNotConditions(String field, List<String> values, List<String> queryClauses, Map<String, Object> map) {
        if (CollectionUtils.isNotEmpty(values)) {
            List<String> clauses = new ArrayList<String>();
            int i = 0;
            for (String value : values) {
                if (StringUtils.isNotBlank(value)) {
                    clauses.add("(" + field + " != :" + field + i + ")");
                    map.put(field + i++, value);
                }
            }
            if (clauses.size() > 0) {
                queryClauses.add("(" + StringUtils.join(clauses, " OR ") + ")");
            }
        }
    }

    public static String generateCountStr(String sql) {
        String lowerCaseHql = sql.toLowerCase();
        int start = lowerCaseHql.indexOf(START_RESERVE_WORDS);
        int end = sql.length();

        //判断是否包含保留字
        for (String key : END_RESERVE_WORDS) {
            if (lowerCaseHql.indexOf(key) > -1) {
                end = lowerCaseHql.indexOf(key);
                break;
            }
        }

        return SELECT_COUNT_PREFIX + sql.substring(start, end);
    }

    /**
     * 截取用于计算记录集总数的HQL语句的起始保留字“from”
     */
    private static final String START_RESERVE_WORDS = "from";
    /**
     * 计算记录集总数的HQL语法前缀
     */
    private static final String SELECT_COUNT_PREFIX = "select count(*) ";

    /**
     * 截取用于计算记录集总数的HQL语句的起始保留字数组。
     * 由于这些保留字在HQL语句中均为可选，因此要依次判断HQL语句中是否存在这些保留字。如果发现存在
     * 这些保留字中的任何一个（依次），便确定用于计算记录集总数的HQL语句的结束位置；如果未不存在
     * 这些保留字，则结束位置为原HQL的长度。
     */
    private static final String[] END_RESERVE_WORDS = {" group", " having", " order", "limit"};
}
