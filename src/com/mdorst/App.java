package com.mdorst;

import com.mdorst.util.TestLogger;

/**
 * Michael Dorst
 */
public class App {
    private TestLogger testLogger;

    public void main() {
        Interpreter interpreter = new Interpreter();
        interpreter.interpret("1 + 2");
        expect(interpreter, 3);

        testLogger.print(System.out);
    }

    /**
     * Inspects +interpreter+, and asserts that it is equal to +value+.
     * Should be used to list "expectations" about what the result should be.
     * @param interpreter
     * @param value
     */
    private void expect(Interpreter interpreter, int value) {
        if (interpreter.result() == value) {
            System.out.print(".");
        } else {
            System.out.print("F");
            testLogger.log("Error: expected " + interpreter.expression() + " = " + value);
        }
    }

    public App() {
        testLogger = new TestLogger();
    }
}
