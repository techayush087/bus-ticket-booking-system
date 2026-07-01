package com.busticket.exception;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String msg) {
        super(msg);
    }
}
