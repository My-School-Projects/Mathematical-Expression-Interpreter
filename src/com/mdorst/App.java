package com.mdorst;

import com.mdorst.util.Logger;

/**
 * Michael Dorst
 */
public class App {
    private Logger logger;

    public void main() {
        Interpreter interpreter = new Interpreter();
        interpreter.interpret("1 + 2");
        expect(interpreter, 3);

        logger.print(System.out);
    }

    private void expect(Interpreter interpreter, int value) {
        if (interpreter.result() == value) {
            System.out.print(".");
        } else {
            System.out.print("F");
            logger.log("Error: expected " + interpreter.expression() + " = " + value);
        }
    }

    public App() {
        logger = new Logger();
    }
}
