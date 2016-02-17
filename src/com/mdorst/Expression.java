package com.mdorst;

/**
 * Michael Dorst
 */
public class Expression {
    public String value;
    public Expression lhe, rhe;

    public Expression(String value) {
        this.value = value;
    }
    public Expression() {}
}
