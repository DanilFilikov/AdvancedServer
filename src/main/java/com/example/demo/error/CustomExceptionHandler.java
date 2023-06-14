package com.example.demo.error;

import com.example.demo.error.ErrorCodes;
import com.example.demo.error.ValidationConstants;
import com.example.demo.exception.CustomException;
import com.example.demo.model.CustomSuccessResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomSuccessResponse> handle(CustomException e) {
        List<Integer> codes = new ArrayList<>();
        codes.add(ErrorCodes.getError(e.getMessage()));
        return new ResponseEntity<>(CustomSuccessResponse.getErrorResponse(codes, codes.get(0)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomSuccessResponse> handle(MethodArgumentNotValidException e) {
        List<Integer> codes = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(element -> ErrorCodes.getError(element.getDefaultMessage()))
                .toList();
        return new ResponseEntity<>(CustomSuccessResponse.getErrorResponse(codes, codes.get(0)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomSuccessResponse> handle(HttpMessageNotReadableException e) {
        List<Integer> codes = new ArrayList<>();
        codes.add(ErrorCodes.getError(ValidationConstants.HTTP_MESSAGE_NOT_READABLE_EXCEPTION));
        return new ResponseEntity<>(CustomSuccessResponse.getErrorResponse(codes, codes.get(0)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomSuccessResponse> handle(ConstraintViolationException e) {
        List<Integer> codes = e.getConstraintViolations()
                .stream()
                .map(element -> ErrorCodes.getError(element.getMessage()))
                .toList();
        return new ResponseEntity<>(CustomSuccessResponse.getErrorResponse(codes, (codes.get(0))), HttpStatus.BAD_REQUEST);
    }
}
