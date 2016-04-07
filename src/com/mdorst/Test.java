package com.mdorst;

import com.mdorst.exception.SyntaxError;
import com.mdorst.exception.UnrecognizedSymbolError;
import com.mdorst.util.TestRunner;

public class Test {

    static TestRunner test = new TestRunner();
    static Interpreter interpreter = new Interpreter();

    public static void main(String[] args) {
        test.verbose = true;
        test("2 + 30", 32);
        test("5*3+2*8", 31);
        test("a = 5", 5);
        test("a", 5);
        test("b=a * 3", 15);
        test("c = b - a + 11", 21);
        test("d = sin(0)", 0);
        test("cos(0)", 1);
        test("var = 5 * (2 + 6)", 40);
        test("var", 40);
        test("(2 + 6) * abs(-5)", 40);
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
            test.fail(s + " threw " + e.getClass().toString().substring(6) + "\n" + e.getMessage());
        } catch (Throwable e) {
            test.fail(s + " threw " + e.getClass().toString().substring(6));
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
