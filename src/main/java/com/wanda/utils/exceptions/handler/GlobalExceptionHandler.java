package com.wanda.utils.exceptions.handler;

import com.wanda.utils.exceptions.CustomException;
import com.wanda.utils.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
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
                e.getMessage()
        );

        return new ResponseEntity<>(error, e.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAnyException(CustomException e) {



        ErrorResponse error = new ErrorResponse(
                false,
                "Something Went Wrong",
                (e.getMessage() != null) ? e.getMessage() : "Internal Server Error"
        );

        return new ResponseEntity<>(error, e.getStatusCode());
    }
}
