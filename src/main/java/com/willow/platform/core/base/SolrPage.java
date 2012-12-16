/**
 * 版权声明：中图一购网络科技有限公司 版权所有 违者必究 2011 
 * 日    期：11-2-28
 */
package com.willow.platform.core.base;

import com.willow.platform.core.Page;
import com.willow.platform.core.PageParam;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.List;
import java.util.Map;

/**
 * @author 姜文樟
 * @version 1.71
 *          功能说明：
 */
public class SolrPage<T> {

    private Page<T> page;

    /**
     * 切面统计结果.
     */
    private Map<String, List<FacetField.Count>> facetResults;

    public SolrPage(PageParam param) {
        this.page = new Page<T>(param);
    }

    public Page<T> getPage() {

        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public Map<String, List<FacetField.Count>> getFacetResults() {
        return facetResults;
    }

    public void setFacetResults(Map<String, List<FacetField.Count>> facetResults) {
        this.facetResults = facetResults;
    }
}
