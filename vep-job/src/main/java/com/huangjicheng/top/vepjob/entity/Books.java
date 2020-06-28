package com.huangjicheng.top.vepjob.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: huangjicheng
 * @Date: 2020/6/8 22:34
 */

@Data
public class Books implements Serializable {
    private String bCate;
    private String href;
    private String sCate;
    private String bookImg;
    private String bookName;
    private String bookAuthor;
    private String bookStore;
    private String bookDate;
    private String bookPrice;
    private Date createTime;

}
