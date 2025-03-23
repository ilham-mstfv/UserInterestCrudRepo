package com.example.userinterestcrudrepo.exceptions;

public class NoUsersFoundException extends RuntimeException {
    public NoUsersFoundException(String message) {
        super(message);
    }

    public NoUsersFoundException(String message, Throwable error) {
        super(message, error);
    }
}
