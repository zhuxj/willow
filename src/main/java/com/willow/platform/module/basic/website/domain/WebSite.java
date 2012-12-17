/**
* 版权声明：贤俊工作室 版权所有 违者必究
* 日    期：2012-12-17
*/
package com.willow.platform.module.basic.website.domain;

import com.willow.platform.core.base.domian.BaseObject;

/**
 * <pre>
 *   网站配置领域对象
 * </pre>
 * @author 朱贤俊
 * @version 1.0
 */
public class WebSite extends BaseObject{
    //网站ID
    private String objId;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;
    //操作人
    private String userId;
    //公司ID
    private String companyId;
    //公司ID
    private String templateId;
    //商城名称
    private String websiteName;
    //商城标题
    private String websiteTitle;
    //商城描述
    private String websiteDesc;
    //商城英文名称
    private String websiteNameEn;
    //商城英文标题
    private String websiteTitleEn;
    //商城英文描述
    private String websiteDescEn;
    //网站Logo图片ID
    private String logoImageId;
    //商城关键字
    private String websiteKeyword;
    //商城英文关键字
    private String websiteKeywordEn;
    //客服邮件地址
    private String serviceEmail;
    //客服电话
    private String serviceTel;
    //客服传真
    private String serviceFax;
    //工作时间
    private String workTime;
    //是否暂时关闭网站
    private String shutDown;
    //ICP备案证书号
    private String websiteIcp;

    public String getObjId() {
        return objId;
    }
    public void setObjId(String objId) {
        this.objId = objId;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getTemplateId() {
        return templateId;
    }
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    public String getWebsiteName() {
        return websiteName;
    }
    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }
    public String getWebsiteTitle() {
        return websiteTitle;
    }
    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }
    public String getWebsiteDesc() {
        return websiteDesc;
    }
    public void setWebsiteDesc(String websiteDesc) {
        this.websiteDesc = websiteDesc;
    }
    public String getWebsiteNameEn() {
        return websiteNameEn;
    }
    public void setWebsiteNameEn(String websiteNameEn) {
        this.websiteNameEn = websiteNameEn;
    }
    public String getWebsiteTitleEn() {
        return websiteTitleEn;
    }
    public void setWebsiteTitleEn(String websiteTitleEn) {
        this.websiteTitleEn = websiteTitleEn;
    }
    public String getWebsiteDescEn() {
        return websiteDescEn;
    }
    public void setWebsiteDescEn(String websiteDescEn) {
        this.websiteDescEn = websiteDescEn;
    }
    public String getLogoImageId() {
        return logoImageId;
    }
    public void setLogoImageId(String logoImageId) {
        this.logoImageId = logoImageId;
    }
    public String getWebsiteKeyword() {
        return websiteKeyword;
    }
    public void setWebsiteKeyword(String websiteKeyword) {
        this.websiteKeyword = websiteKeyword;
    }
    public String getWebsiteKeywordEn() {
        return websiteKeywordEn;
    }
    public void setWebsiteKeywordEn(String websiteKeywordEn) {
        this.websiteKeywordEn = websiteKeywordEn;
    }
    public String getServiceEmail() {
        return serviceEmail;
    }
    public void setServiceEmail(String serviceEmail) {
        this.serviceEmail = serviceEmail;
    }
    public String getServiceTel() {
        return serviceTel;
    }
    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }
    public String getServiceFax() {
        return serviceFax;
    }
    public void setServiceFax(String serviceFax) {
        this.serviceFax = serviceFax;
    }
    public String getWorkTime() {
        return workTime;
    }
    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
    public String getShutDown() {
        return shutDown;
    }
    public void setShutDown(String shutDown) {
        this.shutDown = shutDown;
    }
    public String getWebsiteIcp() {
        return websiteIcp;
    }
    public void setWebsiteIcp(String websiteIcp) {
        this.websiteIcp = websiteIcp;
    }
}
