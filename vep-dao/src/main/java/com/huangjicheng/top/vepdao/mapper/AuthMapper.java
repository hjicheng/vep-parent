package com.huangjicheng.top.vepdao.mapper;


import com.huangjicheng.top.vepdao.base.BaseMapper;
import com.huangjicheng.top.vepdao.entity.Auth;
import io.swagger.models.auth.In;

import java.util.List;

public interface AuthMapper extends BaseMapper<Auth> {

    List<Auth> getByAuthPId(Integer id);
}
