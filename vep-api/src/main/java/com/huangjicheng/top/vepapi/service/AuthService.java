package com.huangjicheng.top.vepapi.service;


import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepdao.entity.Auth;

import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:05
 */

public interface AuthService {

    Auth get(Integer id);

    int save(Auth auth);

    int update(Auth auth);

    int delete(Integer id);

    List<Auth> getList(Auth auth);

    List<Auth> getListTree();

    List<Auth> getByAuthIdByPId(Integer id);

    PageInfo<Auth> findAllAuthByPage(Auth auth, int pageNum, int pageSize);

}


