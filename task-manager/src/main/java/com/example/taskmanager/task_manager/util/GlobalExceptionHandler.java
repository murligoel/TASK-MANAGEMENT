package com.example.taskmanager.task_manager.util;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidFormat(HttpMessageNotReadableException ex) {

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ife) {

            Class<?> targetType = ife.getTargetType();

            if (targetType.isEnum()) {
                return ResponseEntity.badRequest()
                        .body("Invalid status value. Allowed values: " +
                                Arrays.toString(targetType.getEnumConstants()));
            }

            if (targetType.equals(LocalDate.class)) {
                return ResponseEntity.badRequest()
                        .body("Invalid Date Format. Expected YYYY-MM-DD");
            }
        }

        return ResponseEntity.badRequest().body("Invalid request body");
    }
}

