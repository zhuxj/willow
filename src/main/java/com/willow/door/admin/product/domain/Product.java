/**
* 版权声明：贤俊工作室 版权所有 违者必究
* 日    期：2012-12-21
*/
package com.willow.door.admin.product.domain;

import com.willow.platform.core.base.domian.BaseObject;

/**
 * <pre>
 *   产品信息领域对象
 * </pre>
 * @author 朱贤俊
 * @version 1.0
 */
public class Product extends BaseObject{
    //产品编号
    private String productSn;
    //产品中文名称
    private String productName;
    //产品英文名称
    private String productNameEn;
    //产品中文规格
    private String productNorms;
    //产品英文规格
    private String productNormsEn;
    //产品型号
    private String productVersion;
    //产品分类
    private String catalogId;
    //产品中文详情
    private String productDesc;
    //产品英文详情
    private String productDescEn;
    //产品图片
    private String productImage;
    //浏览次数
    private Integer browseTime;

    public String getProductSn() {
        return productSn;
    }
    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductNameEn() {
        return productNameEn;
    }
    public void setProductNameEn(String productNameEn) {
        this.productNameEn = productNameEn;
    }
    public String getProductNorms() {
        return productNorms;
    }
    public void setProductNorms(String productNorms) {
        this.productNorms = productNorms;
    }
    public String getProductNormsEn() {
        return productNormsEn;
    }
    public void setProductNormsEn(String productNormsEn) {
        this.productNormsEn = productNormsEn;
    }
    public String getProductVersion() {
        return productVersion;
    }
    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }
    public String getCatalogId() {
        return catalogId;
    }
    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
    public String getProductDesc() {
        return productDesc;
    }
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
    public String getProductDescEn() {
        return productDescEn;
    }
    public void setProductDescEn(String productDescEn) {
        this.productDescEn = productDescEn;
    }
    public String getProductImage() {
        return productImage;
    }
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    public Integer getBrowseTime() {
        return browseTime;
    }
    public void setBrowseTime(Integer browseTime) {
        this.browseTime = browseTime;
    }
}
