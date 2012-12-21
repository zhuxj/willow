/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.door.admin.sysuser.web;

import com.willow.platform.core.Page;
import com.willow.platform.core.PageParam;
import com.willow.platform.core.base.web.BaseController;
import com.willow.platform.core.context.WebSiteContext;
import com.willow.platform.module.basic.sysuser.domain.SysUser;
import com.willow.platform.module.basic.sysuser.service.SysUserService;
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
@RequestMapping(WebSiteContext.MANAGER + "admin/sysuser")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 显示新增页面
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/addPage")
    public ModelAndView addPage(SysUser sysUser) {
        ModelAndView view = new ModelAndView("/door/admin/sysuser/add");
        return view;
    }

    /**
     * 保存记录
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(SysUser sysUser) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = sysUserService.save(sysUser);
        if (affectCount > 0) {
            map.put("success", "1");
        } else {
            map.put("success", "0");
        }
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
        int affectCount = sysUserService.deleteByObjId(objId);
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
        sysUserService.deleteByObjIds(objIdArr);
        map.put("success", "1");
        return map;
    }


    /**
     * 显示修改页面
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/updatePage")
    public ModelAndView updatePage(SysUser sysUser) {
        ModelAndView view = new ModelAndView("/door/admin/sysuser/update");
        SysUser user = sysUserService.selectByObjId(sysUser.getObjId());
        view.addObject("user", user);
        return view;
    }


    /**
     * 更新记录
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/update")
    public Map<String, Object> update(SysUser sysUser) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = sysUserService.update(sysUser);
        if (affectCount > 0) {
            map.put("success", "1");
        } else {
            map.put("success", "0");
        }
        return map;
    }

    /**
     * 更新记录
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/updateByIdSelective")
    public Map<String, Object> updateByIdSelective(SysUser sysUser) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = sysUserService.updateByIdSelective(sysUser);
        return map;
    }


    /**
     * 显示查询页面
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/listPage")
    public ModelAndView listPage(SysUser sysUser) {
        ModelAndView view = new ModelAndView("/door/admin/sysuser/list");
        return view;
    }

    /**
     * 查询记录
     *
     * @param sysUser
     * @param pageParam
     * @return
     */
    @RequestMapping("/query")
    public Map<String, Object> query(SysUser sysUser, PageParam pageParam) {
        Page<SysUser> page = sysUserService.queryPageList(sysUser, pageParam);
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
        ModelAndView view = new ModelAndView("/door/admin/sysuser/detail");
        SysUser sysUser = sysUserService.selectByObjId(objId);
        view.addObject("user", sysUser);
        return view;
    }


}
