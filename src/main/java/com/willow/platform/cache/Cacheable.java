/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 * 日    期：12-12-24
 */
package com.willow.platform.cache;

import java.io.Serializable;

/**
 * <pre>
 *    缓存对象必须实现的接口
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.0
 */
public interface Cacheable<T> extends Serializable {
    /**
     * 默认本地缓存项的存活时间,单位为秒 （15秒）
     */
    int LOCAL_CACHE_DEFAUL_TIME_TO_LIVE = 15;

    /**
     * 默认远程缓存项的存活时间,单位为秒 （一个月）
     */
    int REMOTE_CACHE_DEFAUL_TIME_TO_LIVE = 30 * 24 * 3600;

    /**
     * 获取缓存的对象
     *
     * @return
     */
    T getValue();

    /**
     * 设置缓存的对象
     *
     * @param value
     */
    void setValue(T value);


    /**
     * 设置版本号
     *
     * @return
     */
    void setVersion(int version);

    /**
     * 获取版本号
     *
     * @return
     */
    int getVersion();

    /**
     * 在本地缓存中的存活时间，单位为秒，负数或0表示永不过期
     *
     * @return
     */
    int getLocalCacheTimeToLive();

    /**
     * 在远程缓存中的存活时间，单位为秒，负数或0表示永不过期
     *
     * @return
     */
    int getRemoteCacheTimeToLive();

    /**
     * 获取缓存更改时间，单位为毫秒
     *
     * @return
     */
    long getCacheUpdateTime();

    /**
     * 设置缓存更新时间
     *
     * @param cacheUpdateTime 缓存更改时间，单位为毫秒
     */
    void setCacheUpdateTime(long cacheUpdateTime);

}
