package com.ecms.backend.ecmsapi.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {
    private String errorMessage;
    private HttpStatus statusCode;

    public CustomException(String errorMessage, HttpStatus statusCode) {
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
