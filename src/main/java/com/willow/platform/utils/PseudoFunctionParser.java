/**
 * @(#)com.willow.domain.sys.JobConfig
 * 2009-6-13
 * Copyright 2009
 * 软件公司, 版权所有 违者必究
 */
package com.willow.platform.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Calendar;

/**
 * @author 朱贤俊
 * @version 1.0
 */
public class PseudoFunctionParser {
    /**
     * 解析日历的伪函数
     *
     * @param str
     * @return
     */
    public static String parse(String str) {

        if (StringUtils.isEmpty(str))
            return "";

        StringBuffer resultSB = new StringBuffer();

        while (true) {
            if (StringUtils.isEmpty(str))
                break;
            int idxDateStart = str.indexOf("$DATE{");
            if (idxDateStart == -1) {
                resultSB.append(str);
                break;
            }
            resultSB.append(str.substring(0, idxDateStart));
            int idxDateEnd = str.indexOf('}', idxDateStart);

            String parseResult = parseCalendar(str.substring(idxDateStart, idxDateEnd + 1));
            resultSB.append(parseResult);
            str = str.substring(idxDateEnd + 1);
        }

        return resultSB.toString();
    }

    /**
     * 解析日历伪函数
     *
     * @param str 格式为：$DATE{yyyy-MM-dd}
     * @return
     */
    private static String parseCalendar(String str) {

        if (StringUtils.isEmpty(str))
            return "";

        Calendar c = Calendar.getInstance();
        int idx = str.toUpperCase().indexOf("$DATE{");

        if (idx == -1)
            return str;

        String param = str.substring(idx + 6, str.length() - 1);

        String[] paramArray = param.split(",");

        for (int i = 1, length = paramArray.length; i < length; i = i + 2) {

            String paramStr = paramArray[i].trim();
            String paramValue = paramArray[i + 1].trim();

            if ("YEAR".equalsIgnoreCase(paramStr)) {
                c.add(Calendar.YEAR, Integer.parseInt(paramValue));
                continue;
            }

            if ("MONTH".equalsIgnoreCase(paramStr)) {
                c.add(Calendar.MONTH, Integer.parseInt(paramValue));
                continue;
            }

            if ("DATE".equalsIgnoreCase(paramStr)) {
                c.add(Calendar.DATE, Integer.parseInt(paramValue));
                continue;
            }

            if ("HOUR".equalsIgnoreCase(paramStr)) {
                c.add(Calendar.HOUR, Integer.parseInt(paramValue));
                continue;
            }

            if ("MINUTE".equalsIgnoreCase(paramStr)) {
                c.add(Calendar.MINUTE, Integer.parseInt(paramValue));
                continue;
            }

            if ("SECOND".equalsIgnoreCase(paramStr)) {
                c.add(Calendar.SECOND, Integer.parseInt(paramValue));
                continue;
            }
        }

        String result = DateFormatUtils.format(c.getTimeInMillis(), paramArray[0].trim());

        result = DateUtils.toStoreStr(result);

        return result;
    }

    public static void main(String[] args) {
        System.out.println(parse("$BOA{DDDD-DDDD}ddd$DATE{yy-MM-dd,YEAR,-1}~$DATE{yyyy-MM-dd}"));
    }
}
