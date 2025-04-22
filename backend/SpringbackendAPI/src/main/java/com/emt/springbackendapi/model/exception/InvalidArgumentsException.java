package com.emt.springbackendapi.model.exception;

public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException() {
        super("Please provide a username and password");
    }
}
