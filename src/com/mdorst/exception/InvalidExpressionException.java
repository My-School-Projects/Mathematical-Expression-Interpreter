package com.mdorst.exception;

public class InvalidExpressionException extends Exception {
    public InvalidExpressionException(String message) {
        super(message);
    }
    public InvalidExpressionException() {}
}
