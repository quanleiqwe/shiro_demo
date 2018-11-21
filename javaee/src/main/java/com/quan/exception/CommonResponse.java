package com.quan.exception;

public class CommonResponse<T> {

    private String status;

    private String message;

    private T data;

    public CommonResponse() {
    }

    public CommonResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public CommonResponse<T> setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommonResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public CommonResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static CommonResponse successResponse(Object data){
        CommonResponse response = new CommonResponse<>();
        response.setData(data);
        return response;
    }
}
