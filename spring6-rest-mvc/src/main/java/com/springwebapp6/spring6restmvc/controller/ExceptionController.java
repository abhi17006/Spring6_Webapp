//package com.springwebapp6.spring6restmvc.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice //globally handle exception
//public class ExceptionController {
//    //handler method for handling Not Found exception  Globally controller class using @ExceptionHandler
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException(){
//        return ResponseEntity.notFound().build();
//    }
//}
