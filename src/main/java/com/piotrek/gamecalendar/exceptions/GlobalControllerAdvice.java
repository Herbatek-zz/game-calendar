package com.piotrek.gamecalendar.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse badRequestHandle(BadRequestException exception) {
        return new ErrorResponse(BAD_REQUEST.value(), exception.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse notFoundHandle(NotFoundException exception) {
        return new ErrorResponse(NOT_FOUND.value(), exception.getMessage());
    }
}
