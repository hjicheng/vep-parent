package com.huangjicheng.top.vepimpl.impl;

import com.alibaba.nacos.spring.util.NacosUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.BlogService;
import com.huangjicheng.top.vepapi.service.FilesService;
import com.huangjicheng.top.vepdao.entity.Blog;
import com.huangjicheng.top.vepdao.entity.Dict;
import com.huangjicheng.top.vepdao.entity.Files;
import com.huangjicheng.top.vepdao.mapper.BlogCategoryMapper;
import com.huangjicheng.top.vepdao.mapper.BlogMapper;
import com.huangjicheng.top.vepdao.mapper.BlogTagMapper;
import com.huangjicheng.top.vepdao.mapper.FilesMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:36
 */

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;


    @Autowired
    private BlogCategoryMapper blogCategoryMapper;

    @Override
    public Blog get(Integer id) {
        return blogMapper.get(id);
    }

    @Override
    public int save(Blog blog) {
        return blogMapper.save(blog);
    }

    @Override
    public int update(Blog blog) {
        return blogMapper.update(blog);
    }

    @Override
    public int delete(Integer id) {
        return blogMapper.delete(id);
    }

    @Override
    public List<Blog> getList(Blog blog) {
        return null;
    }

    @Override
    public int getTotalByMonth(String month) {
        return blogMapper.getTotalByMonth(month);
    }

    @Override
    public PageInfo<Blog> findAllBlogByPage(Blog blog, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> lists = blogMapper.getList(blog);
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(formatDict(lists));
        return pageInfo;
    }

    public List<Blog> formatDict(List<Blog> lists) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lists.forEach(list -> {
            if (list.getCreateTime() != null) list.setCreateTimeStr(simpleDateFormat.format(list.getCreateTime()));
            if (list.getUpdateTime() != null) list.setUpdateTimeStr(simpleDateFormat.format(list.getUpdateTime()));
            if (list.getBlogCategoryId() != null)
                list.setBlogCategoryId(blogCategoryMapper.get(Integer.valueOf(list.getBlogCategoryId())).getCategoryName());
            if (list.getBlogTagId() != null)
                list.setBlogTagId(blogTagMapper.get(Integer.valueOf(list.getBlogTagId())).getTagName());
        });
        return lists;
    }

}
