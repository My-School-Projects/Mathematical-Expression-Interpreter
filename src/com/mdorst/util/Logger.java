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
        stream.println();
        for (String info : log) {
            stream.println(info);
        }
    }

    public Logger() {
        log = new LinkedList<>();
    }
}
