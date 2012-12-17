/**
 * 版权声明：贤俊工作室 版权所有 违者必究
 * 日    期：2012-12-17
 */
package com.willow.platform.module.basic.website.service;

import com.willow.platform.core.base.dao.BaseDao;
import com.willow.platform.core.base.service.BaseService;
import com.willow.platform.module.basic.website.dao.WebSiteDao;
import com.willow.platform.module.basic.website.domain.WebSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 网站配置业务类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.0
 */
@Service
public class WebSiteService extends BaseService<WebSite> {
    @Autowired
    private WebSiteDao webSiteDao;

    public void saveOrUpdateWebSite(WebSite webSite, String companyId) {
        webSite.setCompanyId(companyId);
        if (getWebSiteByCompanyId(companyId) != null) {
            update(webSite);
        } else {
            save(webSite);
        }
    }


    /**
     * 根据companyId查询
     *
     * @param companyId
     * @return
     */
    public WebSite getWebSiteByCompanyId(String companyId) {
        WebSite filter = new WebSite();
        filter.setCompanyId(companyId);
        WebSite webSite = webSiteDao.selectByCondition(filter);
        return webSite;
    }

    @Override
    public BaseDao<WebSite> getDao() {
        return webSiteDao;
    }

    public void setWebSiteDao(WebSiteDao webSiteDao) {
        this.webSiteDao = webSiteDao;
    }
}
