package com.skill.dto;

/**
 * 请求响应的结果
 * 封装JSON数据
 */
public class ResponseResult<T> {
    private boolean success;
    private Object data;
    private String message;

    public ResponseResult() {}

    public ResponseResult(boolean success, Object data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public ResponseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
