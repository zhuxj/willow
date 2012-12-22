/**
 * 版权声明：软件公司 版权所有 违者必究 2011
 * 日    期：11-7-20
 */
package com.willow.platform.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 朱贤俊
 * @version 1.72
 *          功能说明：
 */
public class TrandtionalToSimplified {
    private final static String encoding = "utf-8";
    private static String tra;
    private static String sim;

    private static Map<String, String> specilWord = new HashMap<String, String>();

    static {
        specilWord.put("著", "著");
    }

    /**
     * 初始化繁体和简体字库
     */
    public static void initChars() {
        ClassPathResource traResource = new ClassPathResource("tranditional.dic");
        ClassPathResource simResource = new ClassPathResource("simplified.dic");

        try {
            tra = IOUtils.toString(traResource.getInputStream(), encoding);
            sim = IOUtils.toString(simResource.getInputStream(), encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 简体转繁体
     *
     * @param simplified
     * @return
     */
    public static String simTotra(String simplified) {
        if (sim == null) {
            initChars();
        }
        StringBuilder traditional = new StringBuilder();
        for (int i = 0; i < simplified.length(); i++) {
            if (sim.indexOf(simplified.charAt(i)) != -1) {
                traditional.append(tra.charAt(sim.indexOf(simplified.charAt(i))));
            } else {
                traditional.append(simplified.charAt(i));
            }
        }
        return traditional.toString();
    }

    /**
     * 繁体转简体
     *
     * @param traditional
     * @return
     */
    public static String traTosim(String traditional) {
        if (LocalStringUtils.isEmpty(traditional)) {
            return traditional;
        }
        if (tra == null) {
            initChars();
        }
        StringBuilder simplified = new StringBuilder();
        StringBuilder specialSimplified = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < traditional.length(); i++) {
            if (tra.indexOf(traditional.charAt(i)) != -1) {
                if (specilWord.containsKey(String.valueOf(traditional.charAt(i)))) {
                    specialSimplified.append(traditional.charAt(i));
                    flag = true;
                }
                simplified.append(sim.charAt(tra.indexOf(traditional.charAt(i))));
            } else {
                simplified.append(traditional.charAt(i));
                specialSimplified.append(traditional.charAt(i));
            }
        }
        if (flag) {
            simplified.append(" OR ");
            simplified.append(specialSimplified);
        }

        return simplified.toString();
    }


    /**
     * 判断是否是繁体
     *
     * @param str
     * @return
     */
    public static boolean isTrandtional(String str) {
        if (null == str)
            return false;
        if (" ".equals(str.trim()))
            return false;
        byte[] bytes = str.getBytes();
        if (bytes.length < 2)
            return false;
        byte aa = (byte) 0xB0;
        byte bb = (byte) 0xF7;
        byte cc = (byte) 0xA1;
        byte dd = (byte) 0xFE;
        if (bytes[0] >= aa && bytes[0] <= bb) {
            return !(bytes[1] < cc || bytes[1] > dd);
        }
        return false;
    }

    /**
     * 判断是否是简体
     *
     * @param str
     * @return
     */
    public static boolean isSimplified(String str) {
        if (null == str)
            return false;
        if (" ".equals(str.trim()))
            return false;
        byte[] bytes = str.getBytes();
        if (bytes.length < 2)
            return false;
        byte aa = (byte) 0xB0;
        byte bb = (byte) 0xF7;
        byte cc = (byte) 0xA1;
        byte dd = (byte) 0xFE;
        if (bytes[0] >= aa && bytes[0] <= bb) {
            return (bytes[1] < cc || bytes[1] > dd);
        }
        return false;
    }
}
