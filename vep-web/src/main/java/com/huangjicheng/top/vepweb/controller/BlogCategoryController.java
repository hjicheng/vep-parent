package com.huangjicheng.top.vepweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.BlogCategoryService;
import com.huangjicheng.top.vepapi.service.BlogService;
import com.huangjicheng.top.vepcommont.util.ResponseCode;
import com.huangjicheng.top.vepcommont.util.ResultJson;
import com.huangjicheng.top.vepdao.entity.Blog;
import com.huangjicheng.top.vepdao.entity.BlogCategory;
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
@RequestMapping("/blogs/categorys")
@Api(description = "博客分类管理")
public class BlogCategoryController {

    @Reference
    private BlogCategoryService blogCategoryService;


    /**
     * 获取博客分类管理
     *
     * @return
     */
    @ApiOperation(value = "获取博客分类管理", notes = "获取博客分类管理")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResultJson get(
            @ApiParam(value = "博客ID", readOnly = true)
            @PathVariable Integer id) {
        BlogCategory blogCategory = blogCategoryService.get(id);
        return new ResultJson(blogCategory);
    }

    /**
     * 获取博客分类名称
     *
     * @return
     */
    @ApiOperation(value = "获取博客分类名称", notes = "获取博客分类名称")
    @RequestMapping(value = "/getName", method = RequestMethod.GET)
    public ResultJson getName() {
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setIsDeleted(0);
        List<BlogCategory> blogCategoryList = blogCategoryService.getList(blogCategory);
        return new ResultJson(blogCategoryList);
    }

    /**
     * 获取全部博客分类管理
     *
     * @return
     */

    @ApiOperation(value = "获取全部博客分类管理", notes = "获取全部博客分类管理")
    @RequestMapping(value = "/getList/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResultJson getList(@RequestBody BlogCategory blogCategory, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageInfo<BlogCategory> blogCategorys = blogCategoryService.findAllBlogCategoryByPage(blogCategory, pageNum, pageSize);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("blogCategorys", blogCategorys.getList());
        jsonObject.put("total", blogCategorys.getTotal());

        return new ResultJson(jsonObject);

    }

    /**
     * 更新博客分类状态
     *
     * @return
     */
    @ApiOperation(value = "更新博客分类状态", notes = "更新博客分类状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    public ResultJson updateStatus(
            @ApiParam(value = "博客分类ID", readOnly = true)
            @PathVariable Integer id) {
        if (blogCategoryService.get(id) != null) {
            if (blogCategoryService.updateStatus(blogCategoryService.get(id)) > 0) {
                return new ResultJson().ok();
            } else {
                return new ResultJson(ResponseCode.DELETE_ERROR, null, "更新失败");
            }
        } else {
            return new ResultJson(ResponseCode.GET_ERROR, null, "查询失败");

        }
    }

    /**
     * 删除博客分类
     *
     * @return
     */
    @ApiOperation(value = "删除博客分类", notes = "删除博客分类")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResultJson delete(
            @ApiParam(value = "博客ID", readOnly = true)
            @PathVariable Integer id) {
        if (blogCategoryService.get(id) != null) {
            if (blogCategoryService.delete(id) > 0) {
                return new ResultJson().ok();
            } else {
                return new ResultJson(ResponseCode.DELETE_ERROR, null, "删除失败");
            }
        } else {
            return new ResultJson(ResponseCode.GET_ERROR, null, "查询失败");

        }
    }

    /**
     * 新建博客分类
     *
     * @return
     */
    @ApiOperation(value = "新建博客分类", notes = "新建博客分类")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultJson save(@RequestBody BlogCategory blogCategory) {
        if (blogCategoryService.getByCateName(blogCategory.getCategoryName()) == null) {
            blogCategory.setCreateTime(new Date());
            blogCategory.setCategoryRank(0);
            if (blogCategoryService.save(blogCategory) > 0) {
                return new ResultJson(blogCategory);
            } else {
                return new ResultJson(ResponseCode.CREATE_ERROR, null, "创建失败");
            }
        } else {
            return new ResultJson(ResponseCode.CREATE_ERROR, null, "创建失败,分类名称存在");

        }
    }

    /**
     * 更新博客分类
     *
     * @return
     */
    @ApiOperation(value = "更新博客分类", notes = "更新博客分类")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson update(@RequestBody BlogCategory blogCategory) {
        blogCategory.setUpdateTime(new Date());
        if (blogCategoryService.update(blogCategory) > 0) {
            return new ResultJson(blogCategory);
        } else {
            return new ResultJson(ResponseCode.UPDATE_ERROR, null, "更新失败");
        }
    }

}
