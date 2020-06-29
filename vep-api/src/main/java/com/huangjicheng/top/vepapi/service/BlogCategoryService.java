package com.huangjicheng.top.vepapi.service;


import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepdao.entity.BlogCategory;

import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:05
 */

public interface BlogCategoryService {

    BlogCategory get(Integer id);

    int save(BlogCategory blogCategory);

    int update(BlogCategory blogCategory);

    int updateStatus(BlogCategory blogCategory);

    int delete(Integer id);

    int getTotalByMonth(String month);

    BlogCategory getByCateName(String categoryName) ;

    List<BlogCategory> getList(BlogCategory blogCategory);

    PageInfo<BlogCategory> findAllBlogCategoryByPage(BlogCategory blogCategory, int pageNum, int pageSize);

}


