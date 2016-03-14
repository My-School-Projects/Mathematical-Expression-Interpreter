package com.mdorst;

import com.mdorst.util.TestRunner;

public class Main {

    public static void main(String[] args) {
        TestRunner test = new TestRunner();
        Interpreter interpreter = new Interpreter();
        interpreter.interpret("1 + 2");
        test.assertEqual(interpreter.interpret("1 + 2"), 3, "interpret()");
        test.done();
    }
}
