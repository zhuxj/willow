/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-24
 */
package com.willow.platform.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.util.Assert;

/**
 * <pre>
 *  缓存客户端构造器
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class WillowCacheClientBuilder {
    private static final String LOCAL_CACHE_NAME = "LOCAL_CACHE#WILLOW";

    //本地缓存服务器                                                                                                                   s
    private CacheManager cacheManager;

    public WillowCacheClientBuilder(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        buildLocalCache();
    }


    private void buildLocalCache() {
        Assert.notNull(this.cacheManager, "本地缓存管理服务器不能为null");
        Cache cache = new Cache(LOCAL_CACHE_NAME, 50000, true, false, 0L, 0L);
        cacheManager.addCache(cache);
    }

    /**
     * 获取本地缓存
     *
     * @return
     */
    private Cache getLocalCache() {
        return this.cacheManager.getCache(LOCAL_CACHE_NAME);
    }

    /**
     * 构造缓存客户端
     *
     * @return
     */
    public WillowCacheClient build() {
        return new DefaultWillowCacheClient(getLocalCache());
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
