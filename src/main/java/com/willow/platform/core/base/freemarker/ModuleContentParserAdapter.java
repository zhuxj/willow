/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-2-15
 */
package com.willow.platform.core.base.freemarker;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  店铺模块解析适配器
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public abstract class ModuleContentParserAdapter implements ModuleContentParser {

    @Autowired
    private FtlParser ftlParser;

    public String loadModule(String moduleCode, Map<String, Object> extParamMap) {
        Map<String, Object> map = loadModuleData(moduleCode, extParamMap);
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        map.putAll(extParamMap);
        return ftlParser.formatContent(getModuleFtlBaseName(), map);
    }

}
