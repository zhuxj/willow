/**
* 版权声明：贤俊工作室 版权所有 违者必究
* 日    期：2012-12-20
*/
package com.willow.door.admin.productcatalog.domain;

import com.willow.platform.core.base.domian.BaseObject;

/**
 * <pre>
 *   产品分类领域对象
 * </pre>
 * @author 朱贤俊
 * @version 1.0
 */
public class ProductCatalog extends BaseObject{
    //分类名称
    private String catalogName;
    //分类英文名称
    private String catalogNameEn;

    public String getCatalogName() {
        return catalogName;
    }
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }
    public String getCatalogNameEn() {
        return catalogNameEn;
    }
    public void setCatalogNameEn(String catalogNameEn) {
        this.catalogNameEn = catalogNameEn;
    }
}
