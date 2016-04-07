package com.mdorst;

import com.mdorst.exception.SyntaxError;

import java.util.Scanner;

public class Run {
    public static void main(String[] args) {
        System.out.println("Welcome to the REPL. Type \"Exit\" to quit.");
        Scanner scanner = new Scanner(System.in);
        Interpreter interpreter = new Interpreter();
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Exit"))
                break;
            try {
                double result = interpreter.interpret(input);
                System.out.println(result);
            } catch (SyntaxError e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
