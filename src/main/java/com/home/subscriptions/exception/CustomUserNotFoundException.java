package com.home.subscriptions.exception;

public class CustomUserNotFoundException extends RuntimeException {
    public CustomUserNotFoundException(String message) {
        super(message);
    }
}
