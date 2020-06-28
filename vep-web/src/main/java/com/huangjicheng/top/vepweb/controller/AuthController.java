package com.huangjicheng.top.vepweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.AuthService;
import com.huangjicheng.top.vepcommont.util.ResponseCode;
import com.huangjicheng.top.vepcommont.util.ResultJson;
import com.huangjicheng.top.vepdao.entity.Auth;
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
@RequestMapping("/auth")
@Api(description = "权限管理")
public class AuthController {

    @Reference
    private AuthService authService;


    /**
     * 通过用户ID获取权限对象
     *
     * @return
     */
    @ApiOperation(value = "获取权限", notes = "通过ID获取权限")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResultJson get(
            @ApiParam(value = "权限ID", readOnly = true)
            @PathVariable Integer id) {
        Auth auth = authService.get(id);
        return new ResultJson(auth);
    }

    /**
     * 获取全部权限
     *
     * @return
     */

    @ApiOperation(value = "获取全部角色", notes = "获取全部角色")
    @RequestMapping(value = "/getList/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResultJson getList(@RequestBody Auth auth, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageInfo<Auth> auths = authService.findAllAuthByPage(auth, pageNum, pageSize);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auths", auths.getList());
        jsonObject.put("total", auths.getTotal());

        return new ResultJson(jsonObject);

    }

    /**
     * 获取全部权限Tree
     *
     * @return
     */
    @ApiOperation(value = "获取全部权限", notes = "获取全部权限")
    @RequestMapping(value = "/getListTree", method = RequestMethod.GET)
    public ResultJson getListTree() {
        List<Auth> auths = authService.getListTree();
        return new ResultJson(auths);
    }

    /**
     * 新建权限
     *
     * @return
     */
    @ApiOperation(value = "新建权限", notes = "新建权限")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultJson save(Auth auth) {
        auth.setCreateTime(new Date());
        if (authService.save(auth) > 0) {
            return new ResultJson(auth);
        } else {
            return new ResultJson(ResponseCode.CREATE_ERROR, null, "创建失败");
        }
    }

    /**
     * 更新权限
     *
     * @return
     */
    @ApiOperation(value = "更新权限", notes = "更新权限")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson update(Auth auth) {
        auth.setUpdateTime(new Date());
        if (authService.update(auth) > 0) {
            return new ResultJson(auth);
        } else {
            return new ResultJson(ResponseCode.UPDATE_ERROR, null, "更新失败");
        }
    }

    /**
     * 删除权限
     *
     * @return
     */
    @ApiOperation(value = "删除权限", notes = "删除权限")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResultJson delete(
            @ApiParam(value = "权限ID", readOnly = true)
            @PathVariable Integer id) {
        if (authService.get(id) != null) {
            if (authService.delete(id) > 0) {
                return new ResultJson().ok();
            } else {
                return new ResultJson(ResponseCode.DELETE_ERROR, null, "删除失败");
            }
        } else {
            return new ResultJson(ResponseCode.GET_ERROR, null, "查询失败");

        }
    }

}
