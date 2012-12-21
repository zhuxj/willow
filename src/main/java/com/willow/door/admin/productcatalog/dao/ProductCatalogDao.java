/**
 * 版权声明：贤俊工作室 版权所有 违者必究
 * 日    期：2012-12-18
 */
package com.willow.door.admin.productcatalog.dao;

import com.willow.platform.core.base.dao.BaseDao;
import com.willow.door.admin.productcatalog.domain.ProductCatalog;
import com.willow.door.admin.productcatalog.mapper.ProductCatalogMapper;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *   产品分类持久层
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.0
 */
@Repository
public class ProductCatalogDao extends BaseDao<ProductCatalog> {
    @Override
    public Class getMapperClass() {
        return ProductCatalogMapper.class;
    }
}
