package com.huangjicheng.top.vepdao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huangjicheng.top.vepdao.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BlogCategory  extends BaseEntity implements Serializable {

    private Integer id;

    private String categoryName;

    private Integer categoryRank;

    private Integer isDeleted;

}