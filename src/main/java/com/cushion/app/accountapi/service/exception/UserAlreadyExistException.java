package com.cushion.app.accountapi.service.exception;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserAlreadyExistException(){
        super("User already exists");
    }
}
