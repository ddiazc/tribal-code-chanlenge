package com.tribal.challenge.authorizer.domain.exception;

public class FoundingTypeNotRecognizedException extends RuntimeException {
    public FoundingTypeNotRecognizedException(String message) {
        super(message);
    }
}
