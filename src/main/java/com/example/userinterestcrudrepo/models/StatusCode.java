package com.example.userinterestcrudrepo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    SUCCESS(0, 200, "Success"),
    VALIDATION_ERROR(2001, 400, "Validation Error"),
    JSON_VALIDATION_ERROR(2001, 400, "JSON Validation Error"),
    SERVICE_ERROR(2002, 200, "Service Error"),
    SERVER_ERROR(3001, 200, "Internal Server Error"),
    ;

    private final int code;
    private final int httpStatus;
    private final String message;
}
