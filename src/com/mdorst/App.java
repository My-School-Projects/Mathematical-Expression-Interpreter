package com.mdorst;

import com.mdorst.util.TestRunner;

public class App {
    private TestRunner tests;

    public void main() {
        Interpreter interpreter = new Interpreter();
        interpreter.interpret("1 + 2");
        tests.assertEqual(interpreter.result(), 3, "result");
        tests.done();
    }

    public App() {
        tests = new TestRunner();
    }
}
