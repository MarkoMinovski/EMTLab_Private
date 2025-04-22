package com.emt.springbackendapi.model.exception;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super("Incorrect password");
    }
}
