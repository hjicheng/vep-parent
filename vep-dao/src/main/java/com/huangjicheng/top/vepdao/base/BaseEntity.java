package com.huangjicheng.top.vepdao.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 23:15
 */

@Data
public class BaseEntity implements Serializable {

    @ApiModelProperty("创建是时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    private String createTimeStr;

    private String updateTimeStr;
}
