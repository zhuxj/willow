/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-3
 */
package com.willow.enterprise;

import com.willow.platform.core.base.web.BaseController;
import com.willow.platform.core.context.WebSiteContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 后台管理器
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
@Controller
@RequestMapping(WebSiteContext.MANAGER)
public class ManagerController extends BaseController {

    @RequestMapping("/main")
    public ModelAndView main() {
        ModelAndView view = new ModelAndView("enterprise/mainExt");

        return view;
    }

}
