/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-12
 */
package com.willow.platform.core.base.mapper;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  mybatis映射类基础类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public interface BaseMapper<E> {
    /**
     * 保存
     *
     * @param entity
     * @return
     */
    public int save(E entity);

    /**
     * 根据主键删除
     *
     * @param objId
     * @return
     */
    public int deleteByObjId(String objId);

    /**
     * 根据查询条件删除
     *
     * @param param
     * @return
     */
    public int deleteByParam(Map<String, Object> param);

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    public int update(E entity);

    /**
     * 根据主键更新不为空的字段
     *
     * @param entity
     * @return
     */
    public int updateByIdSelective(E entity);

    /**
     * 根据主键查询对象
     *
     * @param objId
     * @return
     */
    public E selectByObjId(String objId);

    /**
     * 根据条件查询结果集,包括传入分页参数
     *
     * @param param
     * @return
     */
    public List<E> queryList(Map<String, Object> param);

    /**
     * 根据条件查询结果集数量
     *
     * @param param
     * @return
     */
    public int countList(Map<String, Object> param);


    /**
     * 根据Criteria方式查询结果集,包括传入分页参数
     *
     * @param param
     * @return
     */
    public List<E> queryListByCriteria(Map<String, Object> param);

    /**
     * 根据Criteria方式查询结果集数量
     *
     * @param param
     * @return
     */
    public int countListByCriteria(Map<String, Object> param);


}
