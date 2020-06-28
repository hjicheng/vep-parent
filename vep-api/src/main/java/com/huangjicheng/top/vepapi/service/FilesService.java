package com.huangjicheng.top.vepapi.service;


import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepdao.entity.Files;

import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:05
 */

public interface FilesService {

    Files get(Integer id);

    int save(Files files);

    int update(Files files);

    int delete(Integer id);

    List<Files> getList(Files files);

    PageInfo<Files> findAllFileByPage(Files files, int pageNum, int pageSize);

}


