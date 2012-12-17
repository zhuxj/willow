/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-11-30
 */
package com.willow.platform.core.base.service;

import com.willow.platform.core.Page;
import com.willow.platform.core.PageParam;
import com.willow.platform.core.base.dao.BaseDao;

import java.util.List;

/**
 * <pre>
 * 业务类基类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public abstract class BaseService<E> {

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    public int save(E entity) {
        return getDao().save(entity);
    }

    /**
     * 根据主键删除
     *
     * @param objId
     * @return
     */
    public int deleteByObjId(String objId) {
        return getDao().deleteByObjId(objId);
    }

    /**
     * 根据主键数组批量删除
     *
     * @param objIds
     */
    public void deleteByObjIds(String[] objIds) {
        for (String objId : objIds) {
            getDao().deleteByObjId(objId);
        }
    }


    /**
     * 根据查询条件删除
     *
     * @param entity
     * @return
     */
    public int deleteByParam(E entity) {
        return getDao().deleteByParam(entity);
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    public int update(E entity) {
        int affectCount = getDao().update(entity);
        return affectCount;
    }

    /**
     * 根据主键更新不为空的字段
     *
     * @param entity
     * @return
     */
    public int updateByIdSelective(E entity) {
        int affectCount = getDao().updateByIdSelective(entity);
        return affectCount;
    }

    /**
     * 根据主键查询对象
     *
     * @param objId
     * @return
     */
    public E selectByObjId(String objId) {
        Object result = getDao().selectByObjId(objId);
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
        Page<E> page = getDao().queryPageList(entity, pageParam);
        return page;
    }

    /**
     * 根据条件查询结果集,包括传入分页参数
     *
     * @param entity
     * @return
     */
    public List<E> queryList(E entity) {
        Object results = getDao().queryList(entity);
        return (List<E>) results;
    }

    /**
     * 根据条件查询结果集数量
     *
     * @param entity
     * @return
     */
    public int countList(E entity) {
        return getDao().countList(entity);
    }


    public abstract BaseDao<E> getDao();
}
