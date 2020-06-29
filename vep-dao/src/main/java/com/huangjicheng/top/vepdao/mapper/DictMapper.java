package com.huangjicheng.top.vepdao.mapper;


import com.huangjicheng.top.vepdao.base.BaseMapper;
import com.huangjicheng.top.vepdao.entity.Dict;

public interface DictMapper extends BaseMapper<Dict> {

    Dict getByName(String name);

}
