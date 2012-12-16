/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-3
 */
package com.willow.platform.core.context;

import com.willow.platform.utils.EasyApplicationContextUtils;

/**
 * <pre>
 *  系统上下文
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class WebSiteContext {
    public static final String RESOURCE = "/resources";
    private String version = "1.0";
    public static final String MANAGER = "";

    public static WebSiteContext ctx() {
        return (WebSiteContext) EasyApplicationContextUtils.getBeanByName("webSiteContext");
    }

    /**
     * 获取资源逻辑路径
     *
     * @return
     */
    public String getResourceRoot() {
        return RESOURCE + "-" + getVersion();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
