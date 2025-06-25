package com.kitnet.kitnet.exception;

public class DocumentAlreadyExistsException extends RuntimeException {
    public DocumentAlreadyExistsException(String message) {
        super(message);
    }
}
