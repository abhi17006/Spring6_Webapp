package com.springwebapp6.spring6restmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
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

    //JPA validation error Handler
    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception){
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();

        //exception.getCause().getCause() gets rollbackException
        if(exception.getCause().getCause() instanceof ConstraintViolationException){
            //cast into constrainViolationException
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception.getCause().getCause();

            List errors = constraintViolationException.getConstraintViolations().stream()
                    .map(constraintViolation -> {
                        Map<String,String> errMap = new HashMap<>();
                        errMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                        return errMap;
                    }).collect(Collectors.toList());
            return responseEntity.body(errors);
        }

            return responseEntity.build();
    }

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
