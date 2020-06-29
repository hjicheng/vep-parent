package com.huangjicheng.top.vepweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.UserService;
import com.huangjicheng.top.vepcommont.util.AESUtil;
import com.huangjicheng.top.vepcommont.util.JwtUtil;
import com.huangjicheng.top.vepcommont.util.ResponseCode;
import com.huangjicheng.top.vepcommont.util.ResultJson;
import com.huangjicheng.top.vepdao.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 20:32
 */

@RestController
@RequestMapping("/user")
@Api(description = "用户管理")
public class UserController {

    @Reference
    private UserService userService;

    /**
     * 用户登录
     *
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户登录，获取用户信息")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultJson login(@RequestBody User user) {
        user.setPassword(AESUtil.AES_cbc_encrypt(user.getPassword()));
        User login = userService.login(user);
        login.setToken(JwtUtil.getToken(login.getId().toString(), user.getPassword(), 1000 * 60 * 60));
        return new ResultJson(login);
    }


    /**
     * 通过用户ID获取用户对象
     *
     * @return
     */
    @ApiOperation(value = "获取用户", notes = "通过ID获取用户对想息")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResultJson get(
            @ApiParam(value = "用户ID", readOnly = true)
            @PathVariable Integer id) {
        User user = userService.get(id);
        return new ResultJson(user);
    }

    /**
     * 获取全部用户
     *
     * @return
     */
    @ApiOperation(value = "获取全部用户", notes = "获取全部用户")
    @RequestMapping(value = "/getList/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResultJson getList(@RequestBody User user, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageInfo<User> users = userService.findAllUserByPage(user, pageNum, pageSize);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("users", users.getList());
        jsonObject.put("total", users.getTotal());

        return new ResultJson(jsonObject);
    }

    /**
     * 用户分配角色
     *
     * @return
     */
    @ApiOperation(value = "用户分配角色", notes = "用户分配角色")
    @RequestMapping(value = "/setRole/{id}/{roleId}", method = RequestMethod.POST)
    public ResultJson setRole( @PathVariable Integer id, @PathVariable Integer roleId) {
        User user = new User();
        user.setId(id);
        user.setRoleId(roleId);
        userService.update(user);
        return new ResultJson(userService.get(id));
    }

    /**
     * 修改用户状态
     *
     * @return
     */
    @ApiOperation(value = "修改用户状态", notes = "修改用户状态 ")
    @RequestMapping(value = "/updateState/{id}/{state}", method = RequestMethod.POST)
    public ResultJson updateState(@PathVariable Integer id, @PathVariable Integer state) {
        User user = userService.get(id);
        user.setState(state == 1 ? 0 : 1);
        userService.update(user);
        return new ResultJson(user);
    }

    /**
     * 新建用户
     *
     * @return
     */
    @ApiOperation(value = "新建用户", notes = "新建用户")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultJson save(@RequestBody User user) {
        user.setCreateTime(new Date());
        user.setPassword(AESUtil.AES_cbc_encrypt(user.getPassword()));
        if (userService.save(user) > 0) {
            return new ResultJson(user);
        } else {
            return new ResultJson(ResponseCode.CREATE_ERROR, null, "创建失败");
        }
    }

    /**
     * 更新用户
     *
     * @return
     */
    @ApiOperation(value = "更新用户", notes = "更新用户")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultJson update(@RequestBody User user) {
        user.setUpdateTime(new Date());
        if (userService.update(user) > 0) {
            return new ResultJson(user);
        } else {
            return new ResultJson(ResponseCode.UPDATE_ERROR, null, "更新失败");
        }
    }

    /**
     * 删除用户
     *
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResultJson delete(
            @ApiParam(value = "用户ID", readOnly = true)
            @PathVariable Integer id) {
        if (userService.get(id) != null) {
            if (userService.delete(id) > 0) {
                return new ResultJson().ok();
            } else {
                return new ResultJson(ResponseCode.DELETE_ERROR, null, "删除失败");
            }
        } else {
            return new ResultJson(ResponseCode.GET_ERROR, null, "查询失败");

        }
    }

}
