/**
 * @(#)com.willow.util.BeanCopierUtils
 * 2010-3-8
 * Copyright 2010 
 * 软件公司, 版权所有 违者必究
 */
package com.willow.platform.utils;

import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 朱贤俊
 * @version 1.0
 */
 public abstract class BeanCopierUtils {

    private static final Map<ClassPair,BeanCopier> BEAN_COPIERS = new HashMap<ClassPair,BeanCopier>();

    /**
     * 根据要进行值复制的源类和目标类获取BeanCopier对象实例
     *
     * @param fromClass
     * @param toClass
     * @return
     */
    public static BeanCopier getBeanCopierInstance(Class fromClass,Class toClass){
         ClassPair classPair = ClassPair.getInstance(fromClass, toClass);
         if(!BEAN_COPIERS.containsKey(classPair)){
            BEAN_COPIERS.put(classPair,BeanCopier.create(classPair.getFromClass(), classPair.getToClass(), false));
         }
         return BEAN_COPIERS.get(classPair);
    }

    /**
     * 要进行复制属性操作的源类对象和目标类对象
     */
    private static class ClassPair {
        private Class fromClass;
        private Class toClass;

        private ClassPair(Class fromClass, Class toClass) {
            this.fromClass = fromClass;
            this.toClass = toClass;
        }

        /**
         * 根据源类对象和目标类对象生成
         *
         * @param fromClass
         * @param toClass
         * @return
         */
        public static ClassPair getInstance(Class fromClass, Class toClass) {
            return new ClassPair(fromClass, toClass);
        }

        public Class getFromClass() {
            return fromClass;
        }

        public void setFromClass(Class fromClass) {
            this.fromClass = fromClass;
        }

        public Class getToClass() {
            return toClass;
        }

        public void setToClass(Class toClass) {
            this.toClass = toClass;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).
                    append(this.fromClass).
                    append(this.toClass).
                    toHashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }
            ClassPair classPair = (ClassPair) obj;
            return new EqualsBuilder()
                    .append(fromClass, classPair.getFromClass())
                    .append(toClass, classPair.getToClass())
                    .isEquals();
        }
    }
}
