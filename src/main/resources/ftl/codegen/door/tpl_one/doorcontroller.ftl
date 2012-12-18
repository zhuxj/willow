/**
* 版权声明：${codeGenConfig.developerConfig.company!} 版权所有 违者必究
* 日    期：${date!}
*/
package ${codeGenConfig.table.packageVar!}.web;

import com.willow.platform.core.Page;
import com.willow.platform.core.PageParam;
import com.willow.platform.core.base.web.BaseController;
import com.willow.platform.core.context.WebSiteContext;
import ${codeGenConfig.table.packageVar!}.domain.${codeGenConfig.table.classVar!};
import ${codeGenConfig.table.packageVar!}.service.${codeGenConfig.table.classVar!}Service;
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
 * ${codeGenConfig.table.tableName!}控制层
 * </pre>
*
* @author ${codeGenConfig.developerConfig.developer!}
* @version ${codeGenConfig.developerConfig.version!}
*/
@Controller
@RequestMapping(WebSiteContext.MANAGER + "admin/${codeGenConfig.table.simplePackageVar!}/")
public class ${codeGenConfig.table.classVar!}Controller extends BaseController {
@Autowired
private ${codeGenConfig.table.classVar!}Service ${codeGenConfig.table.classVariable!}Service;

    /**
     * 显示新增页面
     *
     * @param ${codeGenConfig.table.classVariable!}
     * @return
     */
    @RequestMapping("/addPage")
    public ModelAndView addPage(${codeGenConfig.table.classVar!} ${codeGenConfig.table.classVariable!}) {
        ModelAndView view = new ModelAndView("${codeGenConfig.table.jspDir!}/add");
        return view;
    }

    /**
     * 保存记录
     *
     * @param ${codeGenConfig.table.classVariable!}
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(${codeGenConfig.table.classVar!} ${codeGenConfig.table.classVariable!}) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = ${codeGenConfig.table.classVariable!}Service.save(${codeGenConfig.table.classVariable!});
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
        int affectCount = ${codeGenConfig.table.classVariable!}Service.deleteByObjId(objId);
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
        ${codeGenConfig.table.classVariable!}Service.deleteByObjIds(objIdArr);
        map.put("success", "1");
        return map;
    }


    /**
     * 显示修改页面
     *
     * @param ${codeGenConfig.table.classVariable!}
     * @return
     */
    @RequestMapping("/updatePage")
    public ModelAndView updatePage(${codeGenConfig.table.classVar!} ${codeGenConfig.table.classVariable!}) {
        ModelAndView view = new ModelAndView("${codeGenConfig.table.jspDir!}/update");
        ${codeGenConfig.table.classVar!} domain = ${codeGenConfig.table.classVariable!}Service.selectByObjId(${codeGenConfig.table.classVariable!}.getObjId());
        view.addObject("${codeGenConfig.table.classVariable!}", domain);
        return view;
    }


    /**
     * 更新记录
     *
     * @param ${codeGenConfig.table.classVariable!}
     * @return
     */
    @RequestMapping("/update")
    public Map<String, Object> update(${codeGenConfig.table.classVar!} ${codeGenConfig.table.classVariable!}) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = ${codeGenConfig.table.classVariable!}Service.update(${codeGenConfig.table.classVariable!});
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
     * @param ${codeGenConfig.table.classVariable!}
     * @return
     */
    @RequestMapping("/updateByIdSelective")
    public Map<String, Object> updateByIdSelective(${codeGenConfig.table.classVar!} ${codeGenConfig.table.classVariable!}) {
        Map<String, Object> map = new HashMap<String, Object>();
        int affectCount = ${codeGenConfig.table.classVariable!}Service.updateByIdSelective(${codeGenConfig.table.classVariable!});
        return map;
    }


    /**
     * 显示查询页面
     *
     * @param ${codeGenConfig.table.classVariable!}
     * @return
     */
    @RequestMapping("/listPage")
    public ModelAndView listPage(${codeGenConfig.table.classVar!} ${codeGenConfig.table.classVariable!}) {
        ModelAndView view = new ModelAndView("${codeGenConfig.table.jspDir!}/list");
        return view;
    }

    /**
     * 查询记录
     *
     * @param  ${codeGenConfig.table.classVariable!}
     * @param pageParam
     * @return
     */
    @RequestMapping("/query")
    public Map<String, Object> query(${codeGenConfig.table.classVar!} ${codeGenConfig.table.classVariable!}, PageParam pageParam) {
        Page<${codeGenConfig.table.classVar!}> page = ${codeGenConfig.table.classVariable!}Service.queryPageList(${codeGenConfig.table.classVariable!}, pageParam);
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
        ModelAndView view = new ModelAndView("${codeGenConfig.table.jspDir!}/detail");
        ${codeGenConfig.table.classVar!} ${codeGenConfig.table.classVariable!} = ${codeGenConfig.table.classVariable!}Service.selectByObjId(objId);
        view.addObject("${codeGenConfig.table.classVariable!}", ${codeGenConfig.table.classVariable!});
        return view;
    }


}
