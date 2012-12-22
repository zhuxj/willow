/**
 * @(#)com.willow.base.dao.Page
 * 2009-6-15
 * Copyright 2009 
 * Book软件公司, 版权所有 违者必究
 */
package com.willow.platform.core;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 *
 * @param <T> Page中记录的类型.
 * @author 朱贤俊
 * @version 1.0
 */
public class Page<T> {

    protected List<T> result = Collections.emptyList();

    protected int totalCount = -1;//总记录数

    protected int pageCount;//总页数

    //其他附加信息
    protected Map<String, Object> map = new HashMap<String, Object>();

    private PageParam pageParam;

    public Page(PageParam pageParam) {
        this.pageParam = pageParam;
    }

    public Page(PageParam pageParam, List<T> result) {
        this.pageParam = pageParam;
        this.result = result;
    }

    /**
     * 取得页内的记录列表.
     */
    public List<T> getResult() {
        return result;
    }

    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * 取得总记录数,默认值为-1.
     */
    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(final int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            this.pageCount = (totalCount + pageParam.getPageSize() - 1) / pageParam.getPageSize();
        }
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageParam.pageNo + 1 <= getPageCount());
    }

    /**
     * 取得下页的页号,序号从1开始.
     */
    public int getNextPage() {
        if (isHasNext())
            return pageParam.pageNo + 1;
        else
            return pageParam.pageNo;
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return (pageParam.pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号,序号从1开始.
     */
    public int getPrePage() {
        if (isHasPre())
            return pageParam.pageNo - 1;
        else
            return pageParam.pageNo;
    }

    /**********************************************/

    /**
     * 得到总页数
     *
     * @return
     */
    public int getPageCount() {
//        return this.pageCount > PageParam.MAX_PAGENUM ? PageParam.MAX_PAGENUM : pageCount;
        return this.pageCount;
    }

    public void put(String key, Object value) {
        this.map.put(key, value);
    }

    public PageParam getPageParam() {
        return pageParam;
    }


    public String toJSON() {
        return toJSON(null);
    }

    public String toJSON(JsonConfig jsonConfig) {
        JSONObject jsonResult = new JSONObject();
        JSONArray data = null;
        if (jsonConfig != null) {
            jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP);
            data = JSONArray.fromObject(result, jsonConfig);
        } else {
            data = JSONArray.fromObject(result);
        }
        jsonResult.put("data", data);

        JSONObject pageInfo = new JSONObject();
        pageInfo.put("pageNum", this.getPageParam().getPageNo());
        pageInfo.put("pageSize", this.getPageParam().getPageSize());
        pageInfo.put("pageCount", String.valueOf(this.getPageCount()));
        jsonResult.put("pageInfo", pageInfo);

        jsonResult.putAll(map);
        return jsonResult.toString();
    }

    public Map<String, Object> toDataMap(JsonConfig jsonConfig) {
        JSONArray data = null;
        if (jsonConfig != null) {
            jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP);
            data = JSONArray.fromObject(result, jsonConfig);
        } else {
            data = JSONArray.fromObject(result);
        }
        map.put("data", data);

        JSONObject pageInfo = new JSONObject();
        pageInfo.put("pageNum", this.getPageParam().getPageNo());
        pageInfo.put("pageSize", this.getPageParam().getPageSize());
        pageInfo.put("pageCount", String.valueOf(this.getPageCount()));
        pageInfo.put("recordCount", String.valueOf(this.getTotalCount()));
        map.put("pageInfo", pageInfo);
        return map;
    }
}
