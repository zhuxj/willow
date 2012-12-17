/**
* 版权声明：贤俊工作室 版权所有 违者必究
* 日    期：2012-12-17
*/
package com.willow.platform.module.basic.website.dao;

import com.willow.platform.core.base.dao.BaseDao;
import com.willow.platform.module.basic.website.domain.WebSite;
import com.willow.platform.module.basic.website.mapper.WebSiteMapper;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *   网站配置持久层
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.0
 */
@Repository
public class WebSiteDao extends BaseDao<WebSite> {
    @Override
    public Class getMapperClass() {
    return WebSiteMapper.class;
    }
}
