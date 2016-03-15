package com.mdorst.exception;

/**
 * Michael Dorst
 */
public class UnrecognizedSymbolError extends InvalidExpressionException {
    public UnrecognizedSymbolError(String message) {
        super(message);
    }
    public UnrecognizedSymbolError() {}
}
