package org.base.utils;

import cn.hutool.extra.pinyin.PinyinUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static String reg_cardno_18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";
    private static String reg_cardno_15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    private static String reg_phone = "^(12[0-9]|13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\d{8}$";

    public static boolean isEmpty(String strIn) {
        return strIn == null || "".equals(strIn) || "".equals(strIn.trim());
    }

    public static boolean isNotEmpty(String strIn) {
        return !isEmpty(strIn);
    }

    /**
     * @Description: 验证身份证号
     * @author 白海安 （baihaian@126.com） 2016年6月15日 下午3:28:32
     * @version V1.0
     */
    public static boolean validataCardNo(String cardNo) {
        // 编译正则表达式
        Pattern p = Pattern.compile(reg_cardno_18);
        Pattern p2 = Pattern.compile(reg_cardno_15);
        Matcher m = p.matcher(cardNo);
        Matcher m2 = p2.matcher(cardNo);
        if (m.matches() || m2.matches()) {
            return true;
        }
        return false;
    }

    public static boolean validateIsInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @Description: 验证手机号
     * @author 白海安 （baihaian@126.com） 2016年6月15日 下午3:28:24
     * @version V1.0
     */
    public static boolean ValidatePhone(String phone) {
        if (isEmpty(phone)) {
            return true;
        }
        Pattern p = Pattern.compile(reg_phone);
        Matcher m = p.matcher(phone);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * @Description: 去除重复字符串
     * @author linyl(linyl @ jizhicar.com) 2022/10/8 14:17
     * @version V1.0
     */
    public static String removeRepeat(String str) {
        try {
            if (isEmpty(str)) {
                return "";
            }
            TreeSet<String> ts = new TreeSet<>();
            String[] split = str.split(",");
            for (int i = 0; i < split.length; i++) {
                ts.add(split[i]);
            }
            Iterator<String> iterator = ts.iterator();
            StringBuffer sb = new StringBuffer();
            while (iterator.hasNext()) {
                sb.append(iterator.next() + ",");
            }
            return sb.toString().substring(0, sb.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 数组中是否包含某个元素
     * @author linyl(linyl @ jizhicar.com) 2022/10/8 14:18
     * @version V1.0
     */
    public static boolean arrayContainsStr(String[] arr, String target) {
        if (arr.length == 0 || isEmpty(target)) {
            return false;
        }
        return Arrays.asList(arr).contains(target);
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Long date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static String join(Collection<?> collection, String separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), separator);
    }

    public static String join(Iterator<?> iterator, String separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return "";
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return first == null ? "" : first.toString();
        }
        StringBuffer buf = new StringBuffer(256); // Java default is 16,
        // probably too small
        if (first != null) {
            buf.append(first);
        }
        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    /**
     * @author wh 2021/9/1 10:45
     * @version V1.0
     * @Description: 转换为日期格式yyyy-MM-dd
     */
    public static String getDayDate(String format) {
        Date date = new Date();
        SimpleDateFormat dft = new SimpleDateFormat(format);
        return dft.format(date);
    }

    public static void main(String[] args) {
        System.out.println(getNamePinyin("白"));
    }

    /**
     * @Description 昨天
     * @author 白海安 （baiha@jizhicar.com） 2017年4月1日 上午11:22:32
     * @version V1.5
     */
    public static String getPrecedingDay(String date, int dayCount) {
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(date2TimeStamp2(date, "yyyy-MM-dd"));
        time.add(Calendar.DATE, dayCount);
        Date afterDate = (Date) time.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(afterDate);
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Long date2TimeStamp2(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * @Description: 验证字符串的长度
     * @author 白海安 （baihaian@126.com） 2016年7月5日 上午10:01:03
     * @version V1.0
     */
    public static boolean ValidateStrLength(String strIn, int length) {
        if (isEmpty(strIn)) {
            return true;
        }
        if (strIn.length() > length) {
            return false;
        } else {
            return true;
        }
    }

    public static String getDateStr(Long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (time == null) {
            return null;
        }
        return sdf.format(new Date(time * 1000));
    }

    public static String getKey(String originalFilename) {
        String key = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        // 获取图片后缀
        int index = originalFilename.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        String imgExt = "jpg|jpeg|png|bmp|gif|cdr|svg|raw|zip|rar|war|tar|cab|7z|pdf|doc|docx|dot|dotx|docm|dotm|xls|xlsm|xlsx|pages|txt|pptx|ppt|xps|mp4|mp3|m4a|wav";
        String suffix = originalFilename.substring(index + 1).toLowerCase();
        // 校验文件格式
        if (imgExt.indexOf(suffix.toLowerCase()) < 0) {
            return "";
        }
        return key + "." + suffix;
    }

    /**
     * @Description 传入日期与当前时间差值
     * @author 白海安 （baiha@jizhicar.com） 2017年4月1日 上午11:22:32
     * @version V1.5
     */
    public static long timeDifference(String targetDate) {
        LocalDate now = LocalDate.now();
        LocalDate target = LocalDate.parse(targetDate);
        return target.toEpochDay() - now.toEpochDay();
    }

    /**
     * @Description 获取明天时间戳
     * @author 白海安 （baiha@jizhicar.com） 2017年4月1日 上午11:22:32
     * @version V1.5
     */
    public static Long getDayTimeMillis(String date, int dayCount) {
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(date2TimeStamp2(date, "yyyy-MM-dd"));
        time.add(Calendar.DATE, dayCount);
        return time.getTimeInMillis() / 1000;
    }

    public static String getNamePinyin(String name) {
        if (isEmpty(name)) {
            return null;
        }
        if (name.length() == 1) {
            return PinyinUtil.getPinyin(name);
        } else {
            return (PinyinUtil.getPinyin(name.substring(0, 1)) + "." + PinyinUtil.getPinyin(name.substring(1))).replaceAll(" ", "");
        }
    }


}
