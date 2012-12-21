/**
* 版权声明：贤俊工作室 版权所有 违者必究
* 日    期：2012-12-21
*/
package com.willow.door.admin.product.service;

import com.willow.platform.core.base.dao.BaseDao;
import com.willow.platform.core.base.service.BaseService;
import com.willow.door.admin.product.dao.ProductDao;
import com.willow.door.admin.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
<pre>
 * 产品信息业务类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.0
 */
@Service
public class ProductService extends BaseService<Product> {
    @Autowired
    private ProductDao productDao;
    @Override
    public BaseDao<Product> getDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
