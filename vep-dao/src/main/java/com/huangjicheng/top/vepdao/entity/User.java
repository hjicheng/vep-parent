package com.huangjicheng.top.vepdao.entity;

import com.huangjicheng.top.vepdao.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.io.Serializable;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:04
 */

@Data
public class User extends BaseEntity implements Serializable {

    @ApiModelProperty("用户ID")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;


    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("状态：1禁用，2：开启")
    private Integer state;

    private Boolean stateStr;

    private String roleName;

    @ApiModelProperty("登录后的唯一验证")
    private String token;
}
