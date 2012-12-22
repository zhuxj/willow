/**
 * @(#)com.willow.util.RangeDateUtils
 * 2009-7-21
 * Copyright 2009 
 * 软件公司, 版权所有 违者必究
 */
package com.willow.platform.utils.date;

import com.willow.platform.utils.DateUtils;

/**
 * @author 朱贤俊
 * @version 1.0
 */
public class RangeDateUtils {

    /*今天*/
    public static final int TODAY = 1;
    /*最近三天*/
    public static final int LAST_THREE_DAY = 2;
    /*本周*/
    public static final int THIS_WEEK = 3;
    /*本月*/
    public static final int THIS_MONTH = 4;
    /*最近三个月*/
    public static final int LAST_THREE_MONTH = 5;
    /*最近六个月*/
    public static final int LAST_SIX_MONTH = 7;
    /*最近一年*/
    public static final int LAST_YEAR = 8;
    /*昨天*/
    public static final int YESTERDAY = 9;
    /*最近一周*/
    public static final int LAST_ONE_WEEK = 10;

    /*所有*/
    public static final int ALL = 6;

    public static final String FROM_SUBFIX = "000000";
    public static final String END_SUBFIX = "235959";


    public static RangeDateTime getRangeDateTime(int dateType) {
        try {
            RangeDateTime rangeDateTime = new RangeDateTime();
            switch (dateType) {
                case TODAY:
                    String dateStr = DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE);
                    rangeDateTime.setBegin(dateStr + FROM_SUBFIX);
                    rangeDateTime.setEnd(dateStr + END_SUBFIX);
                    break;
                case LAST_THREE_DAY:
                    String begin = DateUtils.getThreeDayAgoBegin("yyyyMMdd");
                    rangeDateTime.setBegin(begin + FROM_SUBFIX);
                    rangeDateTime.setEnd(DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE) + END_SUBFIX);
                    break;
                case LAST_ONE_WEEK:
                    String oneWeekBegin = DateUtils.getOneWeekBeforeTime("yyyyMMdd");
                    rangeDateTime.setBegin(oneWeekBegin + FROM_SUBFIX);
                    rangeDateTime.setEnd(DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE) + END_SUBFIX);
                    break;
                case THIS_WEEK:
                    String weekBegin = DateUtils.getCurrentWeekBegin("yyyyMMdd");
                    rangeDateTime.setBegin(weekBegin + FROM_SUBFIX);
                    rangeDateTime.setEnd(DateUtils.getCurrentWeekEnd("yyyyMMdd") + END_SUBFIX);
                    break;
                case THIS_MONTH:
                    String monthBegin = DateUtils.getCurrentMonthBegin("yyyyMMdd");
                    rangeDateTime.setBegin(monthBegin + FROM_SUBFIX);
                    rangeDateTime.setEnd(DateUtils.getCurrentMonthEnd("yyyyMMdd") + END_SUBFIX);
                    break;
                case LAST_THREE_MONTH:
                    String threeMonthBegin = DateUtils.getThreeMonthAgoBegin("yyyyMMdd");
                    rangeDateTime.setBegin(threeMonthBegin + FROM_SUBFIX);
                    rangeDateTime.setEnd(DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE) + END_SUBFIX);
                    break;
                case LAST_SIX_MONTH:
                    String sixMonthBegin = DateUtils.getSixMonthAgoBegin("yyyyMMdd");
                    rangeDateTime.setBegin(sixMonthBegin + FROM_SUBFIX);
                    rangeDateTime.setEnd(DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE) + END_SUBFIX);
                    break;
                case LAST_YEAR:
                    String oneYearBegin = DateUtils.getOneYearAgoBegin("yyyyMMdd");
                    rangeDateTime.setBegin(oneYearBegin + FROM_SUBFIX);
                    rangeDateTime.setEnd(DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE) + END_SUBFIX);
                    break;
                case YESTERDAY:
                    String yesterdayBegin = DateUtils.getYesterday();
                    rangeDateTime.setBegin(yesterdayBegin + FROM_SUBFIX);
                    rangeDateTime.setEnd(DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE) + END_SUBFIX);
                    break;
                case ALL:
                    rangeDateTime.setBegin("00000000" + FROM_SUBFIX);
                    rangeDateTime.setEnd("99999999" + END_SUBFIX);
                    break;
                default:
                    break;
            }
            return rangeDateTime;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
