package com.emt.springbackendapi.model.exception;

public class NoCopiesAvailableException extends Exception {
    public NoCopiesAvailableException(String bookName) {
        super("No copies available for book: " + bookName);
    }
}
