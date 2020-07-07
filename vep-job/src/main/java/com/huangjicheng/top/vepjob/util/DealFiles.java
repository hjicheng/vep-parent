package com.huangjicheng.top.vepjob.util;

import com.alibaba.fastjson.JSON;
import com.huangjicheng.top.vepapi.service.FilesService;
import com.huangjicheng.top.vepcommont.util.CsvUtil;
import com.huangjicheng.top.vepcommont.util.StringUtils;
import com.huangjicheng.top.vepdao.entity.Files;
import com.huangjicheng.top.vepjob.entity.Books;
import com.huangjicheng.top.vepjob.entity.ElasticSearchEntity;
import com.huangjicheng.top.vepjob.jobhandler.ReadFile;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/6/21 14:09
 */

public class DealFiles {

    private static Logger logger = LoggerFactory.getLogger(DealFiles.class);
    private static String task = "vep项目读取文件......";


    /**
     * 处理文件
     *
     * @param files
     */
    public static void dealFile(Files files, FilesService filesService, RestHighLevelClient restHighLevelClient) {
        logger.info(task + "开始读文件:" + files.getFileName());
        // 读取csv文件
        List<String[]> list = CsvUtil.readCsv(files.getPath());

        // 把数据放入索引
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout(TimeValue.timeValueHours(1));

        String filePlat = files.getFileName().split("\\.")[0].split("_")[1];

        String esType = "";
        if ("书籍".equals(files.getFileType())) {
            esType = "file_book_" + filePlat;
            bulkRequest = dealBooksFile(list, esType,files.getFileType()+"_"+filePlat);
        } else if ("工作".equals(files.getFileType())) {
            esType = "file_job_" + filePlat;
        } else if ("商品".equals(files.getFileType())) {
            esType = "file_goods_" + filePlat;
        }

        BulkResponse bulk = null;
        try {
            bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(task + "读取文件--" + bulk.hasFailures());


        if (!bulk.hasFailures()) {
            files.setState(1);
            files.setUpdateTime(new Date());
        }
        filesService.update(files);
        logger.info(task + "读取文件结束");

    }

    public static BulkRequest dealBooksFile(List<String[]> list, String esType,String type) {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < list.size(); i++) {
            Books books = new Books();
            books.setBCate(list.get(i)[0]);
            books.setHref(list.get(i)[1]);
            books.setSCate(list.get(i)[2]);
            books.setBookImg(list.get(i)[3]);
            books.setBookName(list.get(i)[4]);
            books.setBookAuthor(list.get(i)[5]);
            books.setBookStore(list.get(i)[6]);
            books.setBookDate(list.get(i)[7]);
            books.setBookPrice(list.get(i)[8]);
            books.setCreateTime(new Date());
            if (StringUtils.isNotBlank(books.getBCate())
                    && StringUtils.isNotBlank(books.getHref()) &&
                    StringUtils.isNotBlank(books.getSCate()) &&
                    StringUtils.isNotBlank(books.getBookImg()) &&
                    StringUtils.isNotBlank(books.getBookDate()) &&
                    StringUtils.isNotBlank(books.getBookName()) &&
                    StringUtils.isNotBlank(books.getBookAuthor()) &&
                    StringUtils.isNotBlank(books.getBookStore()) &&
                    StringUtils.isNotBlank(books.getBookPrice())) {
                ElasticSearchEntity elasticSearchEntity = new ElasticSearchEntity();
                elasticSearchEntity.setType(type);
                elasticSearchEntity.setName(books.getBookName());
                elasticSearchEntity.setValue(books.toString());
                elasticSearchEntity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                bulkRequest.add(new IndexRequest(esType).source(JSON.toJSONString(elasticSearchEntity), XContentType.JSON));
            }
        }
        return bulkRequest;
    }
}
