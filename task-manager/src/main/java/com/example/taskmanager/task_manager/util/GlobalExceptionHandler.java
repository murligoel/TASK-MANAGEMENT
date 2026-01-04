package com.example.taskmanager.task_manager.util;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidFormat(HttpMessageNotReadableException ex) {
        String message = ex.getCause() instanceof InvalidFormatException
                ? "Invalid Date Format. Expected YYYY-MM-DD"
                : "Invalid request body";

        return ResponseEntity.badRequest().body(message);
    }
}
