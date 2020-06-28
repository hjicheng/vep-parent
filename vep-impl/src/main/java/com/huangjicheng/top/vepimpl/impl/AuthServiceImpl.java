package com.huangjicheng.top.vepimpl.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.AuthService;
import com.huangjicheng.top.vepdao.entity.Auth;
import com.huangjicheng.top.vepdao.mapper.AuthMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:36
 */

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public Auth get(Integer id) {
        return authMapper.get(id);
    }

    @Override
    public int save(Auth auth) {
        return authMapper.save(auth);
    }

    @Override
    public int update(Auth auth) {
        return authMapper.update(auth);
    }

    @Override
    public int delete(Integer id) {
        return authMapper.delete(id);
    }

    @Override
    public List<Auth> getList(Auth auth) {
        return authMapper.getList(auth);
    }

//    @Override
//    public List<Auth> getListTree() {
//
//        // 获取全部一级权限
//        Auth auth = new Auth();
//        auth.setLevel(0);
//        List<Auth> auth1List = authMapper.getList(auth);
//        for (Auth a1 : auth1List) {
//            // 根据一级权限id，获取二级权限
//            auth.setLevel(1);
//            auth.setPid(a1.getId());
//            List<Auth> auth2List = authMapper.getList(auth);
//            for (Auth a2 : auth2List) {
//                // 根据二级权限id,获取三级权限
//                auth.setLevel(2);
//                auth.setPid(a2.getId());
//                List<Auth> auth3List = authMapper.getList(auth);
//                a2.setChildren(auth3List);
//            }
//            a1.setChildren(auth2List);
//        }
//        return auth1List;
//    }

    @Override
    public List<Auth> getListTree() {

        // 获得所有的权限
        List<Auth> authList = authMapper.getList(new Auth());
        //根节点
        List<Auth> rootMenu = new ArrayList<Auth>();

        for (Auth auth : authList) {
            if (auth.getPid() == 0) {
                // 为根节点
                rootMenu.add(auth);
            }
        }

        //为根菜单设置子菜单，getClild是递归调用的
        for (Auth nav : rootMenu) {
            /* 获取根节点下的所有子节点 使用getChild方法*/
            List<Auth> childList = getChild(nav.getId(), authList);
            nav.setChildren(childList);//给根节点设置子节点
        }
        return rootMenu;
    }

    @Override
    public PageInfo<Auth> findAllAuthByPage(Auth auth, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<Auth> lists = authMapper.getList(auth);
        PageInfo<Auth> pageInfo = new PageInfo<Auth>(lists);
        return pageInfo;

    }

    /**
     * 获取子节点
     *
     * @param id      父节点id
     * @param authList 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<Auth> getChild(Integer id, List<Auth> authList) {
        //设置子菜单
        List<Auth> childList = new ArrayList<Auth>();
        for (Auth nav : authList) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (nav.getPid().intValue() == id.intValue()) {
                childList.add(nav);
            }
        }
        //递归子节点
        for (Auth nav : childList) {
            nav.setChildren(getChild(nav.getId(), authList));
        }
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<Auth>();
        }
        return childList;
    }

    /**
     * 遍历查询id下的所有子id
     *
     * @param pid
     * @return
     */
    @Override
    public List<Auth> getByAuthIdByPId(Integer pid) {
        List<Auth> authList = authMapper.getByAuthPId(pid);
        return authList;
    }

}
