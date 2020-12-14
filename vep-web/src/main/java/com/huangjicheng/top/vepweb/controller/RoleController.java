package com.huangjicheng.top.vepweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.AuthService;
import com.huangjicheng.top.vepapi.service.RoleService;
import com.huangjicheng.top.vepcommont.util.ResponseCode;
import com.huangjicheng.top.vepcommont.util.ResultJson;
import com.huangjicheng.top.vepdao.entity.Auth;
import com.huangjicheng.top.vepdao.entity.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 20:32
 */

@RestController
@RequestMapping("/role")
@Api(description = "角色管理")
public class RoleController {

    @Reference
    private RoleService roleService;

    @Reference
    private AuthService authService;


    /**
     * 通过用户ID获取角色对象
     *
     * @return
     */
    @ApiOperation(value = "获取角色", notes = "通过ID获取角色")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResultJson get(
            @ApiParam(value = "角色ID", readOnly = true)
            @PathVariable Integer id) {
        Role role = roleService.get(id);
        return new ResultJson(role);
    }

    /**
     * 获取全部角色
     *
     * @return
     */
    @ApiOperation(value = "获取全部角色", notes = "获取全部角色")
    @RequestMapping(value = "/getAllList/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResultJson getList( @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageInfo<Role> roles = roleService.findAllRoleByPage( pageNum, pageSize);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roles", roles.getList());
        jsonObject.put("total", roles.getTotal());

        return new ResultJson(jsonObject);
    }
    @ApiOperation(value = "角色查询", notes = "角色查询")
    @RequestMapping(value = "/getList/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResultJson getList(@RequestBody Role role, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageInfo<Role> roles = roleService.findRoleByPage(role, pageNum, pageSize);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roles", roles.getList());
        jsonObject.put("total", roles.getTotal());

        return new ResultJson(jsonObject);
    }
    /**
     * 新建角色
     *
     * @return
     */
    @ApiOperation(value = "新建角色", notes = "新建角色")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultJson save(@RequestBody Role role) {
        role.setCreateTime(new Date());
        if (roleService.save(role) > 0) {
            return new ResultJson(role);
        } else {
            return new ResultJson(ResponseCode.CREATE_ERROR, null, "创建失败");
        }
    }

    /**
     * 更新角色
     *
     * @return
     */
    @ApiOperation(value = "更新角色", notes = "更新角色")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson update(@RequestBody Role role) {
        role.setUpdateTime(new Date());
        if (roleService.update(role) > 0) {
            return new ResultJson(role);
        } else {
            return new ResultJson(ResponseCode.UPDATE_ERROR, null, "更新失败");
        }
    }

    /**
     * 删除角色
     *
     * @return
     */
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResultJson delete(
            @ApiParam(value = "角色ID", readOnly = true)
            @PathVariable Integer id) {
        if (roleService.get(id) != null) {
            if (roleService.delete(id) > 0) {
                return new ResultJson().ok();
            } else {
                return new ResultJson(ResponseCode.DELETE_ERROR, null, "删除失败");
            }
        } else {
            return new ResultJson(ResponseCode.GET_ERROR, null, "查询失败");
        }
    }

    /**
     * 删除角色下权限
     *
     * @return
     */
    @ApiOperation(value = "删除角色下权限", notes = "删除角色下权限")
    @RequestMapping(value = "/roleDelete/{roleId}/auth/{authId}", method = RequestMethod.POST)
    public ResultJson deleteAuth(
            @ApiParam(value = "角色ID", readOnly = true)
            @PathVariable Integer roleId,
            @ApiParam(value = "权限ID", readOnly = true)
            @PathVariable Integer authId
    ) {
        Role role = roleService.get(roleId);

        // 原角色下的所有权限
        List<String> roleAuthIds = new ArrayList<>(Arrays.asList(role.getIds().split(",")));

        List<String> authIds = new ArrayList<>();
        authIds.add(authId.toString());
        // 获得所有的权限
        List<Auth> authList = authService.getList(new Auth());
        // 获取该权限下的所有权限
        getChild(authId, authList, authIds);

        // 删除后的角色下的所有权限
        listrem(roleAuthIds, authIds);

        role.setIds(String.join(",", roleAuthIds));
        roleService.update(role);
        return new ResultJson(role);
    }

    /**
     * 从listA里删除listB里有的数据
     *
     * @param listA
     * @param listB
     * @return
     */
    public static List<String> listrem(List<String> listA, List<String> listB) {
        for (int i = 0; i < listA.size(); i++) {
            for (int j = 0; j < listB.size(); j++) {
                if (listA.get(i).equals(listB.get(j))) {
                    listA.remove(i);
                }
            }
        }
        return listA;
    }


    /**
     * 获取子节点
     *
     * @param id       父节点id
     * @param authList 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<Auth> getChild(Integer id, List<Auth> authList, List<String> authIds) {
        //设置子菜单
        List<Auth> childList = new ArrayList<Auth>();
        for (Auth nav : authList) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (nav.getPid() == id) {
                childList.add(nav);
            }
        }
        //递归子节点
        for (Auth nav : childList) {
            authIds.add(nav.getId().toString());
            nav.setChildren(getChild(nav.getId(), authList, authIds));
        }
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<Auth>();
        }
        return childList;
    }
}
