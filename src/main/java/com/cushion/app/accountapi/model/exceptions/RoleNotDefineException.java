package com.cushion.app.accountapi.model.exceptions;

public class RoleNotDefineException extends RuntimeException {
    public RoleNotDefineException(String message) {
        super(message);
    }
    public RoleNotDefineException(String message, Throwable cause) {
        super(message, cause);
    }
    public RoleNotDefineException(){
        super("Role is not defined");
    }
}
