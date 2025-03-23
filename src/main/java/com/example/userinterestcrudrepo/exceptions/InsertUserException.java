package com.example.userinterestcrudrepo.exceptions;

public class InsertUserException extends RuntimeException {
    public InsertUserException(String message) {
        super(message);
    }

    public InsertUserException(String message, Throwable error) {
        super(message, error);
    }
}

