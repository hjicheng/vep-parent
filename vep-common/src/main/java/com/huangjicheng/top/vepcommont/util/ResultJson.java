package com.huangjicheng.top.vepcommont.util;

import lombok.Data;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 23:31
 */

@Data
public class ResultJson {
    private static final long serialVersionUID = -4800793124936904868L;
    public static final int SUCCESS = 200;
    public static final int ERROR = 201;

    /**
     * 返回是否成功的状态,200表示成功,
     * 201或其他值 表示失败
     */
    private int state;
    /**
     * 成功时候,返回的JSON数据
     */
    private Object data;
    /**
     * 是错误时候的错误消息
     */
    private String message;


    public ResultJson() {
    }


    public ResultJson(int state, Object data, String message) {
        this.state = state;
        this.data = data;
        this.message = message;
    }

    public ResultJson(Throwable e) {
        state = ERROR;
        data = null;
        message = e.getMessage();
    }

    public ResultJson(Object data) {
        state = SUCCESS;
        this.data = data;
        message = "";
    }

    public ResultJson ok() {
        return new ResultJson(SUCCESS, null, "成功");

    }
}
