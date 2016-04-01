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
        while (!postfix.isEmpty()) {
            /**
             * If a token is not an operator, then it is either a variable or a numeric literal.
             * Variables and numeric literals are automatically pushed to eval stack.
             */
            if (!postfix.front().isOperator()) {
                eval.push(postfix.dequeue());
                /**
                 * If a variable was pushed, look up its value and convert it to
                 * a value token UNLESS it is the only token on the eval stack, (which means it is the
                 * variable being assigned to, and needs to remain a variable).
                 */
                if (eval.size() > 1 && eval.top().isVariable()) {
                    Double value = symbols.get(eval.pop().name());
                    /**
                     * If value is null, the variable was not found in the symbol table
                     */
                    if (value == null) {
                        throw new UnrecognizedSymbolError("Error: Variables cannot be used before they are assigned");
                    }
                    eval.push(new Token(value));
                }
            }
            else {
                /**
                 * When an operator is reached, two values are popped from the eval stack.
                 * The operator is applied to them, and the result is pushed onto the eval stack.
                 *
                 * If there aren't at least two operands to operate on, there's an error.
                 */
                if (eval.size() < 2) {
                    throw new SyntaxError("Expected two operands for operator " + postfix.dequeue().name());
                }
                Operator operator = getOperator(postfix.dequeue().name());
                Token operandR = eval.pop();
                Token operandL = eval.pop();
                double result = operator.call(operandL, operandR);
                eval.push(new Token(result));
            }
        }
        if (eval.top().isVariable()) {
            return symbols.get(eval.pop().name());
        } else {
            return eval.pop().value();
        }
    }

    /**
     * Returns a function which, when called on two operands, applies the appropriate operation to them
     * @param token The string representation of the operator, eg. "+" or "*"
     * @return a function which implements the appropriate operation
     */
    Operator getOperator(String token) {
        switch (token) {
            case "+":
                return (op1, op2) -> op1.value() + op2.value();
            case "-":
                return (op1, op2) -> op1.value() - op2.value();
            case "*":
                return (op1, op2) -> op1.value() * op2.value();
            case "/":
                return (op1, op2) -> op1.value() / op2.value();
            case "=":
                return (op1, op2) -> {
                    symbols.add(op1.name(), op2.value());
                    return op2.value();
                };
        }
        return null;
    }

    private interface Operator {
        double call(Token op1, Token op2);
    }
}
