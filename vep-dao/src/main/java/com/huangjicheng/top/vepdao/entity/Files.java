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
public class Files extends BaseEntity implements Serializable {

    @ApiModelProperty("文件ID")
    private Integer id;

    @ApiModelProperty("文件名")
    private String fileName;

    @ApiModelProperty("文件类型")
    private String fileType;


    @ApiModelProperty("文件状态")
    private Integer state;

    @ApiModelProperty("文件路径")
    private String path;


    @ApiModelProperty("文件路径")
    private String hdfsPath;

    @ApiModelProperty("文件上传状态")
    private Integer uploadStatus;

}
