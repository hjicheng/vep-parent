package com.huangjicheng.top.vepcommont.util;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/6/7 19:16
 */

public class CsvUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvUtil.class);

    public static final String ENCODE = "UTF-8";

    /**
     * readCsv:根据路径读取CSV文件.<br/>
     *
     * @param csvFilePath
     * @return
     */
    public static List<String[]> readCsv(String csvFilePath) {
        List<String[]> csvList = new ArrayList<String[]>();
        try {
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName(ENCODE));
            /** 跳过表头 如果需要表头的话，不要写这句 */
            reader.readHeaders();
            String[] strings = null;
            while (reader.readRecord()) {
                strings = reader.getValues();
                csvList.add(strings);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("read csv file error. : {}", e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("read csv file error. : {}", e.getLocalizedMessage());
        }
        return csvList;
    }

    /**
     * readCsv:根据数据流读取CSV文件.<br/>
     *
     * @param csvIs
     * @return
     */
    public static List<String[]> readCsv(InputStream csvIs) {

        List<String[]> csvList = new ArrayList<String[]>();
        try {
            CsvReader reader = new CsvReader(csvIs, Charset.forName(ENCODE));
            /** 跳过表头 如果需要表头的话，不需要写这句 */
            reader.readHeaders();
            while (reader.readRecord()) {
                csvList.add(reader.getValues());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("read csv inputStream error. : {}", e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("read csv inputStream error. : {}", e.getLocalizedMessage());
        }
        return csvList;
    }

    /**
     * writeCsv:把内容写入到文件中.<br/>
     *
     * @param csvFilePath
     * @param contents
     */
    public static void writeCsv(String csvFilePath, List<String[]> contents) {
        try {
            CsvWriter wr = new CsvWriter(csvFilePath, ',', Charset.forName(ENCODE));
            for (int i = 0; i < contents.size(); i++) {
                wr.writeRecord(contents.get(i));
            }
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("write csv file error. : {}", e.getLocalizedMessage());
        }
    }

    /**
     * writeCsv:把内容写到输出流.<br/>
     *
     * @param ou
     * @param list
     */
    public static void writeCsv(OutputStream ou, List<String[]> list) {
        CsvWriter cw = null;
        try {
            cw = new CsvWriter(ou, ',', Charset.forName(ENCODE));
            for (String[] s : list) {
                cw.writeRecord(s);
            }
            // 在文件中增加BOM,该处的byte[] 可以针对不同编码进行修改
            ou.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            cw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("write csv outputStream error. : {}", e.getLocalizedMessage());
        } finally {
            if (null != cw) {
                cw.close();
            }
        }

    }


}
