package com.example.securitytest.exceptions;

public class XSSServletException extends RuntimeException {

    public XSSServletException() {
    }

    public XSSServletException(String message) {
        super(message);
    }
}
