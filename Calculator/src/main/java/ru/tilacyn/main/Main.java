package ru.tilacyn.main;

import ru.tilacyn.calculator.Calculator;

import java.util.Scanner;

/**
 * our console application
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String source = in.nextLine();
        Calculator calculator = new Calculator("(" + source + ")");
        System.out.println(calculator.evaluate());
    }
}
