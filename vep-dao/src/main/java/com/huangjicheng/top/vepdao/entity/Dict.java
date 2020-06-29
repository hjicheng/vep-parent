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
public class Dict extends BaseEntity implements Serializable {

    @ApiModelProperty("文件ID")
    private Integer id;

    @ApiModelProperty("字典名")
    private String dictName;

    @ApiModelProperty("字典类型")
    private String dictValue;

    @ApiModelProperty("字典描述")
    private String dictDesc;

}
