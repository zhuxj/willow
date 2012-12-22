/**
 * @(#)com.willow.util.Arithmetic4Double
 * 2009-9-15
 * Copyright 2009 
 * 软件公司, 版权所有 违者必究
 */
package com.willow.platform.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author 朱贤俊
 * @version 1.0
 */
public class NumberUtils {

    //默认除法运算精度(精确到小数点后几位，平台中默认是两位) 
    private static final int DEF_DIV_SCALE = 2;

    //所有方法均用静态方法实现，不允许实例化
    private NumberUtils() {
    }

    /**
     * 实现浮点数的加法运算功能
     *
     * @param v1 加数1
     * @param v2 加数2
     * @return v1+v2的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 实现浮点数的减法运算功能
     *
     * @param v1 被减数
     * @param v2 减数
     * @return v1-v2的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 实现浮点数的乘法运算功能
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return v1×v2的积
     */
    public static double multi(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 实现浮点数的除法运算功能
     * 当发生除不尽的情况时，精确到小数点以后DEF_DIV_SCALE位(默认为10位)，后面的位数进行四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return v1/v2的商
     */
    public static double div(double v1, double v2) {
        if (v2 == 0d) {
            throw new IllegalArgumentException(
                    "The v2 can not equal zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 实现浮点数的除法运算功能
     * 当发生除不尽的情况时，精确到小数点以后scale位，后面的位数进行四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示需要精确到小数点以后几位
     * @return v1/v2的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入功能
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        return b.divide(BigDecimal.ONE, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将String转为double
     *
     * @param value
     * @return
     */
    public static double string2Double(String value) {
        double returnValue;
        try {
            returnValue = Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            returnValue = 0.0;
        }
        if (Double.isInfinite(returnValue)) {
            returnValue = 0.0;
        }
        return returnValue;
    }

    /**
     * 将双精度型转为长整形
     *
     * @param value
     * @return
     */
    public static long double2Long(double value) {
        return BigDecimal.valueOf(value).longValue();
    }

    /**
     * 将双精度型转为整形
     *
     * @param value
     * @return
     */
    public static int double2Int(double value) {
        return BigDecimal.valueOf(value).intValue();
    }

    /**
     * 浮点型数值，小数点补齐方法
     *
     * @param number
     * @return
     */
    public static String doubleFormat(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("##,###,###,###,##0.00");
        return decimalFormat.format(number);
    }

    /**
     * 将数字字符串，转换为
     *
     * @param number
     * @return
     */
    public static String longFormat(long number) {
        DecimalFormat decimalFormat = new DecimalFormat("##,###,###,###,##0");
        return decimalFormat.format(number);
    }

    /**
     * 转换为保存折扣
     *
     * @param value
     * @param isPercent
     * @return
     */
    public static int discountDBFormat(String value, boolean isPercent) {
        double returnValue;
        try {
            returnValue = Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            returnValue = 0.0;
        }
        if (isPercent) {
            return double2Int(multi(returnValue, 10000000));
        }
        return double2Int(multi(returnValue, 100000));
    }

}
