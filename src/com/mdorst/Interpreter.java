package com.mdorst;

import com.mdorst.container.hash.HashMap;
import com.mdorst.container.list.LinkedList;
import com.mdorst.container.list.Queue;
import com.mdorst.container.list.Stack;
import com.mdorst.exception.InvalidExpressionException;

import java.util.StringTokenizer;

public class Interpreter {

    private class Token {
        private double value;
        private String name;
        public Token(double v) {
            value = v;
        }
        public Token(String n) {
            name = n;
        }
        public double value() {
            if (name == null) {
                return value;
            } else {
                return symbols.get(name);
            }
        }
        public String name() {
            return name;
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

    /**
     * Standard interface for all operators.
     * Allows operators to be stored in a HashMap
     */
    private interface Operator {
        Token call(Token op1, Token op2);
    }

    /**
     * Maps string representations of operators ("+", "-", "*", etc.)
     * to functions which can perform them.
     * Usage:
     * operators.get("+").call(3, 2) // result: 5
     */
    private HashMap<String, Operator> operators = new HashMap<>(String::hashCode);
    private HashMap<String, Integer> priority = new HashMap<>(String::hashCode);
    /**
     * Maps variable names to values.
     */
    private HashMap<String, Double> symbols;

    /**
     * Sets up operators to be accessed via string identifiers.
     */
    public Interpreter() {
        operators.add("+", (op1, op2) -> new Token(op1.value + op2.value));
        priority .add("+", 3);
        operators.add("-", (op1, op2) -> new Token(op1.value - op2.value));
        priority .add("-", 3);
        operators.add("*", (op1, op2) -> new Token(op1.value * op2.value));
        priority .add("*", 2);
        operators.add("/", (op1, op2) -> new Token(op1.value / op2.value));
        priority .add("/", 2);
        operators.add("=", (op1, op2) -> {
            symbols.add(op1.name, op2.value);
            return new Token(op2.value);
        });
        priority .add("=", 0);
    }

    /**
     * Takes an expression, stores it, then interprets it, and returns the result.
     * @param expression The expression to be interpreted
     * @return The result of the expression
     */
    public double interpret(String expression) throws InvalidExpressionException {
        LinkedList<Token> expr = new LinkedList<>();
        /**
         * Tokenize the expression
         */
        {
            StringTokenizer tokenizer = new StringTokenizer(expression);
            while (tokenizer.hasMoreTokens()) {
                expr.add(new Token(tokenizer.nextToken()));
            }
        }
        /**
         * Create postfix expression
         */
        Stack<Token> opStack = new Stack<>();
        Queue<Token> postfix = new Queue<>();
        for (Token token : expr) {
            /**
             * Variables
             */
            if (token.name.matches("[a-z]\\w+")) {
                postfix.enqueue(token);
            } else
            /**
             * Numeric literals
             */
            if (token.name.matches("\\d+")) {
                postfix.enqueue(new Token(Double.valueOf(token.name)));
            }
            /**
             * Operators
             */
            if (token.name.matches("[\\+\\-*/]")) {
                while (!opStack.isEmpty() &&
                        priority.get(opStack.top().name) < priority.get(token.name)) {
                    postfix.enqueue(opStack.pop());
                }
                opStack.push(token);
            }
        }
        while (!opStack.isEmpty()) {
            postfix.enqueue(opStack.pop());
        }
        /**
         * Evaluate postfix expression
         */
        Token result = new Token(0);
        while (!postfix.isEmpty()) {
            try {
                Token operand1 = postfix.dequeue();
                Token operand2 = postfix.dequeue();
                Token operator = postfix.dequeue();
                result = operators.get(operator.name).call(operand1, operand2);
            } catch (NullPointerException e) {
                /**
                 * Either one of the operands wasn't in the symbol table,
                 * or there were not enough operands provided
                 */
                throw new InvalidExpressionException(expr + " is not a valid expression.");
            }
        }
        return result.value;
    }
}
