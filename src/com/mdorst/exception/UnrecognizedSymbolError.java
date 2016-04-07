package com.mdorst.exception;

public class UnrecognizedSymbolError extends SyntaxError {
    public UnrecognizedSymbolError(String message) {
        super(message);
    }
    public UnrecognizedSymbolError() {}
}
