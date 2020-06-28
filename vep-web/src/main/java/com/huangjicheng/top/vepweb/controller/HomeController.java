package com.huangjicheng.top.vepweb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.BlogCategoryService;
import com.huangjicheng.top.vepapi.service.BlogService;
import com.huangjicheng.top.vepapi.service.BlogTagService;
import com.huangjicheng.top.vepapi.service.UserService;
import com.huangjicheng.top.vepcommont.util.*;
import com.huangjicheng.top.vepdao.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 20:32
 */

@RestController
@RequestMapping("/home")
@Api(description = "首页管理")
public class HomeController {

    @Reference
    private BlogService blogService;

    @Reference
    private BlogTagService blogTagService;

    @Reference
    private BlogCategoryService blogCategoryService;

    /**
     * 获取博客详情
     *
     * @return
     */
    @ApiOperation(value = "获取博客详情", notes = "获取博客详情")
    @RequestMapping(value = "/getBlogDetail", method = RequestMethod.GET)
    public ResultJson getBlogDetail() {
        JSONObject jsonObject = new JSONObject();
        JSONArray columnsList = new JSONArray();
        columnsList.add("日期");
        columnsList.add("分类数");
        columnsList.add("博客数");
        columnsList.add("标签数");
        jsonObject.put("columns", columnsList.toArray());

        List<String> monthList = MonthUtil.getLastMonths(6);

        /**
         *           { 日期: '2020.6.11', 用户数: 1723, 文件数: 1423, 下单率: 490 },
         *           { 日期: '2020.6.12', 用户数: 3792, 文件数: 3492, 下单率: 323 }
         */
        JSONArray rowsList = new JSONArray();

        for (String month : monthList) {
            JSONObject rowObject = new JSONObject();
            rowObject.put("日期", month);
            // 根据日期获取博客数
            rowObject.put("博客数", blogService.getTotalByMonth(month));
            // 根据日期获取标签数
            rowObject.put("标签数", blogTagService.getTotalByMonth(month));
            // 根据日期获取分类数
            rowObject.put("分类数", blogCategoryService.getTotalByMonth(month));

            rowsList.add(rowObject);
        }
        jsonObject.put("rows", rowsList.toArray());
        return new ResultJson(jsonObject);
    }

}
