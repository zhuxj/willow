/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2011 
 * 日    期：11-12-26
 */
package com.willow.platform.core.base.solr;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *   Solr检索工具类.
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.82
 */
public class SolrUtils {

    /**
     * 检索关键字分词缓存.
     */
    private static Map<String, String> splitWords = new Hashtable<String, String>();

    /**
     * 条件多个OR关系条件.
     *
     * @param field        字段名
     * @param values       进行OR检索的字段列表.
     * @param queryClauses 查询表达式集合.
     */
    public static void appendOrConditions(String field, List<String> values, List<String> queryClauses) {
        if (CollectionUtils.isNotEmpty(values)) {
            List<String> clauses = new ArrayList<String>();
            for (String value : values) {
                if (StringUtils.isNotBlank(value)) {
                    clauses.add("(" + field + ":" + value + ")");
                }
            }
            if (clauses.size() > 0) {
                queryClauses.add("(" + StringUtils.join(clauses.iterator(), " OR ") + ")");
            }
        }
    }

    /**
     * 构造区间条件
     *
     * @param field
     * @param startValue
     * @param endValue
     * @param queryClauses
     */
    public static void appendBetweenCondition(String field, String startValue, String endValue, List<String> queryClauses) {
        if (StringUtils.isNotBlank(startValue) || StringUtils.isNotBlank(endValue)) {
            if (StringUtils.isBlank(startValue)) {
                startValue = "*";
            }
            if (StringUtils.isBlank(endValue)) {
                endValue = "*";
            }
            queryClauses.add("(" + field + ":[" + startValue + " TO " + endValue + "])");
        }
    }

    /**
     * 追加一个field并占有的比例的条件
     *
     * @param field
     * @param value
     * @param boost
     * @param queryClauses
     */
    public static void appendCondition(String field, String value, double boost, List<String> queryClauses) {
        if (StringUtils.isNotBlank(value)) {
            queryClauses.add("(" + field + ":(" + value + "))^" + boost);
        }
    }

    /**
     * 追加多个字段间的OR条件
     *
     * @param feildValueMap
     * @param queryClauses
     */
    public static void appendConditionForOrMap(Map<String, String> feildValueMap, List<String> queryClauses) {
        List<String> sbl = new ArrayList<String>();
        if (feildValueMap != null) {
            for (String field : feildValueMap.keySet()) {
                sbl.add("(" + field + ":(" + feildValueMap.get(field) + "))");
            }
        }
        if (sbl.size() > 0) {
            queryClauses.add("(" + StringUtils.join(sbl.iterator(), " OR ") + ")");
        }
    }

    /**
     * 追加一个field条件
     *
     * @param field
     * @param value
     * @param queryClauses
     */
    public static void appendCondition(String field, String value, List<String> queryClauses) {
        if (StringUtils.isNotBlank(value)) {
            queryClauses.add("(" + field + ":(" + value + "))");
        }
    }

    /**
     * 追加包含某个前缀的字符串条件
     *
     * @param field
     * @param value
     * @param queryClauses
     */
    public static void appendPrefixCondition(String field, String value, List<String> queryClauses) {
        if (StringUtils.isNotBlank(value)) {
            queryClauses.add("(" + field + ":" + value + "*)");
        }
    }

    /**
     * 追加不等于多个值的条件
     *
     * @param field
     * @param values
     * @param queryClauses
     */
    public static void appendNotConditions(String field, List<String> values, List<String> queryClauses) {
        if (CollectionUtils.isNotEmpty(values)) {
            List<String> clauses = new ArrayList<String>();
            for (String value : values) {
                if (StringUtils.isNotBlank(value)) {
                    clauses.add("(" + field + ":" + value + ")");
                }
            }
            if (clauses.size() > 0) {
                queryClauses.add("-(" + StringUtils.join(clauses.iterator(), " OR ") + ")");
            }
        }
    }

    /**
     * 追加不等于一个值的条件
     *
     * @param field
     * @param values
     * @param queryClauses
     */
    public static void appendNotCondition(String field, String values, List<String> queryClauses) {
        if (StringUtils.isNotBlank(values)) {
            queryClauses.add("-(" + field + ":" + values + ")");
        }
    }

    public static void appendPhraseWithSplit(String field, String value, double noSplitBoost, double splitBoost, List<String> queryClauses) {
        if (StringUtils.isNotBlank(value)) {
            if (value.length() > 3) {
                String splitKeyword = SolrUtils.splitWord(value);
                queryClauses.add("(" + field + ":(" + value + ")^" + noSplitBoost + " OR " + field + ":(" + splitKeyword + ")^" + splitBoost + ")");
            } else {
                queryClauses.add("(" + field + ":(" + value + ")^" + noSplitBoost + ")");
            }
        }
    }

    public static String splitWord(String word) {
        if (StringUtils.isBlank(word)) {
            return "";
        }
        String result = splitWords.get(word);
        if (result != null) {
            return result;
        }

        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_30);
        TokenStream tokenStream = analyzer.tokenStream("dummy", new StringReader(word));
        List<String> words = new ArrayList<String>();
        TermAttribute termAtt = tokenStream.getAttribute(TermAttribute.class);
        try {
            while (tokenStream.incrementToken()) {
                words.add(termAtt.term());
            }
        } catch (IOException e) {

        }
        result = StringUtils.join(words.iterator(), " ");

        splitWords.put(word, result);

        return result;
    }

    public static void main(String[] args) {
        System.out.println(splitWord("期留"));
        System.out.println(splitWord("来自天堂的消息"));
        System.out.println(splitWord("我在哪里错过了你"));
        System.out.println(splitWord("我们在哪里错过了你"));
        System.out.println(splitWord("花都开好了"));
        System.out.println(splitWord("乌龙院系列丛书 虾师高徒"));
        System.out.println(splitWord("的人"));
        System.out.println(splitWord("妈妈说给青春期儿女的悄悄话"));
        System.out.println(splitWord("旅游达人"));
        System.out.println(splitWord("中国人"));
    }

}
