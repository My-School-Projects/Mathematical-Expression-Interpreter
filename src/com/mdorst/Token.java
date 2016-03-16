package com.mdorst;

public class Token {
    private Double value;
    private String name;
    private Operator operator;
    public Token(Double v) {
        value = v;
    }
    public Token(String n) {
        name = n;
    }
    public Token(Operator o) {
        operator = o;
    }
    public Double value() {
        return value;
    }
    public String name() {
        return name;
    }
    public Token call(Token op1, Token op2) {
        return operator.call(op1, op2);
    }
    public boolean isOperator() {
        return operator != null;
    }
    @Override
    public String toString() {
        if (name == null) {
            return Double.toString(value);
        } else {
            return name;
        }
    }
}
