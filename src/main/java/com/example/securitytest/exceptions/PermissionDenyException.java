package com.example.securitytest.exceptions;

public class PermissionDenyException extends Exception{
    public PermissionDenyException(String message){
        super(message);
    }
}
