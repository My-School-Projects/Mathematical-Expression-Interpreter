package com.mdorst;

/**
 * Standard interface for all operations (+, -, *, etc.)
 */
public interface Operation {
    Token call(Token op1, Token op2);
}
