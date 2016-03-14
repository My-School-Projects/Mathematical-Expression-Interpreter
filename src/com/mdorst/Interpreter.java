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
    private interface Operation {
        Token call(Token op1, Token op2);
    }

    private class Operator {
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

    /**
     * Maps string representations of operators ("+", "-", "*", etc.)
     * to functions which can perform them.
     * Usage:
     * operators.get("+").call(3, 2) // result: 5
     */
    private HashMap<String, Operator> operators = new HashMap<>(String::hashCode);
    /**
     * Maps variable names to values.
     */
    private HashMap<String, Double> symbols;

    /**
     * Sets up operators to be accessed via string identifiers.
     */
    public Interpreter() {
        operators.add("+", new Operator(3, (op1, op2) -> new Token(op1.value + op2.value)));
        operators.add("-", new Operator(3, (op1, op2) -> new Token(op1.value - op2.value)));
        operators.add("*", new Operator(2, (op1, op2) -> new Token(op1.value * op2.value)));
        operators.add("/", new Operator(2, (op1, op2) -> new Token(op1.value / op2.value)));
        operators.add("=", new Operator(0, (Token op1, Token op2) -> {
            symbols.add(op1.name, op2.value);
            return new Token(op2.value);
        }));
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
                        operators.get(opStack.top().name).priority < operators.get(token.name).priority) {
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
        Stack<Token> eval = new Stack<>();
        while (!postfix.isEmpty()) {
            eval.push(postfix.dequeue());
            if (!postfix.isEmpty()) {
                eval.push(postfix.dequeue());
                try {
                    eval.push(operators.get(postfix.dequeue().name).call(eval.pop(), eval.pop()));
                } catch (NullPointerException e) {
                    /**
                     * One of the operands wasn't in the symbol table.
                     */
                    throw new InvalidExpressionException(expr + " is not a valid expression.");
                }
            }
        }
        while (eval.size() > 1) {
            try {
                eval.push(operators.get(eval.pop().name).call(eval.pop(), eval.pop()));
            } catch (NullPointerException e) {
                /**
                 * One of the operands wasn't in the symbol table.
                 */
                throw new InvalidExpressionException(expr + " is not a valid expression.");
            }
        }
        return eval.pop().value;
    }
}
