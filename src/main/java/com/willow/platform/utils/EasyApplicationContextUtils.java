/**
 * 版权所有 违者必究
 * by 朱贤俊
 */
package com.willow.platform.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 在Spring容器启动时，框架会将ApplicationContext的引用设置到EasyApplicationContextUtils中
 * 这样就可以直接使用该工具类从Spring中获取Bean了。
 */
public class EasyApplicationContextUtils {

    private static ApplicationContext ctx;
    private static final Log logger = LogFactory.getLog(EasyApplicationContextUtils.class);

    public static void setApplicationContext(ApplicationContext ctx) {
        EasyApplicationContextUtils.ctx = ctx;
    }

    /**
     * 从Spring容器中获取Bean，其类型为clazz。Spring容器中必须包含且只包含一个该类型的Bean，
     * 否则返回null
     *
     * @param clazz Bean的类型
     * @return Bean实例
     */
    public static <T> T getBeanByType(Class<T> clazz) {
        Assert.notNull(ctx, "必须在初始化Spring容器时设置好ApplicationContext");
        Map map = ctx.getBeansOfType(clazz);
        if (map.size() == 1) {
            Object obj = null;
            for (Object o : map.keySet()) {
                obj = map.get(o);
            }
            return (T) obj;
        } else {
            if (map.size() == 0) {
                throw new IllegalStateException("在Spring容器中没有类型为" + clazz.getName() + "的Bean");
            } else {
                throw new IllegalStateException("在Spring容器中有多个类型为" + clazz.getName() + "的Bean");
            }
        }
    }

    public static <T> List<T> getBeansByType(Class<T> clazz) {
        Assert.notNull(ctx, "必须在初始化Spring容器时设置好ApplicationContext");
        Map map = ctx.getBeansOfType(clazz);
        ArrayList<T> tempList = new ArrayList<T>();
        tempList.addAll(map.values());
        return tempList;
    }

    /**
     * 从Spring容器中获取Bean
     *
     * @param beanName Bean的名称
     * @return Bean
     */
    public static Object getBeanByName(String beanName) {

        if (StringUtils.isEmpty(beanName))
            return null;

        Assert.notNull(ctx, "必须在初始化Spring容器时设置好ApplicationContext");

        try {
            return ctx.getBean(beanName);
        } catch (BeansException e) {
            return null;
        }
    }

    /**
     * 获取Spring容器的引用
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    /**
     * 向应用程序上下文发布一个事件
     *
     * @param event
     */
    public static void publishEvent(ApplicationEvent event) {
        ctx.publishEvent(event);
    }

}
