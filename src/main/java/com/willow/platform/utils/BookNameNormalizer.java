/**
 * 版权声明：软件公司 版权所有 违者必究 2010
 * 日    期：2010-7-18
 */
package com.willow.platform.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 图书名称正则化算法实现类，图书名称正则化具体说明如下：
 * * 一、字符转换
 * <pre>
 * (1)全角转为半角
 * 所有全角的ascii字符改为半角
 *
 *
 * (2)字符转为小写
 *
 * (3)特殊数字及字母转换
 * ①~?-->1-20 9312~9331
 * ⑴~⒇-->1-20 9332~9351
 * ⒈~⒛-->1-20  9352~9371
 * ㈠~㈩-->1~10  12832~12841
 * 一~十-->1~10
 * ?~?->a~z  9372~9397
 * ?~?-->a~z 9398~9423
 * ?~?-->a~z 9424~9449
 *
 *
 *
 *
 *
 * 二、将注释性内容去除
 *
 * 三、将4位以上数字去除
 *
 * 四、将4位年标准化为二位的年
 *
 * 五、将(*)中带以下信息的整个(*)去除
 *  vcd,dvd cd mp3 mp4 带 盘
 *

 * 六 、将特殊字符删除掉
 * (1)特殊字符ASCII码段
 * 0~47
 * 58~64
 * 91~96
 * 123~255
 * (2)CJK符号和标点移除
 * http://www.nengcha.com/code/unicode/class/102/
 * unicode编码段：12288~12351
 *
 *
 *
 * 四、如果是教辅图书，将其转换成：xxxx年级-学科-版别的格式
 * </pre>
 * 参见：http://www.nengcha.com/soucha/code/unicode/class/
 *
 * @author 朱贤俊
 * @version 1.0
 *          功能说明：
 */
public class BookNameNormalizer {


    private static final Map<Character, Integer> CN_NUMBER_CHARS = new HashMap<Character, Integer>();
    private static final Map<Character, Integer> TRADITIONAL_NUMBER_CHARS = new HashMap<Character, Integer>();
    private static final Map<String, String> SUBJECTS = new LinkedHashMap<String, String>();
    private static final Map<String, String> ROMAN_NUMS = new LinkedHashMap<String, String>();
    private static final Map<String, String> PUBLISHES = new LinkedHashMap<String, String>();

    //需要从图书名称中滤掉的字符
    private static final String[] IGNORE_ITEMS = {"教师用书","教师","全1册","附答案","附试卷","印刷","签名珍藏版",
                                                      "特价/封底打有圆孔", "珍藏","签名","注音","插图", "退","著","社",
                                                      "旧","拼音美绘","优惠","级", "册", "班", "第", "篇", "期",
                                                      "辑","集", "版","刊","学期", "年","必","修","订","著","系列",
                                                      "总","次","vol","年","月","旬","季","双","随书","配","送","新",
                                                      "赠", "附","附","含","rom","dvd","vcd","cd","mp3","mp4",
                                                      "光盘","1磁带","1张","2张","1盘","2盘","1带","2带", "1磁",
                                                      "2磁","带","盘","磁", "盒","本","券", "上下", "上中下","答案"};


    private static final int ZERO_ASCII_VALUE = 48;
    private static final int NUM_CHAR_SEQUENCE_MIN_LEN = 4;
    private static final int COMMENT_CHAR_SEQUENCE_MIN_LEN = 15; //判断广告语的字符长度
    private static final int COMMENT_CHAR_SEQUENCE_MIN_LEN_OF_STUDY = 8; //判断广告语的字符长度

    private static final char[][] COMMENT_CHAR_GROUP = {{'(', ')'}, {'[', ']'}};

    private static final char STUDY_BOOK_SPLIER='o';
    private static final String WHITE_BLANK=" ";//空格
    //特殊图书字符
    private static final Map<String,String> SPECIAL_ITEMS = new HashMap<String,String>();
    static{
        SPECIAL_ITEMS.put("c++","DC");
        SPECIAL_ITEMS.put("c#","SC");
    }
    //新课标
    private static final String STUDY_BOOK_NKB="新课";

    private static final char STUDY_BOOK_VOL='册';

    //识别教辅的特征字符
    private static final String[] STUDY_BOOK_CHARACTS ={"考试","复习","学前"};


    //一~十 的UNICODE编码映射表

    static {
        BookNameNormalizer.CN_NUMBER_CHARS.put(' ', 0);
        BookNameNormalizer.CN_NUMBER_CHARS.put('一', 1);
        BookNameNormalizer.CN_NUMBER_CHARS.put('二', 2);
        BookNameNormalizer.CN_NUMBER_CHARS.put('三', 3);
        BookNameNormalizer.CN_NUMBER_CHARS.put('四', 4);
        BookNameNormalizer.CN_NUMBER_CHARS.put('五', 5);
        BookNameNormalizer.CN_NUMBER_CHARS.put('六', 6);
        BookNameNormalizer.CN_NUMBER_CHARS.put('七', 7);
        BookNameNormalizer.CN_NUMBER_CHARS.put('八', 8);
        BookNameNormalizer.CN_NUMBER_CHARS.put('九', 9);
        BookNameNormalizer.CN_NUMBER_CHARS.put('十', 10);
    }
    //繁体中文数字

    static {
        BookNameNormalizer.TRADITIONAL_NUMBER_CHARS.put('零', 0);
        BookNameNormalizer.TRADITIONAL_NUMBER_CHARS.put('壹', 1);
        BookNameNormalizer.TRADITIONAL_NUMBER_CHARS.put('贰', 2);
        BookNameNormalizer.TRADITIONAL_NUMBER_CHARS.put('叁', 3);
        BookNameNormalizer.TRADITIONAL_NUMBER_CHARS.put('肆', 4);
        BookNameNormalizer.TRADITIONAL_NUMBER_CHARS.put('伍', 5);
        BookNameNormalizer.TRADITIONAL_NUMBER_CHARS.put('陆', 6);
        BookNameNormalizer.TRADITIONAL_NUMBER_CHARS.put('柒', 7);
        BookNameNormalizer.TRADITIONAL_NUMBER_CHARS.put('捌', 8);
        BookNameNormalizer.TRADITIONAL_NUMBER_CHARS.put('玖', 9);
        BookNameNormalizer.TRADITIONAL_NUMBER_CHARS.put('拾', 10);
    }

    //罗马数据i,ii,iii,iv,v,vi,vii,viii,ix,x,xi,xii
    //初始化的顺利不能变，它对应查询的优先级

    static {
        ROMAN_NUMS.put("xii", "12");
        ROMAN_NUMS.put("xi", "11");
        ROMAN_NUMS.put("ix", "9");
        ROMAN_NUMS.put("x", "10");
        ROMAN_NUMS.put("viii", "8");
        ROMAN_NUMS.put("vii", "7");
        ROMAN_NUMS.put("vi", "6");
        ROMAN_NUMS.put("iv", "4");
        ROMAN_NUMS.put("v", "5");
        ROMAN_NUMS.put("iii", "3");
        ROMAN_NUMS.put("ii", "2");
        ROMAN_NUMS.put("i", "1");
    }

    //学科

    static {
        BookNameNormalizer.SUBJECTS.put("数学", "数学");
        BookNameNormalizer.SUBJECTS.put("物理", "物理");
        BookNameNormalizer.SUBJECTS.put("化学", "化学");
        BookNameNormalizer.SUBJECTS.put("英语", "英语");
        BookNameNormalizer.SUBJECTS.put("历史", "历史");
        BookNameNormalizer.SUBJECTS.put("地理", "地理");
        BookNameNormalizer.SUBJECTS.put("生物", "生物");

        BookNameNormalizer.SUBJECTS.put("政治", "政治");
        BookNameNormalizer.SUBJECTS.put("思政", "政治");

        BookNameNormalizer.SUBJECTS.put("体育", "体育");

        BookNameNormalizer.SUBJECTS.put("思想品德", "思品");
        BookNameNormalizer.SUBJECTS.put("思想", "思品");
        BookNameNormalizer.SUBJECTS.put("思品", "思品");
        BookNameNormalizer.SUBJECTS.put("思德", "思品");


        BookNameNormalizer.SUBJECTS.put("理综", "理综");
        BookNameNormalizer.SUBJECTS.put("理科综合", "理综");
        BookNameNormalizer.SUBJECTS.put("理科综", "理综");

        BookNameNormalizer.SUBJECTS.put("文综", "文综");
        BookNameNormalizer.SUBJECTS.put("文科综合", "文综");
        BookNameNormalizer.SUBJECTS.put("文科综", "文综");

        BookNameNormalizer.SUBJECTS.put("健康", "健康");
        BookNameNormalizer.SUBJECTS.put("社会", "社会");
        BookNameNormalizer.SUBJECTS.put("科学", "科学");
        BookNameNormalizer.SUBJECTS.put("语言", "语言");
        BookNameNormalizer.SUBJECTS.put("艺术", "艺术");

        BookNameNormalizer.SUBJECTS.put("计算", "计算");
        BookNameNormalizer.SUBJECTS.put("常识", "常识");
        BookNameNormalizer.SUBJECTS.put("音乐", "音乐");
        BookNameNormalizer.SUBJECTS.put("美术", "美术");
        BookNameNormalizer.SUBJECTS.put("拼音", "拼音");
        BookNameNormalizer.SUBJECTS.put("识字", "识字");

        BookNameNormalizer.SUBJECTS.put("语文", "语文");
    }

    //版别

    static {
        BookNameNormalizer.PUBLISHES.put("人教实验", "人实");
        BookNameNormalizer.PUBLISHES.put("人教实", "人实");

        BookNameNormalizer.PUBLISHES.put("人教新目标", "人教N");
        BookNameNormalizer.PUBLISHES.put("人教新课标", "人教N");
        BookNameNormalizer.PUBLISHES.put("人教新", "人教N");

        BookNameNormalizer.PUBLISHES.put("新人教", "N人");
        BookNameNormalizer.PUBLISHES.put("新人", "N人");

        BookNameNormalizer.PUBLISHES.put("人a", "人a");
        BookNameNormalizer.PUBLISHES.put("人教a", "人a");

        BookNameNormalizer.PUBLISHES.put("人b", "人b");
        BookNameNormalizer.PUBLISHES.put("人教b", "人b");

        BookNameNormalizer.PUBLISHES.put("人民教育", "人教");
        BookNameNormalizer.PUBLISHES.put("人教", "人教");

        BookNameNormalizer.PUBLISHES.put("人民出版", "人民");
        BookNameNormalizer.PUBLISHES.put("人民", "人民");

        BookNameNormalizer.PUBLISHES.put("语文s", "语s");
        BookNameNormalizer.PUBLISHES.put("语s", "语s");
        BookNameNormalizer.PUBLISHES.put("s", "语s");

        BookNameNormalizer.PUBLISHES.put("语文a", "语a");
        BookNameNormalizer.PUBLISHES.put("语a", "语a");

        BookNameNormalizer.PUBLISHES.put("语文", "语文");


        BookNameNormalizer.PUBLISHES.put("广东上海科学教育", "粤沪");
        BookNameNormalizer.PUBLISHES.put("广东上海科学", "粤沪");
        BookNameNormalizer.PUBLISHES.put("广东沪科", "粤沪");
        BookNameNormalizer.PUBLISHES.put("粤教沪科", "粤沪");
        BookNameNormalizer.PUBLISHES.put("粤沪科", "粤沪");
        BookNameNormalizer.PUBLISHES.put("粤沪", "粤沪");

        BookNameNormalizer.PUBLISHES.put("上海教育实验", "沪实");
        BookNameNormalizer.PUBLISHES.put("上海实验", "沪实");
        BookNameNormalizer.PUBLISHES.put("沪教实验", "沪实");
        BookNameNormalizer.PUBLISHES.put("沪实验", "沪实");

        BookNameNormalizer.PUBLISHES.put("上海科技教育", "沪科");
        BookNameNormalizer.PUBLISHES.put("上海科技", "沪科");
        BookNameNormalizer.PUBLISHES.put("上海科教", "沪科");
        BookNameNormalizer.PUBLISHES.put("沪科", "沪科");
        BookNameNormalizer.PUBLISHES.put("上科", "沪科");

        BookNameNormalizer.PUBLISHES.put("上海教育", "沪教");
        BookNameNormalizer.PUBLISHES.put("上教", "沪教");
        BookNameNormalizer.PUBLISHES.put("上海教", "沪教");
        BookNameNormalizer.PUBLISHES.put("上海", "沪教");
        BookNameNormalizer.PUBLISHES.put("沪教", "沪教");
        BookNameNormalizer.PUBLISHES.put("沪", "沪教");

        BookNameNormalizer.PUBLISHES.put("重庆教育", "渝教");
        BookNameNormalizer.PUBLISHES.put("重庆教", "渝教");
        BookNameNormalizer.PUBLISHES.put("重庆", "渝教");
        BookNameNormalizer.PUBLISHES.put("渝教", "渝教");
        BookNameNormalizer.PUBLISHES.put("渝", "渝教");

        BookNameNormalizer.PUBLISHES.put("江苏科技", "苏科");
        BookNameNormalizer.PUBLISHES.put("苏科", "苏科");

        BookNameNormalizer.PUBLISHES.put("江苏教育", "苏教");
        BookNameNormalizer.PUBLISHES.put("苏教", "苏教");
        BookNameNormalizer.PUBLISHES.put("江苏", "苏教");
        BookNameNormalizer.PUBLISHES.put("苏", "苏教");

        BookNameNormalizer.PUBLISHES.put("河北少年儿童", "冀少");
        BookNameNormalizer.PUBLISHES.put("河北少年", "冀少");
        BookNameNormalizer.PUBLISHES.put("河北少", "冀少");
        BookNameNormalizer.PUBLISHES.put("冀少", "冀少");


        BookNameNormalizer.PUBLISHES.put("河北人民教育", "冀教");
        BookNameNormalizer.PUBLISHES.put("河北教育", "冀教");
        BookNameNormalizer.PUBLISHES.put("河北", "冀教");
        BookNameNormalizer.PUBLISHES.put("冀教", "冀教");
        BookNameNormalizer.PUBLISHES.put("冀", "冀教");

        BookNameNormalizer.PUBLISHES.put("河北大学", "河大");
        BookNameNormalizer.PUBLISHES.put("河大", "河大");

        BookNameNormalizer.PUBLISHES.put("湖南教育实验", "湘实");
        BookNameNormalizer.PUBLISHES.put("湘教实验", "湘实");
        BookNameNormalizer.PUBLISHES.put("湖南实验", "湘实");
        BookNameNormalizer.PUBLISHES.put("湘实验", "湘实");

        BookNameNormalizer.PUBLISHES.put("湖南教育", "湘教");
        BookNameNormalizer.PUBLISHES.put("湘教", "湘教");
        BookNameNormalizer.PUBLISHES.put("湖南", "湘教");
        BookNameNormalizer.PUBLISHES.put("湘", "湘教");

        BookNameNormalizer.PUBLISHES.put("湖北教育", "鄂教");
        BookNameNormalizer.PUBLISHES.put("鄂教", "鄂教");
        BookNameNormalizer.PUBLISHES.put("湖北", "鄂教");
        BookNameNormalizer.PUBLISHES.put("鄂", "鄂教");

        BookNameNormalizer.PUBLISHES.put("豫教", "豫教");
        BookNameNormalizer.PUBLISHES.put("河南教育", "豫教");
        BookNameNormalizer.PUBLISHES.put("河南", "豫教");
        BookNameNormalizer.PUBLISHES.put("豫", "豫教");

        BookNameNormalizer.PUBLISHES.put("浙江科学技术", "浙科");
        BookNameNormalizer.PUBLISHES.put("浙江科学", "浙科");
        BookNameNormalizer.PUBLISHES.put("浙科", "浙科");

        BookNameNormalizer.PUBLISHES.put("浙江人民教育", "浙教");
        BookNameNormalizer.PUBLISHES.put("浙江教育", "浙教");
        BookNameNormalizer.PUBLISHES.put("浙教", "浙教");
        BookNameNormalizer.PUBLISHES.put("浙江", "浙教");
        BookNameNormalizer.PUBLISHES.put("浙", "浙教");

        BookNameNormalizer.PUBLISHES.put("四川教育", "川教");
        BookNameNormalizer.PUBLISHES.put("四川", "川教");
        BookNameNormalizer.PUBLISHES.put("川教", "川教");
        BookNameNormalizer.PUBLISHES.put("川", "川教");

        BookNameNormalizer.PUBLISHES.put("闽教", "闽教");
        BookNameNormalizer.PUBLISHES.put("福建教育", "闽教");
        BookNameNormalizer.PUBLISHES.put("福建", "闽教");
        BookNameNormalizer.PUBLISHES.put("闽", "闽教");

        BookNameNormalizer.PUBLISHES.put("广东教育", "广教");
        BookNameNormalizer.PUBLISHES.put("广东", "广教");
        BookNameNormalizer.PUBLISHES.put("广教", "广教");
        BookNameNormalizer.PUBLISHES.put("粤教", "广教");
        BookNameNormalizer.PUBLISHES.put("粤", "广教");

        BookNameNormalizer.PUBLISHES.put("山东科学教育", "鲁科");
        BookNameNormalizer.PUBLISHES.put("山东科学", "鲁科");
        BookNameNormalizer.PUBLISHES.put("山东科", "鲁科");
        BookNameNormalizer.PUBLISHES.put("鲁科教", "鲁科");
        BookNameNormalizer.PUBLISHES.put("鲁科", "鲁科");

        BookNameNormalizer.PUBLISHES.put("山东人民教育", "鲁人");
        BookNameNormalizer.PUBLISHES.put("山东人民", "鲁人");
        BookNameNormalizer.PUBLISHES.put("鲁人教", "鲁人");
        BookNameNormalizer.PUBLISHES.put("鲁人民", "鲁人");
        BookNameNormalizer.PUBLISHES.put("鲁人", "鲁人");

        BookNameNormalizer.PUBLISHES.put("山东教育实验", "鲁实");
        BookNameNormalizer.PUBLISHES.put("山东实验", "鲁实");
        BookNameNormalizer.PUBLISHES.put("鲁实验", "鲁实");

        BookNameNormalizer.PUBLISHES.put("山东教育", "鲁教");
        BookNameNormalizer.PUBLISHES.put("山东", "鲁教");
        BookNameNormalizer.PUBLISHES.put("鲁教", "鲁教");
        BookNameNormalizer.PUBLISHES.put("鲁", "鲁教");

        BookNameNormalizer.PUBLISHES.put("山东人民教育实验", "鲁实");
        BookNameNormalizer.PUBLISHES.put("山东人民实验", "鲁实");
        BookNameNormalizer.PUBLISHES.put("鲁教实验", "鲁实");
        BookNameNormalizer.PUBLISHES.put("山东教育实验", "鲁实");
        BookNameNormalizer.PUBLISHES.put("山东实验", "鲁实");
        BookNameNormalizer.PUBLISHES.put("鲁实验", "鲁实");

        BookNameNormalizer.PUBLISHES.put("华东师大", "华师");
        BookNameNormalizer.PUBLISHES.put("华师大", "华师");
        BookNameNormalizer.PUBLISHES.put("华师", "华师");
        BookNameNormalizer.PUBLISHES.put("华东", "华师");

        BookNameNormalizer.PUBLISHES.put("北京师大", "北师");
        BookNameNormalizer.PUBLISHES.put("北师大", "北师");
        BookNameNormalizer.PUBLISHES.put("北师", "北师");
        BookNameNormalizer.PUBLISHES.put("京大", "北师");

        BookNameNormalizer.PUBLISHES.put("北京教育", "京教");
        BookNameNormalizer.PUBLISHES.put("京教", "京教");
        BookNameNormalizer.PUBLISHES.put("北京", "京教");

        BookNameNormalizer.PUBLISHES.put("西南师大", "西师");
        BookNameNormalizer.PUBLISHES.put("西南师范", "西师");
        BookNameNormalizer.PUBLISHES.put("西师大", "西师");
        BookNameNormalizer.PUBLISHES.put("西师", "西师");

        BookNameNormalizer.PUBLISHES.put("外研社", "外研");
        BookNameNormalizer.PUBLISHES.put("外研", "外研");

        BookNameNormalizer.PUBLISHES.put("教育科学", "教科");
        BookNameNormalizer.PUBLISHES.put("教科社", "教科");
        BookNameNormalizer.PUBLISHES.put("教科", "教科");

        BookNameNormalizer.PUBLISHES.put("译林牛津", "译林");
        BookNameNormalizer.PUBLISHES.put("译林", "译林");

        BookNameNormalizer.PUBLISHES.put("济南", "济南");
        BookNameNormalizer.PUBLISHES.put("青岛", "青岛");
        BookNameNormalizer.PUBLISHES.put("地图", "地图");
        BookNameNormalizer.PUBLISHES.put("岳麓", "岳麓");
        BookNameNormalizer.PUBLISHES.put("大象", "大象");

        BookNameNormalizer.PUBLISHES.put("人课", "人课");
    }

    /**
     * 对图书名称进行正则化
     *
     * @param bookName 图书名称
     */
    public static String normalize(String bookName) {
        if (LocalStringUtils.isEmpty(bookName)) {
            return bookName;
        } else {
            //1.全角转为半角
            bookName = LocalStringUtils.toDbcCase(bookName);

            //2.将图书名称中连续4位以上的数字串去除
            bookName = peelSequenceNumChar(bookName, NUM_CHAR_SEQUENCE_MIN_LEN);

            //3.将英文字符正则化为小字英文字母
            bookName = normalizeEnglishChar(bookName);

            //4.将数字字符正则化为阿拉伯数字
            bookName = normalizeNumChar(bookName);

            //5.将4位年标准化为2位年
            bookName = normalizeYear(bookName);

            int commentCharLength = COMMENT_CHAR_SEQUENCE_MIN_LEN; //备注性文本长度阈值,默认为社科

            //6.如果是教辅图书，将期转换成：xxxx年级-学科-版别的格式
            if (isStudyBook(bookName)) {
                //标准化为教辅图书的标准形式
                bookName = toStandardStudyBookName(bookName);
                commentCharLength = COMMENT_CHAR_SEQUENCE_MIN_LEN_OF_STUDY; //设置为教辅
            }

             bookName = peelIgnoreItem(bookName);//7.清除无意义的辅助词

            //8.清除备注性文字
             bookName = peelCommentChar(bookName, commentCharLength);


            //5.将中西文特殊字符去除掉
            bookName = peelSpecialChar(bookName);

            return bookName;
        }
    }

    /**
     * 清除在(*)或[*]中的内容超过指定长度的内容
     *
     * @param bookName
     * @param charLengthGreatThan
     * @return
     */
    private static String peelCommentChar(String bookName, int charLengthGreatThan) {
        for (char[] commentChars : COMMENT_CHAR_GROUP) {
            bookName = peelCommentChar(bookName, commentChars[0], commentChars[1]);
        }
        return bookName;
    }

    private static String peelCommentChar(String bookName, char beginChar, char endChar) {
        if (bookName.indexOf(beginChar) > -1 && bookName.indexOf(endChar) > -1) {
            StringBuilder sb = new StringBuilder();
            StringBuilder innerSb = new StringBuilder();
            boolean hasBeginChar = false;
            char tempChar;
            for (int i = 0; i < bookName.length(); i++) {
                tempChar = bookName.charAt(i);
                if (tempChar == beginChar) {
                    hasBeginChar = true;
                }

                if (!hasBeginChar) {
                    sb.append(tempChar);
                } else {
                    innerSb.append(tempChar);
                }

                if (hasBeginChar && tempChar == endChar) {//找到匹配的
                    if (innerSb.length() < COMMENT_CHAR_SEQUENCE_MIN_LEN) {
                        sb.append(innerSb);
                    }
                    innerSb.delete(0, innerSb.length());
                    hasBeginChar = false;
                }
            }
            return sb.toString();
        } else {
            return bookName;
        }
    }

    /**
     * 将4位的年份格式化为2位的年,如将2010格式为10
     *
     * @param bookName
     * @return
     */
    private static String normalizeYear(String bookName) {
        StringBuilder tempBookName = new StringBuilder();
        StringBuilder numChars = new StringBuilder();
        for (int i = 0; i < bookName.length(); i++) {
            if (isNumberChar(bookName.charAt(i))) {
                numChars.append(bookName.charAt(i));
            } else {
                if (numChars.length() == 4) {
                    numChars.delete(0, 2);
                }
                tempBookName.append(numChars);
                tempBookName.append(bookName.charAt(i));
                numChars.delete(0, numChars.length());
            }
        }
        if (numChars.length() == 4) {
            numChars.delete(0, 2);
        }
        if (numChars.length() > 0) {
            tempBookName.append(numChars);
        }

        return tempBookName.toString();
    }

    /**
     * 将greatThanLen以上长度的连续数字串去除
     *
     * @param bookName     图书名称
     * @param greatThanLen 大于此长度连接数字串
     * @return
     */
    private static String peelSequenceNumChar(String bookName, int greatThanLen) {
        StringBuilder tempBookName = new StringBuilder();
        StringBuilder numChars = new StringBuilder();
        for (int i = 0; i < bookName.length(); i++) {
            if (isNumberChar(bookName.charAt(i))) {
                numChars.append(bookName.charAt(i));
            } else {
                if (numChars.length() > 0) {
                    if (numChars.length() <= greatThanLen) {
                        tempBookName.append(numChars);
                    }
                    numChars.delete(0, numChars.length());
                }
                tempBookName.append(bookName.charAt(i));
            }
        }
        if (numChars.length() > 0 && numChars.length() <= greatThanLen) {
            tempBookName.append(numChars);
        }

        return tempBookName.toString();
    }

    /**
     * 将各种类型的数字转换为阿拉伯形式
     * 1.中文数字字符转换
     * 1.1 一~十   中文大写数字
     * 1.2 壹~拾   中文繁体数字
     * <p/>
     * 2.罗马数字字符转换
     * 2.1 大写罗马 Ⅰ~ ?  (8544~8559)
     * 2.2 小写罗马 ?~ ?  (8560~8575)
     * 2.3 英文罗马 i,ii,iii,iv,v,vi,vii,viii,ix,x,xi,xii
     * 3.修饰性数字字符转换
     * 3.1带圆圈阿拉伯数字 ①~?  (9312~9331)
     * 3.2带括号阿拉伯数字 ⑴~⒇-->1-20 9332~9351
     * 3.3带点阿拉伯数字  ⒈~⒛-->1-20  9352~9371
     * 3.4带双圆圈阿拉伯数字 ?~?->1-10 9461~9470
     * 3.5带圆圈中文数字     ?~?-->1-10 12928~12937
     * 3.7带括号的中文数字   ㈠~㈩-->1~10  12832~12841
     *
     * @param bookName
     * @return
     */
    private static String normalizeNumChar(String bookName) {
        StringBuilder tempBookName = new StringBuilder();
        int lastIndex = -1;
        for (int i = 0; i < bookName.length(); i++) {
            int charCode = bookName.codePointAt(i);

            //-----中文数字字符转换----------------------------------------------
            // ? 一~十   中文大写数字  散落区间：19968~ 22235
            if (charCode == 12295 || (charCode >= 19968 && charCode <= 22235)) {
                if (CN_NUMBER_CHARS.containsKey(bookName.charAt(i))) {
                    tempBookName.append(bookName.substring(lastIndex + 1, i));
                    tempBookName.append(CN_NUMBER_CHARS.get(bookName.charAt(i)));
                    lastIndex = i;
                }
            }

            //零，壹~拾 中文繁体数字 散落区间：20237~38470
            if (charCode == 38646 || (charCode >= 20237 && charCode <= 38470)) {
                if (TRADITIONAL_NUMBER_CHARS.containsKey(bookName.charAt(i))) {
                    tempBookName.append(bookName.substring(lastIndex + 1, i));
                    tempBookName.append(TRADITIONAL_NUMBER_CHARS.get(bookName.charAt(i)));
                    lastIndex = i;
                }
            }

            //-----------------------------------------------------------------------

            //-----------罗马数字字符转换--------------------------------------------
            //2.1 大写罗马 Ⅰ~ Ⅻ  (8544~8559)
            if (charCode >= 8544 && charCode <= 8555) {
                tempBookName.append(bookName.substring(lastIndex + 1, i));
                tempBookName.append(charCode - 8544 + 1);
                lastIndex = i;
            } else if (charCode >= 8560 && charCode <= 8571) { //2.2 小写罗马 ?,?,?~?   (8560~8571)
                tempBookName.append(bookName.substring(lastIndex + 1, i));
                tempBookName.append(charCode - 8560 + 1);
                lastIndex = i;
            } else if (charCode >= 9312 && charCode <= 9321) { //3.1带圆圈阿拉伯数字 ①~⑩ (9312~9321)
                tempBookName.append(bookName.substring(lastIndex + 1, i));
                tempBookName.append(charCode - 9312 + 1);
                lastIndex = i;
            } else if (charCode >= 9332 && charCode <= 9351) {  // 3.2带括号阿拉伯数字 ⑴~⒇-->1-20 9332~9351
                tempBookName.append(bookName.substring(lastIndex + 1, i));
                tempBookName.append(charCode - 9332 + 1);
                lastIndex = i;
            } else if (charCode >= 9352 && charCode <= 9371) { // 3.3带点阿拉伯数字  ⒈~⒛-->1-20  9352~9371
                tempBookName.append(bookName.substring(lastIndex + 1, i));
                tempBookName.append(charCode - 9352 + 1);
                lastIndex = i;
            }
            //TODO：要改成UTF-8编码
//            else if(charCode >= 10102 && charCode <= 10111){//?~?[10102~10111]-->1~10
//                tempBookName.append(bookName.substring(lastIndex + 1, i));
//                tempBookName.append(charCode - 10102 + 1);
//                lastIndex = i;
//            }else if(charCode >= 9451 && charCode <= 9460){ // ?~?[9451-9460]-->11~20
//                tempBookName.append(bookName.substring(lastIndex + 1, i));
//                tempBookName.append(charCode - 9451 + 11);
//                lastIndex = i;
//            }
//            else  if (charCode >= 9461 && charCode <= 9470) {  // 3.4带双圆圈阿拉伯数字 ?~?->1-10 9461~9470
//                tempBookName.append(bookName.substring(lastIndex + 1, i));
//                tempBookName.append(charCode - 9461 + 1);
//                lastIndex = i;
//            }else  if (charCode >= 12928 && charCode <= 12937) { // 3.5带圆圈中文数字     ?~?-->1-10 12928~12937
//                tempBookName.append(bookName.substring(lastIndex + 1, i));
//                tempBookName.append(charCode - 12928 + 1);
//                lastIndex = i;
//            }
            else if (charCode >= 12832 && charCode <= 12841) {// 3.6带括号的中文数字   ㈠~㈩-->1~10  12832~12841
                tempBookName.append(bookName.substring(lastIndex + 1, i));
                tempBookName.append(charCode - 12832 + 1);
                lastIndex = i;
            }
        }
        if (lastIndex > -1) {
            if (lastIndex < bookName.length() - 1) {
                tempBookName.append(bookName.substring(lastIndex + 1));
            }
        } else {
            tempBookName.append(bookName);
        }

        //2.3 英文罗马 i,ii,ii,iv,v,vi,vii,viii,ix,x,xi,xii
        bookName = tempBookName.toString();
        if (bookName.indexOf("i") > 0 || bookName.indexOf("v") > 0 || bookName.indexOf("x") > 0) {
            for (Map.Entry<String, String> entry : ROMAN_NUMS.entrySet()) {
                if (bookName.indexOf(entry.getKey()) > 0) {
                    bookName = bookName.replaceAll(entry.getKey(), entry.getValue());
                }
            }
        }
        return bookName;
    }

    /**
     * 正则化英文字母
     * 一、将大写字母转换为小写；
     * A-Z (65~90)--> a-z
     * 二、将修饰性字母转换为小写字母
     * 1.?~?(9424~9449) -->a~z
     * 2.?~?(9372~9397) -->a-Z
     * 3.?~?(9398~9423) -->a-z
     * 其中a-z为97~122
     */
    private static String normalizeEnglishChar(String bookName) {
        StringBuilder tempBookName = new StringBuilder();
        for (int i = 0; i < bookName.length(); i++) {
            int charCode = bookName.codePointAt(i);
            if (charCode >= 65 && charCode <= 90) {//大写转换为小写
                tempBookName.append((char) (charCode - 65 + 97));
            }
//            else if (charCode >= 9424 && charCode <= 9449) {//?~?(9424~9449)
//                tempBookName.append((char) (charCode - 9424 + 97));
//            } else if (charCode >= 9372 && charCode <= 9397) {//?~?(9372~9397)
//                tempBookName.append((char) (charCode - 9372 + 97));
//            } else if (charCode >= 9398 && charCode <= 9423) {//?~?(9398~9423)
//                tempBookName.append((char) (charCode - 9398 + 97));
//            }
            else {
                tempBookName.append((char) charCode);
            }
        }
        return tempBookName.toString();

    }

    /**
     * 将图书名称中,{@link #IGNORE_ITEMS}的字符移除
     *
     * @param bookName
     * @return
     */
    private static String peelIgnoreItem(String bookName) {
       StringBuilder sb = new StringBuilder(bookName);
       int beginIndex = -1;
       for(String ignoreItem:IGNORE_ITEMS){
           while ((beginIndex = sb.indexOf(ignoreItem)) > -1){
               sb.replace(beginIndex,beginIndex+ignoreItem.length(),"");
           }
       }
       return sb.toString();
    }



    /**
     * 转换成标准的教辅图书名称格式，即xxxx<年级>-<学科>-<版别>
     *
     * @param bookName
     * @return
     */
    private static String toStandardStudyBookName(String bookName) {
        String srcBookName = bookName;
        bookName = toStudyBookGrade(bookName);
        bookName = toStudyBookSubject(bookName);
        bookName = toStudyBookPublish(bookName);


        //处理特殊情况
        if(srcBookName.indexOf(STUDY_BOOK_NKB) > -1){ //如果带"新课"字样,则在版别后 添加 N字样
           bookName += "N";
        }
        return bookName;
    }

    /**
     * 对包含学科的教辅图书名称进行转换
     *
     * @param bookName
     * @return
     */
    private static String toStudyBookSubject(String bookName) {
        int lastIndex = -1;
        int currIndex = -1;
        String subjectCode = "x";
        StringBuilder sb = new StringBuilder();
        int bookNameLen = bookName.length();
        for (Map.Entry<String, String> entry : SUBJECTS.entrySet()) {
            currIndex = bookName.indexOf(entry.getKey());
            if (currIndex > -1) {
                subjectCode = entry.getValue();
                sb.append(bookName.substring(0, currIndex));
                lastIndex = currIndex + entry.getKey().length() - 1;
                break;
            }
        }
        if (lastIndex + 1 < bookNameLen) {
            sb.append(bookName.substring(lastIndex + 1));
        }
        sb.append(STUDY_BOOK_SPLIER);
        sb.append(subjectCode);
        return sb.toString();
    }

    /**
     * 对包含版别的信息教辅图书名称进行转换
     * 注意，由于语文即是科目 也是版别，所以在判断时必须判断是否已经作为科目，如果已经做为科目则
     * 版别为x
     *
     * @param bookName
     * @return
     */
    private static String toStudyBookPublish(String bookName) {
        int lastIndex = -1;
        int currIndex = -1;
        String subjectCode = "x";
        StringBuilder sb = new StringBuilder();
        int bookNameLen = bookName.length();
        for (Map.Entry<String, String> entry : PUBLISHES.entrySet()) {
            currIndex = bookName.indexOf(entry.getKey());
            if (currIndex > -1 && currIndex + entry.getKey().length() < bookNameLen) {
                subjectCode = entry.getValue();
                sb.append(bookName.substring(0, currIndex));
                lastIndex = currIndex + entry.getKey().length() - 1;
                break;
            }
        }
        if (lastIndex + 1 < bookNameLen) {
            sb.append(bookName.substring(lastIndex + 1));
        }
        sb.append(STUDY_BOOK_SPLIER);
        sb.append(subjectCode);
        return sb.toString();
    }

    /**
     * 将教辅图书转换成统一的年级格式，即1~18册的格式
     * <pre>
     * 一、将年级转换为第M册的形式
     * 1.将"N年"的转为以"第M册"的形式，如果图书的名称包含“N年”但非“NN年" 且包括“下”则 M=2*N 否则 M=2*N-1
     * 2.对于“初N”的转换规则为：如果图书名称包含"下",则 M=12+2*N  否则 M=12+2*N-1
     * 3.对于“高N”的转换规则为：如果图书名称包含"下",则 M=2*N  否则  M=2*N-1
     * 4.对于“学前”的转换规则为：如果图书名称包含"下",则 M=2*1  否则 M=2*1-1
     * 5.对于“小班”的转换规则为：如果图书名称包含"下",则 M=2*2  否则 M=2*2-1
     * 6.对于“中班”的转换规则为：如果图书名称包含"下",则 M=2*3  否则 M=2*3-1
     * 7.对于“大班”的转换规则为：如果图书名称包含"下",则 M=2*4  否则 M=2*4-1
     *
     * 二、如果图书名称中还有第M册 则将其清除，如果第一步没有分析出来，则使用第M册的进行分析之
     *
     * 三、如果存在N年时，将“上”或"下"的字删除之
     * </pre>
     *
     * @param bookName
     * @return
     */
    private static String toStudyBookGrade(String bookName) {
        String srcBookName = bookName;
        //对高中，初中，小学 ，幼儿班进行年级转换
        ParseBookName parseBookName = toStudyBookGradeOfZxy(new ParseBookName(bookName));

        //对学前班进行年级转换
        if (!parseBookName.isParsed()) {
            parseBookName = toStudyBookGradeOfXqb(parseBookName);
        }

        //对NN册进行处理
        if (!parseBookName.isParsed()) {
            parseBookName = toStudyBookGradeOfVolumeNum(parseBookName);
        }

        StringBuilder sb = new StringBuilder(parseBookName.getBookName());
        if (!parseBookName.isParsed()) {//没有年级的信息
            sb.append(STUDY_BOOK_SPLIER)
              .append('0');
        }

        //所有标准的N年
        if (parseBookName.isParsed()) {
            bookName = sb.toString();
            if(srcBookName.indexOf(STUDY_BOOK_VOL) == -1 ){ //如果有未带"册"字,上,下字眼不删除
                bookName = bookName.replaceAll("上", "");
                bookName = bookName.replaceAll("下", "");
            }
        }
        return bookName;
    }

    /**
     * 对xxxNN册yyy处理，将NN册转换为年级
     *
     * @param parseBookName
     * @return
     */
    private static ParseBookName toStudyBookGradeOfVolumeNum(ParseBookName parseBookName) {
        StringBuilder sb = new StringBuilder();
        StringBuilder volumeNumSb = new StringBuilder();
        int layer = 0;
        int volumeIndex = -1;
        int volumeNumLen = 2;


        //开始分析
        for (int i = 0; i < parseBookName.getBookNameLength(); i++) {//N册
            char tempChar1 = parseBookName.getBookName().charAt(i);
            if (tempChar1 == '册') {
                volumeIndex = i;
                while (--volumeIndex > -1 && --volumeNumLen > -1) {//获取“册”字前最多两位数据
                    char tempChar2 = parseBookName.getBookName().charAt(volumeIndex);
                    if (isNumberChar(tempChar2)) {
                        volumeNumSb.append(tempChar2);
                    } else {
                        break;
                    }
                }
                if (volumeNumSb.length() > 0) {
                    break;
                }
            }
        }

        if (volumeNumSb.length() > 0) {//说明有册数信息
            parseBookName.setParsed(true);
            volumeNumSb.reverse();
            sb.append(parseBookName.getBookName().substring(0, volumeIndex + 1));//截取“xxx4册yyy”的xxx的内容
            //截取“xxx4册yyy”的yyy的内容
            if (volumeIndex + volumeNumSb.length() + 1 < parseBookName.getBookNameLength() - 1) {
                sb.append(parseBookName.getBookName().substring(volumeIndex + volumeNumSb.length() + 2));
            }
            layer = Integer.parseInt(volumeNumSb.toString());
            sb.append(STUDY_BOOK_SPLIER);
            sb.append(layer);
            parseBookName.setParsed(true);
            parseBookName.setBookName(sb.toString());
        }
        return parseBookName;
    }

    /**
     * 将有“学前”字样的转换为年级
     *
     * @param parseBookName
     * @return
     */
    private static ParseBookName toStudyBookGradeOfXqb(ParseBookName parseBookName) {
        StringBuilder sb = new StringBuilder();
        int layer = 0;
        int tempIndex = parseBookName.getBookName().indexOf("学前");
        if (tempIndex > -1) { //有包含学前字样
            parseBookName.setParsed(true);
            if (tempIndex > 0) {
                sb.append(parseBookName.getBookName().substring(0, tempIndex - 1));
            }
            if (tempIndex + 2 < parseBookName.getBookNameLength() - 1) {
                sb.append(parseBookName.getBookName().substring(tempIndex + 2));
            }
            if (parseBookName.getBookName().indexOf('下') > -1) {
                layer = 2;
            } else {
                layer = 1;
            }
            parseBookName.setParsed(true);
            sb.append(STUDY_BOOK_SPLIER);
            sb.append(layer);
            parseBookName.setBookName(sb.toString());
        }
        return parseBookName;
    }

    /**
     * 获取高、初、小学及幼儿班对应的年级
     *
     * @param bookName
     * @return
     */
    private static ParseBookName toStudyBookGradeOfZxy(ParseBookName parseBookName) {
        StringBuilder sb = new StringBuilder();
        boolean parsed = false;
        boolean hasGradePart = false;//是否有"N年"的特征
        int bookNameLen = parseBookName.getBookNameLength();
        int layer = 0;
        char tempChar1, tempChar2;

        for (int i = 0; i < parseBookName.getBookNameLength(); i++) {//是“N年”而不是"NN年"
            tempChar1 = parseBookName.getBookName().charAt(i);
            if (tempChar1 == '年') {
                if (i > 0) {
                    tempChar2 = parseBookName.getBookName().charAt(i - 1);
                    if (isNumberChar(tempChar2) && ((i == 1) || (i >= 2 && !isNumberChar(parseBookName.getBookName().charAt(i - 2))))) {
                        hasGradePart = true;
                        parsed = true;
                        sb.append(parseBookName.getBookName().substring(0, i - 1));
                        if (i < bookNameLen - 1) {
                            sb.append(parseBookName.getBookName().substring(i + 1));
                        }
                        if (parseBookName.getBookName().indexOf('下') > -1) {
                            layer = 2 * parseInt(tempChar2);
                        } else {
                            layer = 2 * parseInt(tempChar2) - 1;
                        }
                    }
                }
            } else if (tempChar1 == '初' || tempChar1 == '高') {
                if (i < bookNameLen - 1) {
                    tempChar2 = parseBookName.getBookName().charAt(i + 1);
                    if (isNumberChar(tempChar2)) {
                        hasGradePart = true;
                        parsed = true;
                        sb.append(parseBookName.getBookName().substring(0, i));
                        if (i < bookNameLen - 2) {
                            sb.append(parseBookName.getBookName().substring(i + 2));
                        }

                        if (parseBookName.getBookName().indexOf('下') > -1) {
                            layer = 2 * parseInt(tempChar2);
                        } else {
                            layer = 2 * parseInt(tempChar2) - 1;
                        }
                        if (tempChar1 == '初') {
                            layer += 12;
                        }
                    }
                }
            } else if (tempChar1 == '班') {
                if (i > 0) {
                    tempChar2 = parseBookName.getBookName().charAt(i - 1);
                    int number = -1;
                    if (tempChar2 == '小') {
                        hasGradePart = true;
                        number = 2;
                    } else if (tempChar2 == '中') {
                        hasGradePart = true;
                        number = 3;
                    } else if (tempChar2 == '大') {
                        hasGradePart = true;
                        number = 4;
                    }
                    if (number > -1) {
                        parsed = true;
                        sb.append(parseBookName.getBookName().substring(0, i - 1));
                        if (i < bookNameLen - 1) {
                            sb.append(parseBookName.getBookName().substring(i + 1));
                        }
                        if (parseBookName.getBookName().indexOf('下') > -1) {
                            layer = 2 * number;
                        } else {
                            layer = 2 * number - 1;
                        }
                    }
                }
            }
            if (parsed) {//已经分析出来
                sb.append(STUDY_BOOK_SPLIER);
                sb.append(layer);
                parseBookName.setBookName(sb.toString());
                parseBookName.setParsed(true);
                break;
            }
        }
        return parseBookName;
    }

    /**
     * 将数据字符转换成其代表的整数
     *
     * @param numChar
     * @return
     */
    private static int parseInt(char numChar) {
        return numChar - ZERO_ASCII_VALUE;
    }

    /**
     * 判断字符是否是数字
     *
     * @param c
     * @return
     */
    private static boolean isNumberChar(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * // 将特殊字符删除掉
     * //(1)特殊字符ASCII码段
     * // 0~47
     * //58~64
     * //91~96
     * //123~255
     * //(2)CJK符号和标点移除 12288~12351
     *
     * @return
     */
    private static String peelSpecialChar(String bookName) {
        String srcBookName = bookName.replaceAll(WHITE_BLANK,"");
        StringBuilder tempBookName = new StringBuilder();
        int lastIndex = -1;
        for (int i = 0; i < bookName.length(); i++) {
            int charCode = bookName.codePointAt(i);
            if ((charCode >= 0 && charCode <= 47) ||
                    (charCode >= 58 && charCode <= 64) ||
                    (charCode >= 91 && charCode <= 96) ||
                    (charCode >= 123 && charCode <= 255) ||
                    (charCode >= 65280 && charCode <= 65519) ||
                    (charCode >= 8192 && charCode <= 8303) ||
                    (charCode >= 12288 && charCode <= 12351)
                    ) {
                tempBookName.append(bookName.substring(lastIndex + 1, i));
                lastIndex = i;
            }
        }
        if (lastIndex > -1) {
            if (lastIndex < bookName.length() - 1) {
                tempBookName.append(bookName.substring(lastIndex + 1));
            }
        } else {
            tempBookName.append(bookName);
        }
        bookName = tempBookName.toString();

        //如果包含特殊项,如C++,或C# 则添加区分符号
        for (Map.Entry<String, String> specialItem : SPECIAL_ITEMS.entrySet()) {
            if (srcBookName.indexOf(specialItem.getKey()) > -1){
                bookName += specialItem.getValue();
            }
        }

        return bookName;
    }

    /**
     * 是否是教辅图书
     * <pre>
     * 1.图书名称中包含以下的关键字
     * 小学
     * 中学
     * 初中
     * 高中
     * 初1
     * 初2
     * 初3
     * 高1
     * 高2
     * 高3
     * 学前
     * 小班
     * 中班
     * 大班
     * 小考
     * 初考
     * 中考
     * 高考
     * 期末
     * 期中
     * 2.图书名称包含“N年"的特征码，但不包含"NN年"的特殊码
     * </pre>
     *
     * @param bookName
     * @return
     */
    private static boolean isStudyBook(String bookName) {
        char tempChar1, tempChar2, tempChar3;
        int bookNameLen = bookName.length();
        for (int i = 0; i < bookName.length(); i++) {
            tempChar1 = bookName.charAt(i);
            if (tempChar1 == '小' || tempChar1 == '中' || tempChar1=='大') {
                if (i < bookNameLen - 1) {
                    tempChar2 = bookName.charAt(i + 1);
                    if (tempChar2 == '学' || tempChar2 == '班' || tempChar2 == '考') {
                        return true;
                    }
                }
            } else if (tempChar1 == '初' || tempChar1 == '高') {
                if (i < bookNameLen - 1) {
                    tempChar2 = bookName.charAt(i + 1);
                    if (tempChar2 == '中' || tempChar2 == '考' || (tempChar2 >= '1' && tempChar2 <= '3')) {
                        return true;
                    }
                }
            } else if (tempChar1 == '期') {
                if (i < bookNameLen - 1) {
                    tempChar2 = bookName.charAt(i + 1);
                    if (tempChar2 == '末' || tempChar2 == '中') {
                        return true;
                    }
                }
            } else if (tempChar1 == '年') {//1年~9年 但不是 2001年~2009年 这咱情况
                if (i > 0) {
                    tempChar2 = bookName.charAt(i - 1);
                    if (isNumberChar(tempChar2) && ((i == 1) || (i >= 2 && !isNumberChar(bookName.charAt(i - 2))))) {
                        return true;
                    }
                }
            }
        }
        for(String charactStr: STUDY_BOOK_CHARACTS){//是否包含特殊码
            if(bookName.indexOf(charactStr) > -1){
                return true;
            }
        }

        return false;
    }

    /**
     * 带状态的图书名称
     */
    private static class ParseBookName {
        private String bookName;
        private boolean parsed;

        private ParseBookName(String bookName) {
            this.bookName = bookName;
            this.parsed = false;
        }

        public boolean isParsed() {
            return parsed;
        }

        public void setParsed(boolean parsed) {
            this.parsed = parsed;
        }

        public String getBookName() {
            return bookName;
        }

        public int getBookNameLength() {
            if (getBookName() != null) {
                return getBookName().length();
            } else {
                return 0;
            }
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }
    }

    public static void main(String[] args) {
//        "级", "册", "班", "第", "卷", "篇", "期", "辑", "版","新",
//                                                      "学期", "年","集","修","订","著","系列","总","次",
//                                                       "vol","年","月","旬","季","双","随书","配","送","赠", "附",
//                                                       "附","含","rom","cd","dvd",",mp3","mp4","光盘","1磁带",
//                                                       "注音","插图", "退",
//                                                       "1张","2张","1盘","2盘","1带","2带", "带","盘","磁", "盒",
//                                                       "珍藏","签名","本","优惠","券",
//                                                       "答案","印刷","签名珍藏版","特价/封底打有圆孔","刊"
        String bookName = "疯狂英语原声版-磁带 2011-01";
        System.out.println(BookNameNormalizer.normalize(bookName));
    }

}
