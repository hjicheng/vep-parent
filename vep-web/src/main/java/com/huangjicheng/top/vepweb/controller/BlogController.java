package com.huangjicheng.top.vepweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.AuthService;
import com.huangjicheng.top.vepapi.service.BlogCategoryService;
import com.huangjicheng.top.vepapi.service.BlogService;
import com.huangjicheng.top.vepapi.service.BlogTagService;
import com.huangjicheng.top.vepcommont.util.ResponseCode;
import com.huangjicheng.top.vepcommont.util.ResultJson;
import com.huangjicheng.top.vepdao.entity.Auth;
import com.huangjicheng.top.vepdao.entity.Blog;
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
@RequestMapping("/blogs")
@Api(description = "博客管理")
public class BlogController {

    @Reference
    private BlogService blogService;


    @Reference
    private BlogCategoryService blogCategoryService;

    @Reference
    private BlogTagService blogTagService;

    /**
     * 通过用户ID获取权限对象
     *
     * @return
     */
    @ApiOperation(value = "获取博客", notes = "获取博客")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResultJson get(
            @ApiParam(value = "博客ID", readOnly = true)
            @PathVariable Integer id) {
        Blog blog = blogService.get(id);
        return new ResultJson(blog);
    }

    /**
     * 获取全部权限
     *
     * @return
     */

    @ApiOperation(value = "获取全部博客", notes = "获取全部博客")
    @RequestMapping(value = "/getList/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResultJson getList(@RequestBody Blog blog, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageInfo<Blog> blogs = blogService.findAllBlogByPage(blog, pageNum, pageSize);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("blogs", blogs.getList());
        jsonObject.put("total", blogs.getTotal());

        return new ResultJson(jsonObject);

    }

    /**
     * 删除博客
     *
     * @return
     */
    @ApiOperation(value = "删除博客", notes = "删除博客")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResultJson delete(
            @ApiParam(value = "博客ID", readOnly = true)
            @PathVariable Integer id) {
        if (blogService.get(id) != null) {
            if (blogService.delete(id) > 0) {
                return new ResultJson().ok();
            } else {
                return new ResultJson(ResponseCode.DELETE_ERROR, null, "删除失败");
            }
        } else {
            return new ResultJson(ResponseCode.GET_ERROR, null, "查询失败");

        }
    }

    /**
     * 新建博客
     *
     * @return
     */
    @ApiOperation(value = "新建博客", notes = "新建博客")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public ResultJson save(@RequestBody Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            if (blogService.save(blog) > 0) {
                return new ResultJson(blog);
            } else {
                return new ResultJson(ResponseCode.CREATE_ERROR, null, "创建失败");
            }
        } else {
            blog.setUpdateTime(new Date());
            blog.setBlogCategoryId(String.valueOf(blogCategoryService.getByCateName(blog.getBlogCategoryId()).getId()));
            blog.setBlogTagId(String.valueOf(blogTagService.getByTagName(blog.getBlogTagId()).getId()));
            if (blogService.update(blog) > 0) {
                return new ResultJson(blog);
            } else {
                return new ResultJson(ResponseCode.UPDATE_ERROR, null, "更新失败");
            }
        }
    }

}
