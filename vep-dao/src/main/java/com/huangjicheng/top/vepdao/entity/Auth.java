package com.huangjicheng.top.vepdao.entity;

import com.huangjicheng.top.vepdao.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 22:46
 */
@Data
public class Auth extends BaseEntity implements Serializable {

    @ApiModelProperty("权限ID")
    private Integer id;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("权限父ID,0为一级目录")
    private Integer pid;

    @ApiModelProperty("权限等级分123")
    private Integer level;

    @ApiModelProperty("目录路径")
    private String path;

    // 不是数据库字段，加此字段
    private List<Auth> children;

    private List<String> ids;
}
