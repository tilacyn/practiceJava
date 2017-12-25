package ru.tilacyn.main;

import ru.tilacyn.calculator.Calculator;
import ru.tilacyn.stack.Stack;

import java.util.Scanner;

/**
 * our console application
 */
public class Main {
    /**
     * @throws Stack.EmptyStackException if pop is applied to an empty Stack
     *                                   this exception might be thrown when input is incorrect
     */
    public static void main(String[] args) throws Stack.EmptyStackException {
        Scanner in = new Scanner(System.in);
        String source = in.nextLine();
        Calculator calculator = new Calculator("(" + source + ")");
        System.out.println(calculator.evaluate());
    }
}
