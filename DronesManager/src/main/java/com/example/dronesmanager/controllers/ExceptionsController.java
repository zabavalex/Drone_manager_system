package com.example.dronesmanager.controllers;

import com.example.dronesmanager.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
@Slf4j
public class ExceptionsController {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Error> handle(RuntimeException e){
        e.printStackTrace();
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.OK);
    }
}
