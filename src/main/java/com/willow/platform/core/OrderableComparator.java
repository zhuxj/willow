/**
 * 版权声明：中图壹购信息科技有限公司 版权所有 违者必究
 * 创建日期：2010-8-31
 */
package com.willow.platform.core;

import java.util.Comparator;

/**
 * 功能说明：
 * 实现Orderable接口的比较器
 *
 * @author 朱贤俊
 */
public class OrderableComparator implements Comparator {
    private static OrderableComparator intance = new OrderableComparator();

    private OrderableComparator() {
    }

    public int compare(Object obj1, Object obj2) {
        int order1 = Integer.MAX_VALUE;
        int order2 = Integer.MAX_VALUE;
        if (obj1 instanceof Orderable) {
            order1 = ((Orderable) obj1).getOrdreNo();
        }
        if (obj2 instanceof Orderable) {
            order2 = ((Orderable) obj2).getOrdreNo();
        }
        if (order1 > order2) {
            return 1;
        } else if (order1 < order2) {
            return -1;
        } else {
            return 0;
        }
    }

    public static OrderableComparator getIntance() {
        return intance;
    }

    public static void main(String[] args) {
        System.out.println("" + (Integer.MIN_VALUE - 1));
    }
}
