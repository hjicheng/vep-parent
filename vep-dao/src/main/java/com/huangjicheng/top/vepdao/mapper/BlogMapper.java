package com.huangjicheng.top.vepdao.mapper;


import com.huangjicheng.top.vepdao.base.BaseMapper;
import com.huangjicheng.top.vepdao.entity.Blog;

public interface BlogMapper extends BaseMapper<Blog> {

    int getTotalByMonth(String month);
}
