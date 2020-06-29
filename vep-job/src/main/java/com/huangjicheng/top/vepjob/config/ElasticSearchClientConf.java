package com.huangjicheng.top.vepjob.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/17 20:52
 */

@Configuration
public class ElasticSearchClientConf {

    private Logger logger = LoggerFactory.getLogger(ElasticSearchClientConf.class);


    @Value("${es.hostname}")
    private String hostname;

    @Value("${es.port}")
    private int port;

    @Value("${es.port}")
    private String scheme;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.1.15", 9200, "http")).setRequestConfigCallback(
                        requestConfigCallback -> {
                            requestConfigCallback.setSocketTimeout(1000 * 30 * 30);
                            return requestConfigCallback;
                        }));
        logger.info("elasticSearch is connect ..............." + restHighLevelClient.toString());
        return restHighLevelClient;
    }

}
