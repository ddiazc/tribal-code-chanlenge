package com.tribal.challenge.authorizer.domain.exception;

public class TooManyRequestException extends RuntimeException{
    public TooManyRequestException(String message) {
        super(message);
    }
}
