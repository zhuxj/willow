/**
 * 版权声明：软件公司 版权所有 违者必究 2012
 * 日    期：12-9-6
 */
package com.willow.platform.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.UnsupportedEncodingException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class PinYinUtils {
    private final static int[] li_SecPosValue =
            {
                    1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472,
                    3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590
            };
    private final static String[] lc_FirstLetter =
            {
                    "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p",
                    "q", "r", "s", "t", "w", "x", "y", "z"
            };

    /**
     * 取得给定汉字串的首字母串,即声母串
     *
     * @param str 给定汉字串
     * @return 声母串
     */
    public static String getAllFirstLetter(String str) {
        if (LocalStringUtils.isBlank(str)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            String[] strings = PinyinHelper.toHanyuPinyinStringArray(chars[i]);
            if (strings != null) {
                sb.append(strings[0].charAt(0));
            } else {
                sb.append(chars[i]);
            }
        }

        return sb.toString().toUpperCase();
    }

    /**
     * 取得给定汉字的首字母,即声母
     *
     * @param chinese 给定的汉字
     * @return 给定汉字的声母
     */
    public static String getFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        chinese = conversionStr(chinese, "GBK", "ISO8859-1");

        if (chinese.length() > 1) //判断是不是汉字
        {
            int li_SectorCode = (int) chinese.charAt(0); //汉字区码
            int li_PositionCode = (int) chinese.charAt(1); //汉字位码
            li_SectorCode = li_SectorCode - 160;
            li_PositionCode = li_PositionCode - 160;
            int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; //汉字区位码
            if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (li_SecPosCode >= li_SecPosValue[i] &&
                            li_SecPosCode < li_SecPosValue[i + 1]) {
                        chinese = lc_FirstLetter[i];
                        break;
                    }
                }
            } else //非汉字字符,如图形符号或ASCII码
            {
                chinese = conversionStr(chinese, "ISO8859-1", "GBK");
                chinese = chinese.substring(0, 1);
            }
        }

        return chinese.toUpperCase();
    }

    /**
     * 字符串编码转换
     *
     * @param str           要转换编码的字符串
     * @param charsetName   原来的编码
     * @param toCharsetName 转换后的编码
     * @return 经过编码转换后的字符串
     */
    private static String conversionStr(String str, String charsetName, String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("字符串编码转换异常：" + ex.getMessage());
        }

        return str;
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    public static String cn2Spell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().toUpperCase();
    }

    public static void main(String[] args) {
        String testString = "闫";
        System.out.println(PinYinUtils.getAllFirstLetter(testString));
        System.out.println(PinYinUtils.getFirstLetter("朱贤俊"));
        System.out.println(PinYinUtils.getAllFirstLetter("朱贤俊"));
        System.out.println(PinYinUtils.cn2Spell("朱贤俊"));
        System.out.println(PinYinUtils.getAllFirstLetter("汉语"));
        System.out.println(PinYinUtils.cn2Spell("汉语"));
    }

}
