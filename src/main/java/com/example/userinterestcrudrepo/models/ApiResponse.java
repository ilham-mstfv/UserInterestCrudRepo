package com.example.userinterestcrudrepo.models;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int code, String message) {
        this(code, message, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                StatusCode.SUCCESS.getCode(),
                StatusCode.SUCCESS.getMessage(),
                data
        );
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(
                StatusCode.SUCCESS.getCode(),
                StatusCode.SUCCESS.getMessage(),
                null
        );
    }

    public static <T> ApiResponse<T> error(int code, String message, T data) {
        return new ApiResponse<>(
                code,
                message,
                data
        );
    }

    public static ApiResponse<Void> error(int code, String message) {
        return new ApiResponse<>(
                code,
                message,
                null
        );
    }
}
