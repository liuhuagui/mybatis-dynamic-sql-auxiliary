package com.github.liuhuagui.mybatis.auxiliary.model;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author liuhuagui
 */
@Getter
public class Response<T> implements Serializable {
    /**
     * 返回码，默认0成功，-1失败 —— 可根据业务重新定义。
     */
    private Long code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    private Response(Long code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Response<T> response(Long code, String msg, T data) {
        return new Response<>(code, msg, data);
    }

    public static <T> Response<T> ok(T data) {
        return response(0L, null, data);
    }

    public static Response ok(String msg) {
        return response(0L, msg, null);
    }

    public static Response error(String msg) {
        return response(-1L, msg, null);
    }

    public static Response error(Long code, String msg) {
        return response(code, msg, null);
    }
}
