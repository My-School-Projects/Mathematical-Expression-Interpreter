package com.mdorst;

import com.mdorst.container.hash.HashMap;
import com.mdorst.container.list.LinkedList;
import com.mdorst.container.list.Queue;
import com.mdorst.container.list.Stack;
import com.mdorst.exception.SyntaxError;
import com.mdorst.exception.UnrecognizedSymbolError;

import java.util.StringTokenizer;

public class Interpreter {

    /**
     * Maps variable names to values.
     */
    private HashMap<String, Double> symbols = new HashMap<>(String::hashCode);

    /**
     * Takes an expression, interprets it, and returns the result.
     * @param expression The expression to be interpreted
     * @return The result of the expression
     */
    public double interpret(String expression) throws SyntaxError {
        LinkedList<String> expr = new LinkedList<>();
        /**
         * Tokenize the expression
         */
        {
            StringTokenizer tokenizer = new StringTokenizer(expression, "+-*/= \n\t\r\f", true);
            while (tokenizer.hasMoreTokens()) {
                expr.add(tokenizer.nextToken());
            }
        }
        /**
         * Create postfix expression
         */
        Stack<String> opStack = new Stack<>();
        Queue<Token> postfix = new Queue<>();
        for (String token : expr) {
            /**
             * Variables:
             * Always get pushed to the postfix expression
             */
            if (token.matches("([a-z]|[A-Z]|_)\\w*")) {
                postfix.enqueue(new Token(token));
            } else
            /**
             * Numeric literals:
             * Always get pushed to the postfix expression
             */
            if (token.matches("\\d+")) {
                postfix.enqueue(new Token(Double.valueOf(token)));
            }
            else if (token.equals("=")) {
                if (opStack.isEmpty()) {
                    opStack.push("=");
                } else {
                    String e = expression.substring(0, expression.indexOf("="));
                    throw new SyntaxError("Cannot assign to the expression " + e);
                }
            }
            else if (token.matches("\\+|-")) {
                while (!opStack.isEmpty()) {
                    if (opStack.top().matches("\\*|/"))
                        postfix.enqueue(new Token(opStack.pop()));
                    else break;
                }
                opStack.push(token);
            }
            else if (token.matches("\\*|/")) {
                while (!opStack.isEmpty()) {
                    if (opStack.top().matches("\\*|/"))
                        postfix.enqueue(new Token(opStack.pop()));
                    else break;
                }
                opStack.push(token);
            }
            else if (token.equals("(")) {
                opStack.push(token);
            }
            else if (token.equals(")")) {
                if (opStack.isEmpty() || opStack.top().equals("=")) {
                    throw new SyntaxError("Unmatched ')'");
                }
                /**
                 * Unstack until "(" is found
                 */
                while (!opStack.isEmpty() && opStack.top() != "(") {
                    postfix.enqueue(new Token(opStack.pop()));
                }
                if (!opStack.pop().equals("(")) {
                    throw new SyntaxError("Unmatched ')'");
                }
            }
            /**
             * Anything else, excluding whitespace
             */
            else if (!token.matches("[ \t\n\r\f]")) {
                /**
                 * Unrecognized symbol (did not match any recognized pattern)
                 * An exception will be thrown.
                 */
                throw new UnrecognizedSymbolError(token + " is not a valid token");
            }
        }
        /**
         * If there are any operators left on the operator stack,
         * enqueue them onto postfix.
         */
        while (!opStack.isEmpty()) {
            postfix.enqueue(new Token(opStack.pop()));
        }
        /**
         * Evaluate postfix expression
         */
        Stack<Token> eval = new Stack<>();
        return 0;
    }

    private boolean isOperator(String token) {
        return token.matches("[\\+\\*-/=]");
    }

    private class Token {
        private double _value;
        private String _name;

        public Token(String name) {
            _name = name;
        }
        public Token(double value) {
            _value = value;
        }

        public String name() {
            return _name;
        }
        public double value() {
            if (_name == null) {
                return _value;
            } else {
                return symbols.get(_name);
            }
        }
    }

    Operator getOperator(String token) {
        switch (token) {
            case "+":
                return (op1, op2) -> op1 + op2;
            case "-":
                return (op1, op2) -> op1 - op2;
            case "*":
                return (op1, op2) -> op1 * op2;
            case "/":
                return (op1, op2) -> op1 = op2;
            case "=":
                return (op1, op2) -> 0; //TODO
        }
        return null;
    }

    private interface Operator {
        public double call(double op1, double op2);
    }
}
