/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.enterprise.module.basic.menu.web;

import com.willow.platform.core.Page;
import com.willow.platform.core.PageParam;
import com.willow.platform.core.base.web.BaseController;
import com.willow.platform.core.context.WebSiteContext;
import com.willow.platform.module.basic.menu.domain.SysMenu;
import com.willow.platform.module.basic.menu.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 系统菜单控制层
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
@Controller
@RequestMapping(WebSiteContext.MANAGER + "/SysMenu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 显示新增页面
     *
     * @param sysMenu
     * @return
     */
    @RequestMapping("/addPage")
    public ModelAndView addPage(SysMenu sysMenu) {
        ModelAndView view = new ModelAndView("/enterprise/module/basic/menu/add");
        return view;
    }

    /**
     * 保存记录
     *
     * @param sysMenu
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(SysMenu sysMenu) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = sysMenuService.save(sysMenu);
        return map;
    }


    /**
     * 删除记录
     *
     * @param objId
     * @return
     */
    @RequestMapping("/del")
    public Map<String, Object> del(String objId) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = sysMenuService.deleteByObjId(objId);
        return map;
    }

    /**
     * 批量删除
     *
     * @param objIds
     * @return
     */
    @RequestMapping("/batchDel")
    public Map<String, Object> batchDel(String objIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] objIdArr = StringUtils.split(objIds, ",");
        sysMenuService.deleteByObjIds(objIdArr);
        map.put("success", "1");
        return map;
    }


    /**
     * 显示修改页面
     *
     * @param sysMenu
     * @return
     */
    @RequestMapping("/updatePage")
    public ModelAndView updatePage(SysMenu sysMenu) {
        ModelAndView view = new ModelAndView("/enterprise/module/basic/menu/update");
        return view;
    }


    /**
     * 更新记录
     *
     * @param sysMenu
     * @return
     */
    @RequestMapping("/update")
    public Map<String, Object> update(SysMenu sysMenu) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = sysMenuService.update(sysMenu);
        return map;
    }

    /**
     * 更新记录
     *
     * @param sysMenu
     * @return
     */
    @RequestMapping("/updateByIdSelective")
    public Map<String, Object> updateByIdSelective(SysMenu sysMenu) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = sysMenuService.updateByIdSelective(sysMenu);
        return map;
    }


    /**
     * 显示查询页面
     *
     * @param sysMenu
     * @return
     */
    @RequestMapping("/listPage")
    public ModelAndView listPage(SysMenu sysMenu) {
        ModelAndView view = new ModelAndView("/enterprise/module/basic/menu/list");
        return view;
    }

    /**
     * 查询记录
     *
     * @param sysMenu
     * @param pageParam
     * @return
     */
    @RequestMapping("/query")
    public Map<String, Object> query(SysMenu sysMenu, PageParam pageParam) {
        Page<SysMenu> page = sysMenuService.queryPageList(sysMenu, pageParam);
        return page.toDataMap(null);
    }

    /**
     * 显示详情页面
     *
     * @param objId
     * @return
     */
    @RequestMapping("/detailPage")
    public ModelAndView detailPage(String objId) {
        ModelAndView view = new ModelAndView("/enterprise/module/basic/menu/detail");
        SysMenu sysMenu = sysMenuService.selectByObjId(objId);
        view.addObject("sysMenu", sysMenu);
        return view;
    }


}
