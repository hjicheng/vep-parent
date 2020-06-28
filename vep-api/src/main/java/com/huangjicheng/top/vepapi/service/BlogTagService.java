package com.huangjicheng.top.vepapi.service;


import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepdao.entity.BlogTag;

import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:05
 */

public interface BlogTagService {

    BlogTag get(Integer id);

    int save(BlogTag blogTag);

    int update(BlogTag blogTag);

    int delete(Integer id);

    int updateStatus(BlogTag blogTag);

    int getTotalByMonth(String month);

    List<BlogTag> getList(BlogTag blogTag);

    BlogTag getByTagName(String tagName);

    PageInfo<BlogTag> findAllBlogByPage(BlogTag blogTag, int pageNum, int pageSize);

}


