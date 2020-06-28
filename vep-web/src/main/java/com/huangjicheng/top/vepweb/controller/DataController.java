package com.huangjicheng.top.vepweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.huangjicheng.top.vepapi.service.AuthService;
import com.huangjicheng.top.vepcommont.util.PageUtil;
import com.huangjicheng.top.vepcommont.util.ResponseCode;
import com.huangjicheng.top.vepcommont.util.ResultJson;
import com.huangjicheng.top.vepcommont.util.StringUtils;
import com.huangjicheng.top.vepdao.entity.Auth;
import com.huangjicheng.top.vepdao.entity.Datas;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 20:32
 */

@RestController
@RequestMapping("/file/data")
@Api(description = "文件数据管理")
public class DataController {


    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取全部权限
     *
     * @return
     */

    @ApiOperation(value = "获取文件数据", notes = "获取文件数据")
    @RequestMapping(value = "/getList/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResultJson getList(@RequestBody Datas datas, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dictName", "FILE_NAME");
        List<Datas> list = new ArrayList<>();

        try {
            list = searchByKey(datas.getType(), datas.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject.put("datas", PageUtil.startPage(list, pageNum, pageSize));
        jsonObject.put("total", list.size());

        return new ResultJson(jsonObject);

    }

    public List<Datas> searchByKey(String type, String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest("file_list");
        // 构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        DisMaxQueryBuilder fuzzyQueryBuilder = QueryBuilders.disMaxQuery();
        MatchAllQueryBuilder matchAllQueryBuilder = null;
        if (StringUtils.isNotBlank(type) || StringUtils.isNotBlank(value)) {
            if (StringUtils.isNotBlank(type)) {
                // 使用QueryBuilders进行匹配
                fuzzyQueryBuilder.add(QueryBuilders.matchQuery("type", type));
            }
            if (StringUtils.isNotBlank(value)) {
                fuzzyQueryBuilder.add(QueryBuilders.matchQuery("value", value));
            }
            sourceBuilder.query(fuzzyQueryBuilder);
        } else {
            // 匹配所有
            matchAllQueryBuilder = QueryBuilders.matchAllQuery();
            sourceBuilder.query(matchAllQueryBuilder);
        }
        sourceBuilder.size(100);
        sourceBuilder.timeout(TimeValue.timeValueSeconds(60));

        // 构建搜索
        searchRequest.source(sourceBuilder);

        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

        List<Datas> list = new ArrayList<>();
        search.getHits().forEach(hit -> {
            Map<String, Object> map = hit.getSourceAsMap();
            Datas datas = JSON.parseObject(JSON.toJSONString(map), Datas.class);
            datas.setCreateTimeStr(simpleDateFormat.format(datas.getCreateTime()));
            list.add(datas);
        });
        return list;
    }


}
