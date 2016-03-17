package com.mdorst;

import com.mdorst.exception.SyntaxError;
import com.mdorst.exception.UnrecognizedSymbolError;
import com.mdorst.util.TestRunner;

public class Main {

    static TestRunner test = new TestRunner();
    static Interpreter interpreter = new Interpreter();

    public static void main(String[] args) {
        test.verbose = true;
        test("2 + 30", 32);
        test("5*3+2*8", 31);
        test("a = 5", 5);
        test("a", 5);
        test("b=a * 3", 15);
        shouldThrow("+", SyntaxError.class);
        shouldThrow("2 * 3 *", SyntaxError.class);
        shouldThrow("= 2", SyntaxError.class);
        shouldThrow("= = 2", SyntaxError.class);
        shouldThrow("== 2", SyntaxError.class);
        shouldThrow("2 * 3#", UnrecognizedSymbolError.class);
        test.done();
    }

    public static void test(String s, double expectation) {
        try {
            test.assertEqual(interpreter.interpret(s), expectation, s);
        } catch (SyntaxError e) {
            test.fail(e.getMessage());
        }
    }

    public static void shouldThrow(String s, Class<? extends Throwable> exceptionType) {
        try {
            interpreter.interpret(s);
        } catch (Throwable e) {
            if (exceptionType.isInstance(e)) {
                test.pass(s + " throws " + exceptionType.getName());
                return;
            }
        }
        test.fail(s + " does not throw " + exceptionType.getName());
    }
}
