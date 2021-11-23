package com.example.backgroundsystem.entity;

import org.springframework.http.HttpStatus;

public class Result<T> {
    private Boolean flag;
    private HttpStatus code;
    private String message;
    private T data;

    public Result(Boolean flag, HttpStatus code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, HttpStatus code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
