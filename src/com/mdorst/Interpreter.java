package com.mdorst;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Michael Dorst
 */
public class Interpreter {
    /**
     * The expression to be evaluated
     */
    private String expression;
    /**
     * The result of of interpret() will be stored here
     */
    private int result;

    /**
     * Accessors
     */
    public String expression() {
        return expression;
    }
    public int result() {
        return result;
    }

    /**
     * Takes an expression, stores it, then interprets it and stores the result.
     * @param expression The expression to be stored and interpreted
     */
    public void interpret(String expression) {

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
