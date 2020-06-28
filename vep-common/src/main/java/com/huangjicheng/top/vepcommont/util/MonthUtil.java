package com.huangjicheng.top.vepcommont.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: huangjicheng
 * @Date: 2020/6/18 23:36
 */

public class MonthUtil {
    /**
     * 获去当前的前n个月
     * @param size
     * @return
     */
    public static List<String> getLastMonths(int size) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        List<String> list = new ArrayList(size);
        for (int i=0;i<size;i++) {
            c.setTime(new Date());
            c.add(Calendar.MONTH, -i);
            Date m = c.getTime();
            list.add(sdf.format(m));
        }
        Collections.reverse(list);
        return list;
    }
}
