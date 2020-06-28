package com.huangjicheng.top.vepapi.service;


import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepdao.entity.User;

import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:05
 */

public interface UserService {
    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return
     */
    User login(User user);

    /**
     * 获取用户对像
     *
     * @param id 用户id
     * @return
     */
    User get(Integer id);

    int save(User user);

    int update(User user);

    int delete(Integer id);

    List<User> getList(User user);

    PageInfo<User> findAllUserByPage(User user,int pageNum, int pageSize);
}
