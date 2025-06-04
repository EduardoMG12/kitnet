package com.kitnet.kitnet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class VerificationEmailAlreadySentException extends RuntimeException {
    public VerificationEmailAlreadySentException(String message) {
        super(message);
    }
}