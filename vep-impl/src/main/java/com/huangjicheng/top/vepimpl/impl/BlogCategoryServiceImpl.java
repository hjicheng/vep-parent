package com.huangjicheng.top.vepimpl.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.BlogCategoryService;
import com.huangjicheng.top.vepdao.entity.BlogCategory;
import com.huangjicheng.top.vepdao.mapper.BlogCategoryMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:36
 */

@Service
public class BlogCategoryServiceImpl implements BlogCategoryService {

    @Autowired
    private BlogCategoryMapper blogCategoryMapper;

    @Override
    public BlogCategory get(Integer id) {
        return blogCategoryMapper.get(id);
    }

    @Override
    public int save(BlogCategory blogCategory) {
        return blogCategoryMapper.save(blogCategory);
    }

    @Override
    public int update(BlogCategory blogCategory) {
        return blogCategoryMapper.update(blogCategory);
    }

    @Override
    public int updateStatus(BlogCategory blogCategory) {
        blogCategory.setIsDeleted(blogCategory.getIsDeleted() == 0 ? 1 : 0);
        blogCategory.setUpdateTime(new Date());
        return blogCategoryMapper.updateStatus(blogCategory);
    }

    @Override
    public int getTotalByMonth(String month) {
        return blogCategoryMapper.getTotalByMonth(month);
    }

    @Override
    public int delete(Integer id) {
        return blogCategoryMapper.delete(id);
    }

    @Override
    public BlogCategory getByCateName(String categoryName) {
        return blogCategoryMapper.getByCateName(categoryName);
    }


    @Override
    public List<BlogCategory> getList(BlogCategory blogCategory) {
        return blogCategoryMapper.getList(blogCategory);
    }

    @Override
    public PageInfo<BlogCategory> findAllBlogCategoryByPage(BlogCategory blogCategory, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<BlogCategory> lists = blogCategoryMapper.getList(blogCategory);
        PageInfo<BlogCategory> pageInfo = new PageInfo<BlogCategory>(formatDict(lists));
        return pageInfo;
    }

    public List<BlogCategory> formatDict(List<BlogCategory> lists) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lists.forEach(list -> {
            if (list.getCreateTime() != null) list.setCreateTimeStr(simpleDateFormat.format(list.getCreateTime()));
            if (list.getUpdateTime() != null) list.setUpdateTimeStr(simpleDateFormat.format(list.getUpdateTime()));
        });
        return lists;
    }

}
