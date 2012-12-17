/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-17
 */
package com.willow.door.admin.website.web;

import com.willow.platform.core.AppConst;
import com.willow.platform.core.base.web.BaseController;
import com.willow.platform.core.context.WebSiteContext;
import com.willow.platform.module.basic.website.domain.WebSite;
import com.willow.platform.module.basic.website.service.WebSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
@Controller
@RequestMapping(WebSiteContext.MANAGER + "admin/website")
public class WebSiteController extends BaseController {
    @Autowired
    private WebSiteService webSiteService;


    /**
     * 编辑网站参数设置
     *
     * @orderId
     */
    @RequestMapping(value = "/webSiteEdit")
    public void webSiteEdit(WebSite webSiteVo) {
        String companyId = AppConst.COMPANY_ID;
        webSiteVo.setCompanyId(companyId);
        webSiteVo.setTemplateId(companyId);
        WebSite webSite = webSiteService.getWebSiteByCompanyId(companyId);
        webSite = this.fromVo2Po(webSite, webSiteVo);
        webSiteService.saveOrUpdateWebSite(webSite, companyId);
    }

    /**
     * 显示网站参数设置编辑页面
     *
     * @orderId
     */
    @RequestMapping(value = "/showWebSiteEdit")
    public String showWebSiteEdit(ModelMap modelMap) {
        String companyId = AppConst.COMPANY_ID;
        WebSite webSite = webSiteService.getWebSiteByCompanyId(companyId);
        if (webSite == null) {
            modelMap.put("webSite", webSite);
            return "/door/admin/website/webSiteEdit";
        }
        webSite.setCompanyId(companyId);
        String workTime = webSite.getWorkTime();
        if (workTime != null) {
            webSite.setWorkTime(workTime.replace("<br/>", "\n"));
        }
        modelMap.put("webSite", webSite);
        return "/door/admin/website/webSiteEdit";

    }


    private WebSite fromVo2Po(WebSite webSite, WebSite webSiteVo) {
        if (webSite == null) {
            webSite = new WebSite();
        }
        webSite.setTemplateId(webSiteVo.getTemplateId());

        webSite.setWebsiteName(webSiteVo.getWebsiteName());
        webSite.setWebsiteTitle(webSiteVo.getWebsiteTitle());
        webSite.setWebsiteDesc(webSiteVo.getWebsiteDesc());
        webSite.setWebsiteKeyword(webSiteVo.getWebsiteKeyword());

        webSite.setWebsiteNameEn(webSiteVo.getWebsiteNameEn());
        webSite.setWebsiteTitleEn(webSiteVo.getWebsiteTitleEn());
        webSite.setWebsiteDescEn(webSiteVo.getWebsiteDescEn());
        webSite.setWebsiteKeywordEn(webSiteVo.getWebsiteKeywordEn());
        webSite.setLogoImageId(webSiteVo.getLogoImageId());

        webSite.setShutDown(webSiteVo.getShutDown());

        webSite.setWebsiteIcp(webSiteVo.getWebsiteIcp());
        webSite.setServiceEmail(webSiteVo.getServiceEmail());
        webSite.setServiceFax(webSiteVo.getServiceFax());
        webSite.setServiceTel(webSiteVo.getServiceTel());
        if (webSiteVo.getWorkTime() != null) {
            webSite.setWorkTime(webSiteVo.getWorkTime().replace("\n", "<br/>"));
        }


        return webSite;
    }


}
