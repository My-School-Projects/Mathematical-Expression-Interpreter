package com.mdorst;

import com.mdorst.container.list.LinkedList;
import java.util.Collection;
import java.util.StringTokenizer;

public class Interpreter {
    /**
     * Takes an expression, stores it, then interprets it, and returns the result.
     * @param expression The expression to be interpreted
     * @return The result of the expression
     */
    public int interpret(String expression) {
        Collection<String> expr = new LinkedList<>();
        /**
         * Tokenize the expression
         */
        {
            StringTokenizer tokenizer = new StringTokenizer(expression);
            while (tokenizer.hasMoreTokens()) {
                expr.add(tokenizer.nextToken());
            }
        }
        
        return 0;
    }
}
