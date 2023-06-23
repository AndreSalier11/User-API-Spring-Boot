package com.cmas.systems.user.exceptions;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
