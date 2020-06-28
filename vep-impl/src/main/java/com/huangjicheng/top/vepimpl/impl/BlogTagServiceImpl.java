package com.huangjicheng.top.vepimpl.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.BlogService;
import com.huangjicheng.top.vepapi.service.BlogTagService;
import com.huangjicheng.top.vepdao.entity.Blog;
import com.huangjicheng.top.vepdao.entity.BlogTag;
import com.huangjicheng.top.vepdao.mapper.BlogMapper;
import com.huangjicheng.top.vepdao.mapper.BlogTagMapper;
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
public class BlogTagServiceImpl implements BlogTagService {

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    public BlogTag get(Integer id) {
        return blogTagMapper.get(id);
    }

    @Override
    public int save(BlogTag blogTag) {
        return blogTagMapper.save(blogTag);
    }

    @Override
    public int update(BlogTag blogTag) {
        return blogTagMapper.update(blogTag);
    }

    @Override
    public int delete(Integer id) {
        return blogTagMapper.delete(id);
    }

    @Override
    public int updateStatus(BlogTag blogTag) {
        blogTag.setIsDeleted(blogTag.getIsDeleted() == 0 ? 1 : 0);
        blogTag.setUpdateTime(new Date());
        return blogTagMapper.updateStatus(blogTag);
    }

    @Override
    public int getTotalByMonth(String month) {
        return blogTagMapper.getTotalByMonth(month);
    }

    @Override
    public List<BlogTag> getList(BlogTag blogTag) {
        return blogTagMapper.getList(blogTag);
    }

    @Override
    public BlogTag getByTagName(String tagName) {
        return blogTagMapper.getByTagName(tagName);
    }

    @Override
    public PageInfo<BlogTag> findAllBlogByPage(BlogTag blogTag, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<BlogTag> lists = blogTagMapper.getList(blogTag);
        PageInfo<BlogTag> pageInfo = new PageInfo<BlogTag>(formatDict(lists));
        return pageInfo;
    }

    public List<BlogTag> formatDict(List<BlogTag> lists) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lists.forEach(list -> {
            if (list.getCreateTime() != null) list.setCreateTimeStr(simpleDateFormat.format(list.getCreateTime()));
            if (list.getUpdateTime() != null) list.setUpdateTimeStr(simpleDateFormat.format(list.getUpdateTime()));
        });
        return lists;
    }

}
