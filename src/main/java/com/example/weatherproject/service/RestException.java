package com.example.weatherproject.service;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {
    HttpStatus status = HttpStatus.BAD_REQUEST;

    RestException() {
        super("Bad Request");
    }

    public RestException(String message, HttpStatus httpStatus) {
        super(message);
        status = httpStatus;
    }

}
