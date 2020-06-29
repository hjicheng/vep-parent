package com.huangjicheng.top.vepcommont.util;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: huangjicheng
 * @Date: 2020/6/13 11:09
 */

public class StringUtils implements Serializable {


    private static final long serialVersionUID = -4966661076018401794L;

    /**
     * 把一个字符串分割成一个int[]
     *
     * @param str  字符串
     * @param sign 分割标准
     * @return
     */
    public final static int[] strToInts(String str, String sign) {
        if (StringUtils.isBlank(str) || sign == null) {
            return null;
        }
        String[] vals = str.split(sign);
        int size = vals.length;
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            String val = vals[i];
            if (StringUtils.isNotBlank(val)) {
                data[i] = Integer.valueOf(val.trim());
            }
        }
        return data;
    }

    public static String getUniqueKey(String serviceName) {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return serviceName + "_" + s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 把一个字符串分割成一个List<Integer>
     *
     * @param str
     * @param sign 各个标准
     * @return
     */
    public final static List<Integer> strToIntList(String str, String sign) {
        if (StringUtils.isBlank(str) || sign == null) {
            return null;
        }
        String[] vals = str.split(sign);
        int size = vals.length;
        List<Integer> data = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            String val = vals[i];
            if (StringUtils.isNotBlank(val)) {
                data.add(Integer.valueOf(val.trim()));
            }
        }
        return data;
    }

    /**
     * 是否为null
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if (str == null) {
            return true;
        }
        return false;
    }

    /**
     * 不为null
     *
     * @param str
     * @return
     */
    public static boolean isNotNull(String str) {
        return !StringUtils.isNull(str);
    }

    /**
     * 是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (!StringUtils.isNull(str) && !"".equals(str.trim())) {
            return false;
        }
        return true;
    }

    /**
     * 不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    /**
     * Integer[] 转 String[] 数组
     */
    public static String[] merge(Integer[]... arr) {
        Set<Integer> set = new TreeSet<Integer>();
        for (Integer[] integers : arr) {
            for (Integer integer : integers) {
                set.add(integer);
            }
        }
        String[] strArrays = new String[set.size()];
        int i = 0;
        for (Integer integer : set) {
            strArrays[i++] = integer.toString();
        }
        return strArrays;
    }

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};


    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 6; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    public static String join(Collection collection, String split) {
        StringBuffer stringBuffer = new StringBuffer();

        for (Iterator var3 = collection.iterator(); var3.hasNext(); stringBuffer.append((String) var3.next())) {
            if (stringBuffer.length() != 0) {
                stringBuffer.append(split);
            }
        }

        return stringBuffer.toString();
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
