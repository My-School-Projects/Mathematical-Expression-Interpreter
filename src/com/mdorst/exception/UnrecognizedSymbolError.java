package com.mdorst.exception;

/**
 * Michael Dorst
 */
public class UnrecognizedSymbolError extends SyntaxError {
    public UnrecognizedSymbolError(String message) {
        super(message);
    }
    public UnrecognizedSymbolError() {}
}
