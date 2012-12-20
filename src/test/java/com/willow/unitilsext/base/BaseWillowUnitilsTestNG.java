/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2011 
 * 日    期：11-10-10
 */
package com.willow.unitilsext.base;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceEditor;
import org.springframework.core.io.support.ResourceArrayPropertyEditor;
import org.unitils.UnitilsTestNG;
import org.unitils.database.DatabaseUnitils;

import javax.sql.DataSource;

/**
 * <pre>
 *   基于mybatis的数据库单元测试,测试能不依赖容器就不依赖 ，否则测试速度非常慢
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.82
 */
public class BaseWillowUnitilsTestNG extends UnitilsTestNG {
    /**
     * 构造SqlSessionFactory
     *
     * @return SqlSessionFactory
     */
    protected SqlSessionFactory buildSqlSessionFactory() {
        try {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            DataSource dataSource = DatabaseUnitils.getDataSource();
            sqlSessionFactoryBean.setDataSource(dataSource);
            ResourceEditor resourceEditor = new ResourceEditor();
            resourceEditor.setAsText("classpath:conf/mybatis/mybatisConfig.xml");
            Resource config = (Resource) resourceEditor.getValue();
            sqlSessionFactoryBean.setConfigLocation(config);

            ResourceArrayPropertyEditor rapEditor = new ResourceArrayPropertyEditor();
            rapEditor.setAsText("classpath*:/com/willow/**/*.mapper*.xml");
            Resource[] resources = (Resource[]) rapEditor.getValue();
            sqlSessionFactoryBean.setMapperLocations(resources);

            sqlSessionFactoryBean.afterPropertiesSet();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
            return sqlSessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("初始化SqlSessionFactory发生异常", e);
        }
    }

    /**
     * 获取SqlSessionTemplate
     *
     * @return SqlSessionTemplate
     */
    protected SqlSessionTemplate getSqlSessionTemplate() {
        SqlSessionFactory sqlSessionFactory = this.buildSqlSessionFactory();
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }

    /**
     * 获取mapper实例
     *
     * @param mapperClazz
     * @param <T>
     * @return
     */
    protected <T> T getMapper(Class<T> mapperClazz) {
        return (T) getSqlSessionTemplate().getMapper(mapperClazz);
    }


}
