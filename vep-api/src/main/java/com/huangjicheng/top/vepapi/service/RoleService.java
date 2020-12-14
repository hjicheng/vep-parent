package com.huangjicheng.top.vepapi.service;


import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepdao.entity.Role;

import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:05
 */

public interface RoleService {

    Role get(Integer id);

    int save(Role role);

    int update(Role role);

    int delete(Integer id);

    List<Role> getList(Role role);

    PageInfo<Role> findAllRoleByPage(int pageNum, int pageSize);

    PageInfo<Role> findRoleByPage(Role role, int pageNum, int pageSize);

}
