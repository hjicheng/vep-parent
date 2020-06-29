package com.huangjicheng.top.vepdao.mapper;


import com.huangjicheng.top.vepdao.base.BaseMapper;
import com.huangjicheng.top.vepdao.entity.BlogTag;

public interface BlogTagMapper extends BaseMapper<BlogTag> {

    int updateStatus(BlogTag blogTag);

    BlogTag getByTagName(String tagName);

    int getTotalByMonth(String month);

}
