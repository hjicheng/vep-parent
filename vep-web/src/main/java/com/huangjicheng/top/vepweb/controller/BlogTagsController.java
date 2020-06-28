package com.huangjicheng.top.vepweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.AuthService;
import com.huangjicheng.top.vepapi.service.BlogTagService;
import com.huangjicheng.top.vepcommont.util.ResponseCode;
import com.huangjicheng.top.vepcommont.util.ResultJson;
import com.huangjicheng.top.vepdao.entity.Auth;
import com.huangjicheng.top.vepdao.entity.Blog;
import com.huangjicheng.top.vepdao.entity.BlogCategory;
import com.huangjicheng.top.vepdao.entity.BlogTag;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
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
@RequestMapping("/blogs/tags")
@Api(description = "博客标签管理")
public class BlogTagsController {

    @Reference
    private BlogTagService blogTagService;


    /**
     * 获取博客标签
     *
     * @return
     */
    @ApiOperation(value = "获取博客标签", notes = "获取博客标签")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResultJson get(
            @ApiParam(value = "标签ID", readOnly = true)
            @PathVariable Integer id) {
        BlogTag blogTag = blogTagService.get(id);
        return new ResultJson(blogTag);
    }

    /**
     * 获取全部博客标签
     *
     * @return
     */

    @ApiOperation(value = "获取全部博客标签", notes = "获取全部博客标签")
    @RequestMapping(value = "/getList/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResultJson getList(@RequestBody BlogTag blogTag, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageInfo<BlogTag> blogTags = blogTagService.findAllBlogByPage(blogTag, pageNum, pageSize);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("blogTags", blogTags.getList());
        jsonObject.put("total", blogTags.getTotal());

        return new ResultJson(jsonObject);

    }

    /**
     * 删除博客标签
     *
     * @return
     */
    @ApiOperation(value = "删除博客标签", notes = "删除博客标签")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResultJson delete(
            @ApiParam(value = "博客ID", readOnly = true)
            @PathVariable Integer id) {
        if (blogTagService.get(id) != null) {
            if (blogTagService.delete(id) > 0) {
                return new ResultJson().ok();
            } else {
                return new ResultJson(ResponseCode.DELETE_ERROR, null, "删除失败");
            }
        } else {
            return new ResultJson(ResponseCode.GET_ERROR, null, "查询失败");

        }
    }

    /**
     * 获取博客分类名称
     *
     * @return
     */
    @ApiOperation(value = "获取博客分类名称", notes = "获取博客分类名称")
    @RequestMapping(value = "/getName", method = RequestMethod.GET)
    public ResultJson getName() {
        BlogTag blogTag = new BlogTag();
        blogTag.setIsDeleted(0);
        List<BlogTag> blogTags = blogTagService.getList(blogTag);
        return new ResultJson(blogTags);
    }
    /**
     * 更新博客标签状态
     *
     * @return
     */
    @ApiOperation(value = "更新博客标签状态", notes = "更新博客标签状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    public ResultJson updateStatus(
            @ApiParam(value = "博客ID", readOnly = true)
            @PathVariable Integer id) {
        if (blogTagService.get(id) != null) {
            if (blogTagService.updateStatus(blogTagService.get(id)) > 0) {
                return new ResultJson().ok();
            } else {
                return new ResultJson(ResponseCode.DELETE_ERROR, null, "更新失败");
            }
        } else {
            return new ResultJson(ResponseCode.GET_ERROR, null, "更新失败");

        }
    }

    /**
     * 新建标签
     *
     * @return
     */
    @ApiOperation(value = "新建标签", notes = "新建标签")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultJson save(@RequestBody BlogTag blogTag) {
        blogTag.setCreateTime(new Date());
        if (blogTagService.getByTagName(blogTag.getTagName()) == null) {
            if (blogTagService.save(blogTag) > 0) {
                return new ResultJson(blogTag);
            } else {
                return new ResultJson(ResponseCode.CREATE_ERROR, null, "创建失败");
            }
        } else {
            return new ResultJson(ResponseCode.CREATE_ERROR, null, "创建失败,标签已存在");
        }
    }

    /**
     * 更新角色
     *
     * @return
     */
    @ApiOperation(value = "更新博客", notes = "更新博客")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson update(@RequestBody BlogTag blogTag) {
        blogTag.setUpdateTime(new Date());
        if (blogTagService.update(blogTag) > 0) {
            return new ResultJson(blogTag);
        } else {
            return new ResultJson(ResponseCode.UPDATE_ERROR, null, "更新失败");
        }
    }
}
