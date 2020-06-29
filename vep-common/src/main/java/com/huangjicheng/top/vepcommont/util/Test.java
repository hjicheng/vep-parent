package com.huangjicheng.top.vepcommont.util;

import com.alibaba.fastjson.JSON;
import com.csvreader.CsvReader;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.huangjicheng.top.vepcommont.util.CsvUtil.ENCODE;

/**
 * @Author: huangjicheng
 * @Date: 2020/6/20 23:39
 */

public class Test {

    public static void main(String[] args) {
        byte[] buffer = new byte[1024];
        int count = 0;
//        File file = new File("D:\\DeskTop\\jd_book.csv");
        List<String[]> list = readCsv("D:\\DeskTop\\books.csv");

        for (int i = 0; i < list.size(); i++) {
//            Map maps = (Map)JSON.parse(list.get(i).toString());
            System.out.println(list.get(i)[1]);
//            System.out.print(list.get(i)[1]);
//            System.out.print(list.get(i)[2]);
//            System.out.print(list.get(i)[3]);
//            System.out.print(list.get(i)[4]);
//            System.out.print(list.get(i)[5]);
//            System.out.print(list.get(i)[6]);
//            System.out.print(list.get(i)[7]);
        }
    }

    public static List<String[]> readCsv(String csvFilePath) {
        List<String[]> csvList = new ArrayList<String[]>();
        try {
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName(ENCODE));
            String[] strings = null;
            while (reader.readRecord()) {
                strings = reader.getValues();
                csvList.add(strings);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvList;
    }

}
