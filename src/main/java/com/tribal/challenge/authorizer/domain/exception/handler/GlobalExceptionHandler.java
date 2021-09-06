package com.tribal.challenge.authorizer.domain.exception.handler;

import com.tribal.challenge.authorizer.domain.exception.FoundingTypeNotRecognizedException;
import com.tribal.challenge.authorizer.domain.exception.TooManyRequestException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FoundingTypeNotRecognizedException.class)
    public Map<String, String> handleValidationExceptions(
            FoundingTypeNotRecognizedException ex) {
        return Map.of("foundingType", ex.getMessage() + " not recognized");
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(TooManyRequestException.class)
    public Map<String, String> handleTooManyRequest(TooManyRequestException ex){
        return Map.of("message", ex.getMessage());
    }

}
