package com.example.dronesmanager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler
    public ResponseEntity<Error> handle(Exception e){
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.OK);
    }
}
