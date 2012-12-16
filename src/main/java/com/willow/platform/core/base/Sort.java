/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2011 
 * 日    期：11-12-26
 */
package com.willow.platform.core.base;

import com.willow.platform.utils.LocalStringUtils;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.82
 */
public class Sort implements Serializable {
    // 排序的字段名
    private String sortFieldName;

    public final static String DESC = "1";
    public final static String ASC = "2";

    // 1，降序 2，升序*/
    private String sortType = DESC;

    protected Sort() {

    }

    public Sort(String sortFieldName) {
        this.sortFieldName = sortFieldName;
        this.sortType = DESC;
    }

    public String getSortFieldName() {
        return sortFieldName;
    }

    public Sort(String sortFieldName, String sortType) {
        this.sortFieldName = sortFieldName;
        this.sortType = sortType;
    }

    public OrderFieldName getOrderFieldName() {
        try {
            return OrderFieldName.valueOf(sortFieldName);
        } catch (IllegalArgumentException e) {
            return OrderFieldName.score;
        }
    }

    public void setSortFieldName(String sortFieldName) {
        this.sortFieldName = sortFieldName;
    }


    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    //(升序 true 降序 false)
    public boolean isOrderType() {
        return ASC.equals(sortType);
    }

    public boolean hasNoField() {
        if (LocalStringUtils.isNotBlank(sortFieldName)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sort sort = (Sort) o;

        if (sortFieldName != null ? !sortFieldName.equals(sort.sortFieldName) : sort.sortFieldName != null)
            return false;
        if (sortType != null ? !sortType.equals(sort.sortType) : sort.sortType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sortFieldName != null ? sortFieldName.hashCode() : 0;
        result = 31 * result + (sortType != null ? sortType.hashCode() : 0);
        return result;
    }


    public enum OrderFieldName {
        score("score", ""),
        sort_order("sortOrder", "sort_order"),
        shop_price("shopPrice", "shop_price"),
        create_time("createTime", "create_time"),
        update_time("updateTime", "update_time"),
        click_count("clickCount", "click_count"),
        commend("commend", "commend"),
        month_sale_num("monthSaleNum", "month_sale_num"),
        comment_num("commentNum", "comment_num"),
        goods_sn("goodsSn", "goods_sn"),
        inventory_amount("inventoryAmount", "inventory_amount");

        private String classFieldName;

        private String dbFieldName;

        OrderFieldName(String classFieldName, String dbFieldName) {
            this.classFieldName = classFieldName;
            this.dbFieldName = dbFieldName;
        }

        public String getClassFieldName() {
            return classFieldName;
        }

        public String getDbFieldName() {
            return dbFieldName;
        }
    }

}
