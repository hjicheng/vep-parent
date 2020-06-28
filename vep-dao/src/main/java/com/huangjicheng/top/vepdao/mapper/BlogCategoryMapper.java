package com.huangjicheng.top.vepdao.mapper;


import com.huangjicheng.top.vepdao.base.BaseMapper;
import com.huangjicheng.top.vepdao.entity.BlogCategory;
import com.huangjicheng.top.vepdao.entity.BlogTag;

public interface BlogCategoryMapper extends BaseMapper<BlogCategory> {

    int updateStatus(BlogCategory blogCategory);

    BlogCategory getByCateName(String categoryName);

    int getTotalByMonth(String month);

}
