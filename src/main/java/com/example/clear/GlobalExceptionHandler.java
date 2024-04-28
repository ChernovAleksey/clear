package com.example.clear;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {



  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception) {
    return ResponseEntity.status(404).body(exception.getMessage());
  }

  @ExceptionHandler(IllegalAccessException.class)
  public ResponseEntity<?> handleIllegalAccessException(IllegalAccessException exception) {
    return ResponseEntity.status(401).body(exception.getMessage());
  }


  }




