package com.mdorst.util;

import com.mdorst.Interpreter;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Michael Dorst
 */

/**
 * TestLogger is responsible for keeping track of error messages
 */
public class TestLogger {
    private List<String> log;

    /**
     * Inspects +interpreter+, and asserts that it is equal to +value+.
     * Should be used to list "expectations" about what the result should be.
     * @param interpreter The interpreter to be tested
     * @param value The value to be checked against interpreter.result()
     */
    public void expect(Interpreter interpreter, int value) {
        if (interpreter.result() == value) {
            System.out.print(".");
        } else {
            System.out.print("F");
            log.add("Error: expected " + interpreter.expression() + " = " + value);
        }
    }

    /**
     * Called when all tests are done. Logs error messages to +stream+
     * and logs "Passed!" or "Failed." depending on weather there are
     * errors or not.
     * @param stream The stream (eg. System.out) to be logged to
     */
    public void finalize(PrintStream stream) {
        stream.println();
        if (log.isEmpty()) {
            stream.println("Passed!");
        } else {
            stream.println("Failed.");
        }
        log.forEach(stream::println);
    }

    public TestLogger() {
        log = new LinkedList<>();
    }
}
