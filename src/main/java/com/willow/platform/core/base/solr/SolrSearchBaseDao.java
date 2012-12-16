/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2011 
 * 日    期：11-12-26
 */
package com.willow.platform.core.base.solr;

import com.willow.platform.core.PageParam;
import com.willow.platform.core.base.Sort;
import com.willow.platform.utils.EasyApplicationContextUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *   Solr全文检索DAO基类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.82
 */
public abstract class SolrSearchBaseDao {

    private static final Logger LOG = LoggerFactory.getLogger(SolrSearchBaseDao.class);

    public static final int DEFAULT_PAGE_NUM = 20;

    public static final int MAX_RESULT_NUM = 100;

    private String solrServerUrl;

    private String coreUrl;

    protected CommonsHttpSolrServer solrServer;

    /**
     * 初始化SolrServer.
     *
     * @throws java.net.MalformedURLException
     */
    public void init() throws MalformedURLException {
        solrServerUrl = "";
        this.coreUrl = solrServerUrl + getCoreName();
        solrServer = new CommonsHttpSolrServer(coreUrl);
        solrServer.setSoTimeout(50000);
        solrServer.setConnectionTimeout(50000);
        solrServer.setDefaultMaxConnectionsPerHost(100);
        solrServer.setMaxTotalConnections(100);
        solrServer.setFollowRedirects(false);
        solrServer.setAllowCompression(true);
        solrServer.setMaxRetries(1);
    }

    public void deleteByIds(final List<String> ids) {

        TaskExecutor taskExecutor = (TaskExecutor) EasyApplicationContextUtils.getBeanByName("taskExecutor");
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    solrServer.deleteById(ids);
                    solrServer.commit();
                } catch (SolrServerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void deleteById(final String id) {
        TaskExecutor taskExecutor = (TaskExecutor) EasyApplicationContextUtils.getBeanByName("taskExecutor");
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    solrServer.deleteById(id);
                    solrServer.commit();
                } catch (SolrServerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    protected void setPageParam(SolrQuery solrQuery, PageParam pageParam) {
        if (pageParam.getPageSize() <= 0) {
            pageParam.setPageSize(DEFAULT_PAGE_NUM);
        }
        solrQuery.setStart((pageParam.getPageNo() - 1) * pageParam.getPageSize());
        solrQuery.setRows(pageParam.getPageSize());
    }

    /**
     * 重建整个索引.
     *
     * @return
     */
    public boolean rebuildIndex() {
        String urlStr = coreUrl + "/dataimport?command=full-import" + this.appendSolrSecurityToken();
        return accessUrl(urlStr);

    }

    /**
     * 内核名.
     *
     * @return
     */
    public abstract String getCoreName();

    private boolean accessUrl(String urlStr) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext context = new BasicHttpContext();
        HttpGet httpGet = new HttpGet(urlStr);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet, context);
            if (HttpStatus.SC_OK == httpResponse.getStatusLine()
                    .getStatusCode()) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新索引.
     *
     * @return
     */
    public boolean updateIndex() {
        String urlStr = coreUrl + "/dataimport?command=delta-import&optimize=false" + this.appendSolrSecurityToken();
        return accessUrl(urlStr);
    }

    /**
     * 获取高亮字段结果.
     *
     * @param hl
     * @param fieldName
     * @return
     */
    public String getHighlightField(Map<String, List<String>> hl, String fieldName) {
        List<String> hlField = hl.get(fieldName);
        if (CollectionUtils.isNotEmpty(hlField)) {
            return hlField.get(0);
        } else {
            return null;
        }

    }

    protected void setSortFields(List<Sort> sortMap, SolrQuery solrQuery) {
        for (Sort sort : sortMap) {
            SolrQuery.ORDER order = SolrQuery.ORDER.asc;
            if (!sort.isOrderType()) {
                order = SolrQuery.ORDER.desc;
            }
            solrQuery.addSortField(sort.getOrderFieldName().getClassFieldName(), order);
        }
    }

    /**
     * 追加solr安全验证token的值
     *
     * @return
     */
    private String appendSolrSecurityToken() {
        return "&token=zhuxj";
    }

    /**
     * 查询时增加token参数
     *
     * @param solrQuery
     */
    protected void setSolrSecurityToken(SolrQuery solrQuery) {
        solrQuery.add("token", "zhuxj");
    }
}

