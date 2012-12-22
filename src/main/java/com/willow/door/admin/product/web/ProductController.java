/**
 * 版权声明：贤俊工作室 版权所有 违者必究
 * 日    期：2012-12-21
 */
package com.willow.door.admin.product.web;

import com.willow.door.admin.productcatalog.domain.ProductCatalog;
import com.willow.door.admin.productcatalog.service.ProductCatalogService;
import com.willow.platform.core.Page;
import com.willow.platform.core.PageParam;
import com.willow.platform.core.base.web.BaseController;
import com.willow.platform.core.context.WebSiteContext;
import com.willow.door.admin.product.domain.Product;
import com.willow.door.admin.product.service.ProductService;
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
 * 产品信息控制层
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.0
 */
@Controller
@RequestMapping(WebSiteContext.MANAGER + "admin/product")
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCatalogService productCatalogService;


    /**
     * 显示新增页面
     *
     * @param product
     * @return
     */
    @RequestMapping("/addPage")
    public ModelAndView addPage(Product product) {
        ModelAndView view = new ModelAndView("/door/admin/product/add");
        ProductCatalog productCatalog = new ProductCatalog();
        List<ProductCatalog> productCatalogs = productCatalogService.queryList(productCatalog);
        view.addObject("productCatalogs", productCatalogs);
        return view;
    }

    /**
     * 保存记录
     *
     * @param product
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(Product product) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = productService.save(product);
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
        int affectCount = productService.deleteByObjId(objId);
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
        productService.deleteByObjIds(objIdArr);
        map.put("success", "1");
        return map;
    }


    /**
     * 显示修改页面
     *
     * @param product
     * @return
     */
    @RequestMapping("/updatePage")
    public ModelAndView updatePage(Product product) {
        ModelAndView view = new ModelAndView("/door/admin/product/update");
        Product domain = productService.selectByObjId(product.getObjId());
        view.addObject("product", domain);
        ProductCatalog productCatalog = new ProductCatalog();
        List<ProductCatalog> productCatalogs = productCatalogService.queryList(productCatalog);
        view.addObject("productCatalogs", productCatalogs);
        return view;
    }


    /**
     * 更新记录
     *
     * @param product
     * @return
     */
    @RequestMapping("/update")
    public Map<String, Object> update(Product product) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = productService.update(product);
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
     * @param product
     * @return
     */
    @RequestMapping("/updateByIdSelective")
    public Map<String, Object> updateByIdSelective(Product product) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = productService.updateByIdSelective(product);
        return map;
    }


    /**
     * 显示查询页面
     *
     * @param product
     * @return
     */
    @RequestMapping("/listPage")
    public ModelAndView listPage(Product product) {
        ModelAndView view = new ModelAndView("/door/admin/product/list");
        ProductCatalog productCatalog = new ProductCatalog();
        List<ProductCatalog> productCatalogs = productCatalogService.queryList(productCatalog);
        view.addObject("productCatalogs", productCatalogs);
        return view;
    }

    /**
     * 查询记录
     *
     * @param product
     * @param pageParam
     * @return
     */
    @RequestMapping("/query")
    public Map<String, Object> query(Product product, PageParam pageParam) {
        Page<Product> page = productService.queryPageList(product, pageParam);
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
        ModelAndView view = new ModelAndView("/door/admin/product/detail");
        Product product = productService.selectByObjId(objId);
        view.addObject("product", product);
        return view;
    }


}
