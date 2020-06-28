package com.huangjicheng.top.vepimpl.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.FilesService;
import com.huangjicheng.top.vepdao.entity.Files;
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
public class FilesServiceImpl implements FilesService {

    @Autowired
    private FilesMapper filesMapper;

    @Override
    public Files get(Integer id) {
        return filesMapper.get(id);
    }

    @Override
    public int save(Files files) {
        return filesMapper.save(files);
    }

    @Override
    public int update(Files files) {
        return filesMapper.update(files);
    }

    @Override
    public int delete(Integer id) {
        return filesMapper.delete(id);
    }

    @Override
    public List<Files> getList(Files files) {
        return filesMapper.getList(files);
    }


    @Override
    public PageInfo<Files> findAllFileByPage(Files files, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<Files> lists = filesMapper.getList(files);
        PageInfo<Files> pageInfo = new PageInfo<Files>(formatFile(lists));
        return pageInfo;

    }

    public List<Files> formatFile(List<Files> lists) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lists.forEach(list -> {
            if (list.getCreateTime() != null) list.setCreateTimeStr(simpleDateFormat.format(list.getCreateTime()));
            if (list.getUpdateTime() != null) list.setUpdateTimeStr(simpleDateFormat.format(list.getUpdateTime()));
        });
        return lists;
    }

}
