/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.platform.core.criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 模拟hibernate的Criteria类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class Criteria {
    //条件集合
    private List<Criterion> criteria;

    public Criteria() {
        criteria = new ArrayList<Criterion>();
    }


    public boolean isValid() {
        return criteria.size() > 0;
    }


    /**
     * 字段为空
     *
     * @param field
     * @return
     */
    public Criteria andFieldIsNull(String field) {
        addCriterion(field + " is null");
        return this;
    }

    /**
     * 字段不为空
     *
     * @param field
     * @return
     */
    public Criteria andFieldIsNotNull(String field) {
        addCriterion(field + " is not null");
        return this;
    }

    /**
     * 字段等于
     *
     * @param field
     * @param value
     * @return
     */
    public Criteria andFieldEqualTo(String field, String value) {
        addCriterion(field + " =", value, field);
        return this;
    }

    /**
     * 字段不等于
     *
     * @param field
     * @param value
     * @return
     */
    public Criteria andFieldNotEqualTo(String field, String value) {
        addCriterion(field + " <>", value, field);
        return this;
    }

    /**
     * 字段值大于
     *
     * @param field
     * @param value
     * @return
     */
    public Criteria andFieldGreaterThan(String field, String value) {
        addCriterion(field + " >", value, field);
        return (Criteria) this;
    }

    /**
     * 字段值大于等于
     *
     * @param field
     * @param value
     * @return
     */
    public Criteria andFieldGreaterThanOrEqualTo(String field, String value) {
        addCriterion(field + " >=", value, field);
        return (Criteria) this;
    }

    /**
     * 字段值小于
     *
     * @param field
     * @param value
     * @return
     */
    public Criteria andFieldLessThan(String field, String value) {
        addCriterion(field + " <", value, field);
        return (Criteria) this;
    }

    /**
     * 字段值小于等于
     *
     * @param field
     * @param value
     * @return
     */
    public Criteria andFieldLessThanOrEqualTo(String field, String value) {
        addCriterion(field + " <=", value, field);
        return (Criteria) this;
    }


    /**
     * 字段值like
     *
     * @param field
     * @param value
     * @return
     */
    public Criteria andFieldLike(String field, String value) {
        addCriterion(field + " like", value, field);
        return (Criteria) this;
    }

    /**
     * 字段值不like
     *
     * @param field
     * @param value
     * @return
     */
    public Criteria andFieldNotLike(String field, String value) {
        addCriterion(field + " not like", value, field);
        return (Criteria) this;
    }

    /**
     * 字段值in
     *
     * @param field
     * @param values
     * @return
     */
    public Criteria andFieldIn(String field, List<String> values) {
        addCriterion(field + " in", values, field);
        return (Criteria) this;
    }

    /**
     * 字段值不in
     *
     * @param field
     * @param values
     * @return
     */
    public Criteria andFieldNotIn(String field, List<String> values) {
        addCriterion(field + " not in", values, field);
        return (Criteria) this;
    }

    /**
     * 字段值介于
     *
     * @param field
     * @param value1
     * @param value2
     * @return
     */
    public Criteria andFieldBetween(String field, String value1, String value2) {
        addCriterion(field + " between", value1, value2, field);
        return (Criteria) this;
    }

    /**
     * 字段值不介于
     *
     * @param field
     * @param value1
     * @param value2
     * @return
     */
    public Criteria andFieldNotBetween(String field, String value1, String value2) {
        addCriterion(field + " not between", value1, value2, field);
        return (Criteria) this;
    }


    protected void addCriterion(String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        criteria.add(new Criterion(condition));
    }

    protected void addCriterion(String condition, Object value, String property) {
        if (value == null) {
            throw new RuntimeException("Value for " + property + " cannot be null");
        }
        criteria.add(new Criterion(condition, value));
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + property + " cannot be null");
        }
        criteria.add(new Criterion(condition, value1, value2));
    }


    public List<Criterion> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<Criterion> criteria) {
        this.criteria = criteria;
    }


}
