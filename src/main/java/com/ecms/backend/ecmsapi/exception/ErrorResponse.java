package com.ecms.backend.ecmsapi.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private String errorMessage;
    private HttpStatus  statusCode;

    public ErrorResponse(String errorMessage, HttpStatus statusCode) {
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
