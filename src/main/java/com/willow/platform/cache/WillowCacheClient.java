/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-24
 */
package com.willow.platform.cache;

/**
 * <pre>
 *  缓存客户端接口
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public interface WillowCacheClient {
    /**
     * 添加到本地缓存和远程缓存中,本地和远程的默认存活时间分别由实现者决定.
     *
     * @param key
     * @param value
     */
    void put(String key, Cacheable value);

    /**
     * 先从本地缓存获取,本地缓存不存在,再从远程缓存获取
     *
     * @param key
     * @return
     */
    Cacheable get(String key);


    /**
     * 删除本地缓存和远程缓存中的数据
     *
     * @param key
     */
    boolean remove(String key);

}
