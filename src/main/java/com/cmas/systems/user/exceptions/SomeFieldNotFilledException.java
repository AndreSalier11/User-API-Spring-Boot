package com.cmas.systems.user.exceptions;

public class SomeFieldNotFilledException extends RuntimeException {
    
    public SomeFieldNotFilledException() {
    }

    public SomeFieldNotFilledException(String msg) {
        super(msg);
    }
}
