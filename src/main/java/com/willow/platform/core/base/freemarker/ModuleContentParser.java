/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-2-15
 */
package com.willow.platform.core.base.freemarker;


import java.util.Map;

/**
 * <pre>
 * 店铺模块解析接口
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public interface ModuleContentParser {
    public String loadModule(String moduleCode, Map<String, Object> extParamMap);

    public Map<String, Object> loadModuleData(String moduleCod, Map<String, Object> extParamMap);

    public String getModuleCode();

    public String getModuleFtlBaseName();


}
