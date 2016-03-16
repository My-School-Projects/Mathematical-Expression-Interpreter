package com.mdorst;

import com.mdorst.container.hash.HashMap;

public class Operator {
    private int priority;
    private Operation operation;

    public Operator(int p, Operation op) {
        priority = p;
        operation = op;
    }
    public int priority() {
        return priority;
    }
    public Token call(Token op1, Token op2) {
        return operation.call(op1, op2);
    }
}
