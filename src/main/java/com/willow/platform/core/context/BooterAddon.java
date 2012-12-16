/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-11
 */
package com.willow.platform.core.context;

/**
 * <pre>
 *  在系统启动前执行的处理器。如果有顺序的要求，实现者还可以实现{@link com.willow.platform.core.Orderable}接口，指定顺序。
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public interface BooterAddon {
    /**
     * 在系统初始化时执行该方法
     */
    void init();

    /**
     * 在应用服务器停止前，执行销毁操作
     */
    void destory();

    /**
     * 系统初始化插件名称
     *
     * @return
     */
    String getName();
}
