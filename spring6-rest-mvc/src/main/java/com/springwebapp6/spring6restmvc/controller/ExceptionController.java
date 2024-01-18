package com.springwebapp6.spring6restmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
//for the Validation
@ControllerAdvice //globally handle exception
public class ExceptionController {
    //handler method for handling Not Found exception  Globally controller class using @ExceptionHandler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){
        //get response String content from MVC Result using below commented code with above function
//        return ResponseEntity.badRequest().body(exception.getBindingResult().getFieldError());

        //below code get respose with key-value pairs from fieldErrors of getField and DefaultMessage
        List errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String,String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorList);
    }
}
