/**
* 版权声明：贤俊工作室 版权所有 违者必究
* 日    期：2012-12-21
*/
package com.willow.door.admin.doorarticle.web;

import com.willow.platform.core.Page;
import com.willow.platform.core.PageParam;
import com.willow.platform.core.base.web.BaseController;
import com.willow.platform.core.context.WebSiteContext;
import com.willow.door.admin.doorarticle.domain.DoorArticle;
import com.willow.door.admin.doorarticle.service.DoorArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*
<pre>
 * 产品信息控制层
 * </pre>
*
* @author 朱贤俊
* @version 1.0
*/
@Controller
@RequestMapping(WebSiteContext.MANAGER + "admin/doorarticle")
public class DoorArticleController extends BaseController {
@Autowired
private DoorArticleService doorArticleService;

    /**
     * 显示新增页面
     *
     * @param doorArticle
     * @return
     */
    @RequestMapping("/addPage")
    public ModelAndView addPage(DoorArticle doorArticle) {
        ModelAndView view = new ModelAndView("/door/admin/doorarticle/add");
        return view;
    }

    /**
     * 保存记录
     *
     * @param doorArticle
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(DoorArticle doorArticle) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = doorArticleService.save(doorArticle);
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
        int affectCount = doorArticleService.deleteByObjId(objId);
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
        doorArticleService.deleteByObjIds(objIdArr);
        map.put("success", "1");
        return map;
    }


    /**
     * 显示修改页面
     *
     * @param doorArticle
     * @return
     */
    @RequestMapping("/updatePage")
    public ModelAndView updatePage(DoorArticle doorArticle) {
        ModelAndView view = new ModelAndView("/door/admin/doorarticle/update");
        DoorArticle domain = doorArticleService.selectByObjId(doorArticle.getObjId());
        view.addObject("doorArticle", domain);
        return view;
    }


    /**
     * 更新记录
     *
     * @param doorArticle
     * @return
     */
    @RequestMapping("/update")
    public Map<String, Object> update(DoorArticle doorArticle) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = doorArticleService.update(doorArticle);
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
     * @param doorArticle
     * @return
     */
    @RequestMapping("/updateByIdSelective")
    public Map<String, Object> updateByIdSelective(DoorArticle doorArticle) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = doorArticleService.updateByIdSelective(doorArticle);
        return map;
    }


    /**
     * 显示查询页面
     *
     * @param doorArticle
     * @return
     */
    @RequestMapping("/listPage")
    public ModelAndView listPage(DoorArticle doorArticle) {
        ModelAndView view = new ModelAndView("/door/admin/doorarticle/list");
        return view;
    }

    /**
     * 查询记录
     *
     * @param  doorArticle
     * @param pageParam
     * @return
     */
    @RequestMapping("/query")
    public Map<String, Object> query(DoorArticle doorArticle, PageParam pageParam) {
        Page<DoorArticle> page = doorArticleService.queryPageList(doorArticle, pageParam);
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
        ModelAndView view = new ModelAndView("/door/admin/doorarticle/detail");
        DoorArticle doorArticle = doorArticleService.selectByObjId(objId);
        view.addObject("doorArticle", doorArticle);
        return view;
    }


}
