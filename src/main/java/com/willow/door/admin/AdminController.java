/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.door.admin;

import com.willow.platform.core.base.web.BaseController;
import com.willow.platform.core.context.WebSiteContext;
import com.willow.platform.module.basic.menu.domain.SysMenu;
import com.willow.platform.module.basic.menu.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
@RequestMapping(WebSiteContext.MANAGER + "admin")
@Controller
public class AdminController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/index")
    public String shopAdminIndex(ModelMap modelMap) {
        return "/door/admin/index";
    }

    /**
     * 解决当输入http://xxx/xxxadmin也能进入后台管理页面
     *
     * @return
     */
    @RequestMapping("")
    public String shopAdminIndex1(ModelMap modelMap) {
        return "/door/admin/index";
    }

    @RequestMapping("/queryMenu")
    public Map<String, Object> queryMenu(String menuCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<SysMenu> sysMenus = sysMenuService.querySysMenusByParentMenuId(menuCode);
        map.put("data", sysMenus);
        return map;
    }
}
