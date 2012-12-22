/**
 * @(#)com.willow.util.date.RangeDate
 * 2009-7-21
 * Copyright 2009 
 * 软件公司, 版权所有 违者必究
 */
package com.willow.platform.utils.date;

/**
 * @author 朱贤俊
 * @version 1.0
 */
public class RangeDateTime {
    private String begin;
    private String end;

    public RangeDateTime() {
    }

    public RangeDateTime(String begin, String end) {
        this.begin = begin;
        this.end = end;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
    
}
