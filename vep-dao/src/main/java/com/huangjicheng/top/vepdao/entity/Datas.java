package com.huangjicheng.top.vepdao.entity;

import com.huangjicheng.top.vepdao.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 22:46
 */
@Data
@Getter
@Setter
public class Datas extends BaseEntity implements Serializable {

    // 不是数据库字段，加此字段
    private String key;

    private String type;

    private String name;

    private String value;
}
