package com.huangjicheng.top.vepdao.entity;

import com.huangjicheng.top.vepdao.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 22:44
 */

@Data
public class Role extends BaseEntity implements Serializable {

    @ApiModelProperty("角色ID")
    private Integer id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色描述")
    private String roleDesc;

    @ApiModelProperty("角色描述")
    private String ids;

    @ApiModelProperty("角色下的权限树")
    private List<Auth> children;

}
