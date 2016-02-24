package com.mdorst;

import com.mdorst.util.TestLogger;

/**
 * Michael Dorst
 */
public class App {
    private TestLogger tests;

    public void main() {
        Interpreter interpreter = new Interpreter();
        interpreter.interpret("1 + 2");
        tests.expect(interpreter, 3);
        tests.finalize(System.out);
    }

    public App() {
        tests = new TestLogger();
    }
}
