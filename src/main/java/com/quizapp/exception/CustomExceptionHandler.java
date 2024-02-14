package com.quizapp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error->errors.put(error.getField(),error.getDefaultMessage()));
        return errors;
    }

    @ExceptionHandler(UserRegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> userRegException(UserRegistrationException ex){
        Map<String, String>error = new HashMap<>();
        error.put("error",ex.getMessage());
        return error;
    }

    @ExceptionHandler(AnswerEvaluationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> AnswerEvaluationException(UserRegistrationException ex){
        Map<String, String>error = new HashMap<>();
        error.put("error",ex.getMessage());
        return error;
    }
}
