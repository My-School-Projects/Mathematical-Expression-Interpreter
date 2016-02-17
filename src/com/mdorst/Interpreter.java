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
