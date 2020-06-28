package com.huangjicheng.top.vepimpl.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.UserService;
import com.huangjicheng.top.vepdao.entity.User;
import com.huangjicheng.top.vepdao.mapper.RoleMapper;
import com.huangjicheng.top.vepdao.mapper.UserMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:36
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    /**
     * 获取用户对像
     *
     * @param id 用户id
     * @return
     */
    @Override
    public User get(Integer id) {
        return userMapper.get(id);
    }

    @Override
    public int save(User user) {
        return userMapper.save(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int delete(Integer id) {
        return userMapper.delete(id);
    }

    @Override
    public List<User> getList(User user) {
        return userMapper.getList(user);
    }


    @Override
    public PageInfo<User> findAllUserByPage(User user,int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<User> lists = userMapper.getList(user);
        PageInfo<User> pageInfo = new PageInfo<User>(formatUser(lists));
        return pageInfo;

    }

    public List<User> formatUser(List<User> lists){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lists.forEach(list -> {
            if (list.getState() == 1) {
                list.setStateStr(true);
            } else {
                list.setStateStr(false);
            }
            if (list.getRoleId() != null) {
                if (roleMapper.get(list.getRoleId()) != null) {
                    list.setRoleName(roleMapper.get(list.getRoleId()).getRoleName());
                }
            }
            if (list.getCreateTime() != null) list.setCreateTimeStr(simpleDateFormat.format(list.getCreateTime()));
        });
        return lists;
    }
}
