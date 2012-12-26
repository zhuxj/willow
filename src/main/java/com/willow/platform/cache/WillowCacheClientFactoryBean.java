/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-24
 */
package com.willow.platform.cache;

import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * <pre>
 *  缓存客户端工厂类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class WillowCacheClientFactoryBean extends AbstractFactoryBean<WillowCacheClient> {
    /**
     * ehcache缓存管理器
     */
    private CacheManager ehCacheManager;

    @Override
    public Class<?> getObjectType() {
        return WillowCacheClient.class;
    }

    @Override
    protected WillowCacheClient createInstance() throws Exception {
        return new WillowCacheClientBuilder(ehCacheManager).build();
    }

    public CacheManager getEhCacheManager() {
        return ehCacheManager;
    }

    public void setEhCacheManager(CacheManager ehCacheManager) {
        this.ehCacheManager = ehCacheManager;
    }
}
