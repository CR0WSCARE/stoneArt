package com.example.backend.exception;

public class serviceException extends RuntimeException {

    private String code;

    public serviceException(String message) {
        super(message);
        this.code = "500"; // 默认错误码
    }

    public serviceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
