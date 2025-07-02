package com.khanh.airbnb.exceptions;

public class AuthenticationErrorException extends RuntimeException {
    public AuthenticationErrorException(String message) {
        super(message);
    }
}
