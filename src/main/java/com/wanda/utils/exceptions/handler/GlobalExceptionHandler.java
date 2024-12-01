package com.wanda.utils.exceptions.handler;

import com.wanda.utils.exceptions.CustomException;
import com.wanda.utils.exceptions.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse error = new ErrorResponse(
                false,
                "Something Went Wrong",
                e.getMessage(),
                e.getCode()
        );

        return new ResponseEntity<>(error, e.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAnyException(CustomException e) {



        ErrorResponse error = new ErrorResponse(
                false,
                "Something Went Wrong",
                (e.getMessage() != null) ? e.getMessage() : "Internal Server Error",
                (e.getCode().isEmpty()) ? "ERROR" : e.getCode()
        );

        return new ResponseEntity<>(error, e.getStatusCode());
    }
}
