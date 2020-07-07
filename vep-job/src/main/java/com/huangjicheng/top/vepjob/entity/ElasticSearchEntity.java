package com.huangjicheng.top.vepjob.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ElasticSearchEntity implements Serializable {

    private String name;
    private String value;
    private String type;
    private String createTime;
}
