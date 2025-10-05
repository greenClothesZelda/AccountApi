package com.cushion.app.accountapi.controller.exception;

public class LoginFailException extends RuntimeException {
    public LoginFailException(String message) {
        super(message);
    }
    public LoginFailException() {
        super("Login failed");
    }
    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
