package com.kitnet.kitnet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TermsNotAcceptedException extends RuntimeException {
    public TermsNotAcceptedException(String message) {
        super(message);
    }

    public TermsNotAcceptedException() {
        super("Terms of Use must be accepted to proceed.");
    }
}