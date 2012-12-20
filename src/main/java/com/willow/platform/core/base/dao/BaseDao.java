/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2011 
 * 日    期：11-9-14
 */
package com.willow.platform.core.base.dao;

import com.willow.platform.core.Page;
import com.willow.platform.core.PageParam;
import com.willow.platform.core.WillowException;
import com.willow.platform.core.base.domian.BaseObject;
import com.willow.platform.core.criteria.Criteria;
import com.willow.platform.utils.CodeGenerator;
import com.willow.platform.utils.DateUtils;
import com.willow.platform.utils.LocalStringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *       所有Dao类的基类
 * </pre>
 *
 * @author 朱贤俊
 * @version 2.0
 */
public abstract class BaseDao<E> {

    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(BaseDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 根据Criteria方式查询分页结果集,包括传入分页参数
     *
     * @param criteria
     * @param pageParam
     * @return
     */
    public Page<E> queryPageListByCriteria(Criteria criteria, PageParam pageParam) {
        if (pageParam == null) {
            throw new WillowException("pageParam对象不能为空");
        }
        Page<E> page = new Page<E>(pageParam);
        List<E> result = queryListByCriteria(criteria, pageParam);
        int count = countListByCriteria(criteria);
        page.setResult(result);
        page.setTotalCount(count);
        return page;
    }


    /**
     * 根据Criteria方式查询结果集,包括传入分页参数
     *
     * @param criteria
     * @param pageParam 若为Null的话，则不进行分页
     * @return
     */
    public List<E> queryListByCriteria(Criteria criteria, PageParam pageParam) {
        Map<String, Object> filters = new HashMap<String, Object>();
        if (criteria == null) {
            criteria = createCriteria();
        }
        filters.put("criteria", criteria);
        if (pageParam != null) {
            filters.put("pageFirst", pageParam.getFirst());
            filters.put("pageSize", pageParam.getPageSize());
            if (LocalStringUtils.isNotEmpty(pageParam.getSortFieldName())) {
                filters.put("sortFieldName", pageParam.getSortFieldName());
            }
            if (LocalStringUtils.isNotEmpty(pageParam.getSortType())) {
                filters.put("sortType", pageParam.getSortType());
            }
        }
        String queryStatement = getMyBatisMapperNamespace() + ".queryListByCriteria";
        Object results = sqlSessionTemplate.selectList(queryStatement, filters);
        return (List<E>) results;

    }

    /**
     * 根据Criteria方式查询数量
     *
     * @param criteria
     * @return
     */
    public int countListByCriteria(Criteria criteria) {
        Map<String, Object> filters = new HashMap<String, Object>();
        if (criteria == null) {
            criteria = createCriteria();
        }
        filters.put("criteria", criteria);
        String countStatement = getMyBatisMapperNamespace() + ".countListByCriteria";
        return (Integer) sqlSessionTemplate.selectOne(countStatement, filters);

    }

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    public int save(E entity) {
        String addStatement = getMyBatisMapperNamespace() + ".save";
        if (entity instanceof BaseObject) {
            BaseObject baseObject = (BaseObject) entity;
            baseObject.setObjId(CodeGenerator.getUUID());
            String time = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
            baseObject.setCreateTime(time);
            baseObject.setUpdateTime(time);
        }
        int affectCount = sqlSessionTemplate.update(addStatement, entity);
        return affectCount;
    }

    /**
     * 根据主键删除
     *
     * @param objId
     * @return
     */
    public int deleteByObjId(String objId) {
        String delStatement = getMyBatisMapperNamespace() + ".deleteByObjId";
        return sqlSessionTemplate.delete(delStatement, objId);
    }

    /**
     * 根据查询条件删除
     *
     * @param filters
     * @return
     */
    private int deleteByParam(Map<String, Object> filters) {
        String delStatement = getMyBatisMapperNamespace() + ".deleteByParam";
        return sqlSessionTemplate.delete(delStatement, filters);
    }

    /**
     * 根据查询条件删除
     *
     * @param entity
     * @return
     */
    public int deleteByParam(E entity) {
        Map<String, Object> filters = new HashMap<String, Object>();
        Map parameterObject = null;
        try {
            parameterObject = BeanUtils.describe(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据对象转换异常" + e.getStackTrace());
        }
        filters.putAll(parameterObject);
        return deleteByParam(filters);
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    public int update(E entity) {
        String updStatement = getMyBatisMapperNamespace() + ".update";
        if (entity instanceof BaseObject) {
            BaseObject baseObject = (BaseObject) entity;
            String time = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
            baseObject.setUpdateTime(time);
        }
        int affectCount = sqlSessionTemplate.update(updStatement, entity);
        return affectCount;
    }

    /**
     * 根据主键更新不为空的字段
     *
     * @param entity
     * @return
     */
    public int updateByIdSelective(E entity) {
        String updStatement = getMyBatisMapperNamespace() + ".updateByIdSelective";
        if (entity instanceof BaseObject) {
            BaseObject baseObject = (BaseObject) entity;
            String time = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
            baseObject.setUpdateTime(time);
        }
        int affectCount = sqlSessionTemplate.update(updStatement, entity);
        return affectCount;
    }

    /**
     * 根据主键查询对象
     *
     * @param objId
     * @return
     */
    public E selectByObjId(String objId) {
        String getStatement = getMyBatisMapperNamespace() + ".selectByObjId";
        Object result = sqlSessionTemplate.selectOne(getStatement, objId);
        return (E) result;
    }

    /**
     * 根据领域对象查询唯一记录
     *
     * @param entity
     * @return
     */
    public E selectByCondition(E entity) {
        Map<String, Object> filters = new HashMap<String, Object>();
        Map parameterObject = null;
        try {
            parameterObject = BeanUtils.describe(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据查询对象转换异常" + e.getStackTrace());
        }
        filters.putAll(parameterObject);
        String getStatement = getMyBatisMapperNamespace() + ".selectByCondition";
        Object result = sqlSessionTemplate.selectOne(getStatement, filters);
        return (E) result;
    }


    /**
     * 根据领域对象条件查询分页数据
     *
     * @param entity
     * @param pageParam
     * @return
     */
    public Page<E> queryPageList(E entity, PageParam pageParam) {
        Map<String, Object> filters = new HashMap<String, Object>();
        Map parameterObject = null;
        try {
            parameterObject = BeanUtils.describe(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据查询对象转换异常" + e.getStackTrace());
        }
        filters.putAll(parameterObject);
        Page<E> page = queryPageList(filters, pageParam);
        return page;
    }

    /**
     * 根据查询条件分页数据
     *
     * @param filters
     * @param pageParam
     * @return
     */
    private Page<E> queryPageList(Map<String, Object> filters, PageParam pageParam) {
        if (filters == null) {
            filters = new HashMap<String, Object>();
        }
        Page<E> page = new Page<E>(pageParam);
        filters.put("pageFirst", pageParam.getFirst());
        filters.put("pageSize", pageParam.getPageSize());
        if (LocalStringUtils.isNotEmpty(pageParam.getSortFieldName())) {
            filters.put("sortFieldName", pageParam.getSortFieldName());
        }
        if (LocalStringUtils.isNotEmpty(pageParam.getSortType())) {
            filters.put("sortType", pageParam.getSortType());
        }
        List<E> results = queryList(filters);
        int count = countList(filters);
        page.setResult(results);
        page.setTotalCount(count);
        return page;
    }


    /**
     * 根据领域对象条件查询数据
     *
     * @param entity
     * @return
     */
    public List<E> queryList(E entity) {
        Map<String, Object> filters = new HashMap<String, Object>();
        Map parameterObject = null;
        try {
            parameterObject = BeanUtils.describe(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据查询对象转换异常" + e.getStackTrace());
        }
        filters.putAll(parameterObject);
        return queryList(filters);
    }

    /**
     * 根据条件查询结果集,可传入分页参数
     *
     * @param param
     * @return
     */
    private List<E> queryList(Map<String, Object> param) {
        String queryStatement = getMyBatisMapperNamespace() + ".queryList";
        Object results = sqlSessionTemplate.selectList(queryStatement, param);
        return (List<E>) results;
    }


    public int countList(E entity) {
        Map<String, Object> filters = new HashMap<String, Object>();
        Map parameterObject = null;
        try {
            parameterObject = BeanUtils.describe(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据查询对象转换异常" + e.getStackTrace());
        }
        filters.putAll(parameterObject);
        return countList(filters);
    }


    /**
     * 根据条件查询结果集数量
     *
     * @param param
     * @return
     */
    private int countList(Map<String, Object> param) {
        String countStatement = getMyBatisMapperNamespace() + ".countList";
        return (Integer) sqlSessionTemplate.selectOne(countStatement, param);
    }


    protected Criteria createCriteria() {
        Criteria criteria = new Criteria();
        return criteria;
    }


    /**
     * @param mapClazz
     * @param <M>
     * @return
     */
    protected <M> M getMapper(Class<M> mapClazz) {
        return (M) sqlSessionTemplate.getMapper(mapClazz);
    }

    private String getMyBatisMapperNamespace() {
        return getMapperClass().getName();
    }

    //mybatis映射类名
    public abstract Class getMapperClass();

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
}
