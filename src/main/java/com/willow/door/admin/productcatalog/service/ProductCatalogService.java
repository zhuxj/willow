/**
* 版权声明：贤俊工作室 版权所有 违者必究
* 日    期：2012-12-18
*/
package com.willow.door.admin.productcatalog.service;

import com.willow.platform.core.base.dao.BaseDao;
import com.willow.platform.core.base.service.BaseService;
import com.willow.door.admin.productcatalog.dao.ProductCatalogDao;
import com.willow.door.admin.productcatalog.domain.ProductCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
<pre>
 * 产品分类业务类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.0
 */
@Service
public class ProductCatalogService extends BaseService<ProductCatalog> {
    @Autowired
    private ProductCatalogDao productCatalogDao;
    @Override
    public BaseDao<ProductCatalog> getDao() {
        return productCatalogDao;
    }

    public void setProductCatalogDao(ProductCatalogDao productCatalogDao) {
        this.productCatalogDao = productCatalogDao;
    }
}
