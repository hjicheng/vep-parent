package com.huangjicheng.top.vepapi.service;


import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepdao.entity.Dict;

import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:05
 */

public interface DictService {

    Dict get(Integer id);

    int save(Dict dict);

    int update(Dict dict);

    int delete(Integer id);

    List<Dict> getList(Dict dict);

    PageInfo<Dict> findAllDictByPage(Dict dict, int pageNum, int pageSize);

    Dict getByName(String name);

}


