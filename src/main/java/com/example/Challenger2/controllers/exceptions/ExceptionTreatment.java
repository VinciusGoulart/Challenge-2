package com.example.Challenger2.controllers.exceptions;

import com.example.Challenger2.services.exceptions.BadRequestException;
import com.example.Challenger2.services.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionTreatment {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequest(BadRequestException badRequest, HttpServletRequest request) {
        StandardError error = new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request",
                badRequest.getMessage(), request.getRequestURI());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity invalidField(MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        List<String> errors = new ArrayList<>();
        for (FieldError field : fieldErrors) {
            errors.add(field.getDefaultMessage());
        }

        StandardError error = new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value(), "Invalid fields",
                errors.toString(), request.getRequestURI());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity messageNotReadable(HttpMessageNotReadableException exception, HttpServletRequest request) {
        String errorMessage = "Invalid request body";

        if (exception.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) exception.getCause();
            errorMessage = "Invalid field value " + invalidFormatException.getPath().get(0).getFieldName();
        }

        StandardError error = new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request",
                errorMessage, request.getRequestURI());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFound(NotFoundException notFound, HttpServletRequest request) {
        StandardError error = new StandardError(Instant.now(), HttpStatus.NOT_FOUND.value(), "Not Found",
                notFound.getMessage(), request.getRequestURI());

        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
