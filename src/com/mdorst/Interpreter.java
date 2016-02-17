package com.mdorst;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Michael Dorst
 */
public class Interpreter {

    private String expression;
    private int result;

    public String expression() {
        return expression;
    }
    public int result() {
        return result;
    }

    public void interpret(String expression) {
        this.expression = expression;
        Expression tree = parse(tokenize(expression));
        if (tree.value.equals("+")) {
            result = Integer.parseInt(tree.lhe.value) + Integer.parseInt(tree.rhe.value);
        }
    }

    private Expression parse(List<String> token) {
        Expression expr = new Expression();
        if (token.get(0).matches("-?[0-9]+(.[0-9]+)?")) {
            if (token.get(1).matches("[\\+\\-\\*/]")) {
                expr.value = token.get(1);
                expr.lhe = new Expression(token.get(0));
                if (token.get(2).matches("-?[0-9]+(.[0-9]+)?")) {
                    expr.rhe = new Expression(token.get(2));
                }
            }
        }
        return expr;
    }

    private List<String> tokenize(String expression) {
        StringTokenizer tokenizer = new StringTokenizer(expression);
        List<String> tokenList = new LinkedList<>();
        while (tokenizer.hasMoreTokens()) {
            tokenList.add(tokenizer.nextToken());
        }
        return tokenList;
    }
}
