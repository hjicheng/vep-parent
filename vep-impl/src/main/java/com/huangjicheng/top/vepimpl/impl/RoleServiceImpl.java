package com.huangjicheng.top.vepimpl.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.RoleService;
import com.huangjicheng.top.vepdao.entity.Auth;
import com.huangjicheng.top.vepdao.entity.Role;
import com.huangjicheng.top.vepdao.entity.User;
import com.huangjicheng.top.vepdao.mapper.AuthMapper;
import com.huangjicheng.top.vepdao.mapper.RoleMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:36
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthMapper authMapper;

    @Override
    public Role get(Integer id) {
        Role role = roleMapper.get(id);
        if (role.getIds() != null) {
            dealRoleChildren(role);
        }
        return role;
    }

    @Override
    public PageInfo<Role> findAllRoleByPage(Role role, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<Role> lists = roleMapper.getList(role);
        PageInfo<Role> pageInfo = new PageInfo<Role>(formatRole(lists));
        return pageInfo;

    }

    public List<Role> formatRole(List<Role> lists){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lists.forEach(list -> {
            if (list.getIds() != null) {
                dealRoleChildren(list);
            }
        });
        return lists;
    }
    @Override
    public int save(Role role) {
        return roleMapper.save(role);
    }

    @Override
    public int update(Role role) {
        return roleMapper.update(role);
    }

    @Override
    public int delete(Integer id) {
        return roleMapper.delete(id);
    }

    @Override
    public List<Role> getList(Role role) {
        return null;
    }


    public void dealRoleChildren(Role role) {
        // 查询角色下所有权限
        Auth auth = new Auth();
        auth.setIds(Arrays.asList(role.getIds().split(",")));
        List<Auth> authList = authMapper.getList(auth);
        // 生成权限树
        List<Auth> treeGridList = getChildren(authList);
        role.setChildren(treeGridList);
    }

    /**
     * 获取跟节点
     *
     * @param list
     * @return
     */
    private List<Auth> getChildren(List<Auth> list) {
        List<Auth> result = new ArrayList<>();
        for (Auth adModule : list) {
            // 根节点
            if (adModule.getPid() == 0) {
                result.add(getChildrens(adModule, list));
            }
        }
        return result;
    }

    /**
     * 递归获取子节点
     *
     * @param module
     * @param list
     * @return
     */
    private Auth getChildrens(Auth module, List<Auth> list) {
        List<Auth> childNodes = new ArrayList<>();
        for (Auth node : list) {
            if (node.getPid().equals(module.getId())) {
                childNodes.add(getChildrens(node, list));
            }
        }
        module.setChildren(childNodes);
        return module;
    }
}
