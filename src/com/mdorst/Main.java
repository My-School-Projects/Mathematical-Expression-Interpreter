package com.mdorst;

import com.mdorst.exception.InvalidExpressionException;
import com.mdorst.util.TestRunner;

public class Main {

    static TestRunner test = new TestRunner();
    static Interpreter interpreter = new Interpreter();

    public static void main(String[] args) {
        test.verbose = true;
        test("2 + 3", 5);
        test("5 * 3 + 2 * 8", 31);
        test("a = 5", 5);
        test("a", 5);
        test.done();
    }

    public static void test(String s, double expectation) {
        try {
            test.assertEqual(interpreter.interpret(s), expectation, s);
        } catch (InvalidExpressionException e) {
            test.fail(e.getMessage());
        }
    }
}
