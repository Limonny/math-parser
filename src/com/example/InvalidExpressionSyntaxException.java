package com.example;

public class InvalidExpressionSyntaxException extends RuntimeException {

    public InvalidExpressionSyntaxException(String message) {
        super(message);
    }
}