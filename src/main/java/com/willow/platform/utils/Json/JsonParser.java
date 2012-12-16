/**
 * 版权所有 违者必究
 * by 朱贤俊
 */

package com.willow.platform.utils.Json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专门处理json格式的解析
 *
 * @author 陈谋坤 (chenmoukun@pdpower.com)
 * @version Revision: 1.00 Date：Apr 14, 2008
 */
public class JsonParser {
    /**
     * JSON处理含有嵌套关系对象，避免出现异常：net.sf.json.JSONException: There is a cycle in the hierarchy的方法
     * 注意：这样获得到的字符串中，引起嵌套循环的属性会置为null
     *
     * @param obj
     * @return
     */
    public static JSONObject getJsonObject(Object obj) {

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

        return JSONObject.fromObject(obj, jsonConfig);
    }

    /**
     * JSON处理含有嵌套关系对象，避免出现异常：net.sf.json.JSONException: There is a cycle in the hierarchy的方法
     * 注意：这样获得到的字符串中，引起嵌套循环的属性会置为null
     *
     * @param obj
     * @return
     */
    public static JSONArray getJsonArray(Object obj) {

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

        JSONArray jsonArray = JSONArray.fromObject(obj, jsonConfig);
        if (jsonArray == null) {
            return new JSONArray();
        }
        return jsonArray;
    }

    /**
     * 解析JSON字符串成一个MAP
     *
     * @param jsonStr json字符串，格式如： {dictTable:"BM_XB",groupValue:"分组值"}
     * @return
     */
    public static Map<String, String> parseJsonStr(String jsonStr) {

        Map<String, String> result = new HashMap<String, String>();

        JSONObject jsonObj = JsonParser.getJsonObject(jsonStr);

        for (Object key : jsonObj.keySet()) {
            result.put((String) key, jsonObj.get(key).toString());
        }
        return result;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static List<JSONObject> parseJsonArray(String jsonArrayStr) {

        if (StringUtils.isEmpty(jsonArrayStr))
            return null;

        List<Object> list = JSONArray.toList(getJsonArray(jsonArrayStr));
        List<JSONObject> result = new ArrayList<JSONObject>();

        for (Object obj : list) {
            JSONObject jsonObj = getJsonObject(obj);
            result.add(jsonObj);
        }

        return result;
    }

    /**
     * 把对象转换成为Json字符串
     *
     * @param obj
     * @return
     */
    public static String obj2json(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("对象参数不能为空。");
        }
        JSONObject jsonObject = JsonParser.getJsonObject(obj);
        return jsonObject.toString();
    }

    /**
     * 把对象转换成为Json字符串
     *
     * @param obj
     * @return
     */
    public static String array2json(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("对象参数不能为空。");
        }
        JSONArray jsonArray = JsonParser.getJsonArray(obj);
        return jsonArray.toString();
    }

}
