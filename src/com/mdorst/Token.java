package com.mdorst;

public class Token {
    private double value;
    private String name;

    public Token(String name) {
        this.name = name;
    }
    public Token(double value) {
        this.value = value;
    }

    public String name() {
        return name;
    }
    public double value() {
        return value;
    }
    public boolean isOperator() {
        return isUnaryOperator() || isBinaryOperator();
    }
    public boolean isUnaryOperator() {
        return name != null && name.matches("-|sin|cos|tan|cot|sec|csc|abs|sqrt");
    }
    public boolean isBinaryOperator() {
        return name != null && name.matches("[\\+\\*/=]");
    }
    public boolean isVariable() {
        return name != null && name.matches("([a-z]|[A-Z]|_)\\w*");
    }

    public String toString() {
        if (name == null) {
            return Double.toString(value);
        } else {
            return name;
        }
    }

    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }
}
