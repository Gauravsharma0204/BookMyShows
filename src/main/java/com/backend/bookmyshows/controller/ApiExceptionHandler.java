package com.backend.bookmyshows.controller;

import com.backend.bookmyshows.dto.ApiError;
import com.backend.bookmyshows.service.ResourceNotFoundException;
import com.backend.bookmyshows.service.SeatUnavailbleException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiError> notFound(ResourceNotFoundException exception, HttpServletRequest request){
        return error(HttpStatus.NOT_FOUND,exception.getMessage(),request);
    }

    @ExceptionHandler({SeatUnavailbleException.class, IllegalArgumentException.class})
    ResponseEntity<ApiError> conflict(RuntimeException exception, HttpServletRequest request){
        return error(HttpStatus.CONFLICT,exception.getMessage(),request);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    ResponseEntity<ApiError> validation(Exception exception, HttpServletRequest request){
        return error(HttpStatus.BAD_REQUEST, "Request Validation fail", request);
    }

    private ResponseEntity<ApiError> error(HttpStatus status, String msg, HttpServletRequest request){
        return ResponseEntity.status(status).body(new ApiError(Instant.now(),status.value(),status.getReasonPhrase(),msg,request.getRequestURI()));
    }
}
