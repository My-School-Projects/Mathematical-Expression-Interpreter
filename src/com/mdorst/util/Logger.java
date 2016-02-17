package com.mdorst.util;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Michael Dorst
 */
public class Logger {
    private List<String> log;

    public void log(String info) {
        log.add(info);
    }

    public void print(PrintStream stream) {
        stream.println();
        if (log.isEmpty()) {
            stream.println("Passed!");
        } else {
            stream.println("Failed.");
        }
        log.forEach(stream::println);
    }

    public Logger() {
        log = new LinkedList<>();
    }
}
