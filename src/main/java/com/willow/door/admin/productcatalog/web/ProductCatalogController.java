/**
* 版权声明：贤俊工作室 版权所有 违者必究
* 日    期：2012-12-21
*/
package com.willow.door.admin.productcatalog.web;

import com.willow.platform.core.Page;
import com.willow.platform.core.PageParam;
import com.willow.platform.core.base.web.BaseController;
import com.willow.platform.core.context.WebSiteContext;
import com.willow.door.admin.productcatalog.domain.ProductCatalog;
import com.willow.door.admin.productcatalog.service.ProductCatalogService;
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
 * 产品分类控制层
 * </pre>
*
* @author 朱贤俊
* @version 1.0
*/
@Controller
@RequestMapping(WebSiteContext.MANAGER + "admin/productcatalog")
public class ProductCatalogController extends BaseController {
@Autowired
private ProductCatalogService productCatalogService;

    /**
     * 显示新增页面
     *
     * @param productCatalog
     * @return
     */
    @RequestMapping("/addPage")
    public ModelAndView addPage(ProductCatalog productCatalog) {
        ModelAndView view = new ModelAndView("/door/admin/productcatalog/add");
        return view;
    }

    /**
     * 保存记录
     *
     * @param productCatalog
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(ProductCatalog productCatalog) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = productCatalogService.save(productCatalog);
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
        int affectCount = productCatalogService.deleteByObjId(objId);
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
        productCatalogService.deleteByObjIds(objIdArr);
        map.put("success", "1");
        return map;
    }


    /**
     * 显示修改页面
     *
     * @param productCatalog
     * @return
     */
    @RequestMapping("/updatePage")
    public ModelAndView updatePage(ProductCatalog productCatalog) {
        ModelAndView view = new ModelAndView("/door/admin/productcatalog/update");
        ProductCatalog domain = productCatalogService.selectByObjId(productCatalog.getObjId());
        view.addObject("productCatalog", domain);
        return view;
    }


    /**
     * 更新记录
     *
     * @param productCatalog
     * @return
     */
    @RequestMapping("/update")
    public Map<String, Object> update(ProductCatalog productCatalog) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = productCatalogService.update(productCatalog);
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
     * @param productCatalog
     * @return
     */
    @RequestMapping("/updateByIdSelective")
    public Map<String, Object> updateByIdSelective(ProductCatalog productCatalog) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = productCatalogService.updateByIdSelective(productCatalog);
        return map;
    }


    /**
     * 显示查询页面
     *
     * @param productCatalog
     * @return
     */
    @RequestMapping("/listPage")
    public ModelAndView listPage(ProductCatalog productCatalog) {
        ModelAndView view = new ModelAndView("/door/admin/productcatalog/list");
        return view;
    }

    /**
     * 查询记录
     *
     * @param  productCatalog
     * @param pageParam
     * @return
     */
    @RequestMapping("/query")
    public Map<String, Object> query(ProductCatalog productCatalog, PageParam pageParam) {
        Page<ProductCatalog> page = productCatalogService.queryPageList(productCatalog, pageParam);
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
        ModelAndView view = new ModelAndView("/door/admin/productcatalog/detail");
        ProductCatalog productCatalog = productCatalogService.selectByObjId(objId);
        view.addObject("productCatalog", productCatalog);
        return view;
    }


}
