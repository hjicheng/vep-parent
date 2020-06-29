package com.huangjicheng.top.vepapi.service;


import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepdao.entity.Blog;

import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:05
 */

public interface BlogService {

    Blog get(Integer id);

    int save(Blog blog);

    int update(Blog blog);

    int delete(Integer id);

    List<Blog> getList(Blog blog);

    int getTotalByMonth(String month);

    PageInfo<Blog> findAllBlogByPage(Blog blog, int pageNum, int pageSize);

}


