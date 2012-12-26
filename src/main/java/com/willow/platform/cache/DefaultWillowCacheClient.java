/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-24
 */
package com.willow.platform.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * <pre>
 *  默认的缓存客户端
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class DefaultWillowCacheClient implements WillowCacheClient {
    private Cache ehCache;

    public DefaultWillowCacheClient(Cache ehCache) {
        this.ehCache = ehCache;
    }

    @Override
    public void put(String key, Cacheable value) {
        value.setVersion(value.getVersion() + 1);
        value.setCacheUpdateTime(getCurrTime());
        putToLocal(key, value);
    }

    /**
     * 将数据放到本地缓存中
     *
     * @param key
     * @param value
     */
    private void putToLocal(String key, Cacheable value) {
        Element element = new Element(key, value, false,
                value.getLocalCacheTimeToLive(), value.getLocalCacheTimeToLive());
        this.ehCache.put(element);
    }

    @Override
    public Cacheable get(String key) {
        Cacheable value = getFromLocal(key);
        return value;
    }

    /**
     * 从本地缓存中获取缓存对象，如果没有返回空
     *
     * @param key
     * @return
     */
    private Cacheable getFromLocal(String key) {
        Element element = this.ehCache.get(key);
        Cacheable cacheable = null;
        if (element != null && !element.isExpired()) {
            cacheable = (Cacheable) element.getValue();
        }
        return cacheable;
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    @Override
    public boolean remove(String key) {
        return removeFromLocal(key);
    }

    private boolean removeFromLocal(String key) {
        return this.ehCache.remove(key);
    }


    private long getCurrTime() {
        return System.currentTimeMillis();
    }


}
