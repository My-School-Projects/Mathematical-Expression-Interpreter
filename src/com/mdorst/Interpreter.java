package com.mdorst;

import com.mdorst.container.hash.HashMap;
import com.mdorst.container.list.LinkedList;
import com.mdorst.container.list.Queue;
import com.mdorst.container.list.Stack;
import com.mdorst.exception.InvalidExpressionException;
import com.mdorst.exception.UnrecognizedSymbolError;

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
        public Token call(Token op1, Token op2) {
            return operators.get(name).call(op1, op2);
        }
        public boolean isOperator() {
            return name != null && operators.get(name) != null;
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
     * Standard interface for all operations (+, -, *, etc.)
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
    private static HashMap<String, Operator> operators;
    /**
     * Maps variable names to values.
     */
    private HashMap<String, Double> symbols = new HashMap<>(String::hashCode);

    public Interpreter() {
        /**
         * Set up {@code operators} to be accessed via string identifiers.
         * Do this only once, upon the first call to this constructor.
         */
        if (operators == null) {
            operators = new HashMap<>(String::hashCode);
            operators.add("+", new Operator(3, (op1, op2) -> new Token(op1.value + op2.value)));
            operators.add("-", new Operator(3, (op1, op2) -> new Token(op1.value - op2.value)));
            operators.add("*", new Operator(2, (op1, op2) -> new Token(op1.value * op2.value)));
            operators.add("/", new Operator(2, (op1, op2) -> new Token(op1.value / op2.value)));
            operators.add("=", new Operator(0, (Token op1, Token op2) -> {
                symbols.add(op1.name, op2.value);
                return new Token(op2.value);
            }));
        }
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
            if (token.name.matches("([a-z]|[A-Z]|_)\\w*")) {
                postfix.enqueue(token);
            } else
            /**
             * Numeric literals
             */
            if (token.name.matches("\\d+")) {
                postfix.enqueue(new Token(Double.valueOf(token.name)));
            } else
            /**
             * Operators
             */
            if (token.name.matches("[\\+\\-*/=]")) {
                while (!opStack.isEmpty() &&
                        operators.get(opStack.top().name).priority < operators.get(token.name).priority) {
                    postfix.enqueue(opStack.pop());
                }
                opStack.push(token);
            } else {
                /**
                 * Unrecognized symbol (did not match any recognized pattern)
                 * An exception will be thrown.
                 */
                throw new UnrecognizedSymbolError(token.name + " is not a valid token");
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
            /**
             * While there are operands in the postfix queue,
             * push them to the eval stack
             */
            while (!postfix.isEmpty() && !postfix.front().isOperator()) {
                eval.push(postfix.dequeue());
            }
            /**
             * If the postfix queue is not empty and there
             * are less than two operands in the eval stack
             * at this point, then the expression is not valid.
             * An exception will be thrown.
             */
            if (!postfix.isEmpty() && eval.size() < 2) {
                throw new InvalidExpressionException(expression + " is not a valid expression");
            }
            /**
             * While there is more than one operand on the eval stack,
             * pop the top two and operate on them using the next
             * operator in the postfix queue.
             */
            while (eval.size() > 1) {
                Token rightOperand = eval.pop();
                Token leftOperand = eval.pop();
                Token operator = postfix.dequeue();
                eval.push(operator.call(leftOperand, rightOperand));
            }
        }
        return eval.pop().value();
    }
}
