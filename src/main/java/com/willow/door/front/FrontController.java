/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-22
 */
package com.willow.door.front;

import com.willow.platform.core.AppConst;
import com.willow.platform.core.base.web.BaseController;
import com.willow.platform.module.basic.website.domain.WebSite;
import com.willow.platform.module.basic.website.service.WebSiteService;
import com.willow.platform.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 前台页面
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
@Controller
public class FrontController extends BaseController {
    @Autowired
    private WebSiteService webSiteService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/index")
    public ResponseEntity<String> index(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("webSite", getWebSite());
        map.put("request", request);
        String template = "template_01";
        map.put("extMap", map);
        return makeResponseEntity(formatContent("/door/front/" + template + "/index", map));
    }

    private WebSite getWebSite() {
        WebSite webSite = webSiteService.getWebSiteByCompanyId(AppConst.COMPANY_ID);
        return webSite;
    }


}
