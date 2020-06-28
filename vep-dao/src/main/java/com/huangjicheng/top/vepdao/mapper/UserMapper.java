package com.huangjicheng.top.vepdao.mapper;

import com.huangjicheng.top.vepdao.base.BaseMapper;
import com.huangjicheng.top.vepdao.entity.User;

public interface UserMapper extends BaseMapper<User> {

    User login(User user);
}
