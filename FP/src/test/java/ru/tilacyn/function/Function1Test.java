package ru.tilacyn.function;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;
import java.io.*;
import java.lang.String;

public class Function1Test {

    private ArrayList<Integer> vector = new ArrayList<Integer>();
    private IntToArray intToArray = new IntToArray();
    private Function1<Integer, Integer> squareInt = x -> x * x;
    private Function1<Double, Double> polynom = x -> x + 2 * x * x + 3 * x * x * x;
    private CounterA counterA = new CounterA();


    @Test
    public void apply() {

        assertEquals(new Integer(4), squareInt.apply(2));
        assertEquals(new Integer(9), squareInt.apply(3));
        assertEquals(new Integer(0), squareInt.apply(0));
        assertEquals(new Integer(144), squareInt.apply(-12));


        for (double i = -50; i < 50; i++) {
            assertEquals(new Double(i + 2 * i * i + 3 * i * i * i), polynom.apply(i));
            double j = i + 0.3;
            assertEquals(new Double(j + 2 * j * j + 3 * j * j * j), polynom.apply(j));
            j += 0.444;
            assertEquals(new Double(j + 2 * j * j + 3 * j * j * j), polynom.apply(j));
        }

        assertEquals(new Integer(4), counterA.apply("aaaa"));
        assertEquals(new Integer(0), counterA.apply("gregory"));
        assertEquals(new Integer(13), counterA.apply("alabamalabamalabamalabama"));


        vector.clear();
        vector.add(5);
        vector.add(6);
        vector.add(7);
        assertEquals(vector, intToArray.apply(567));

        vector.clear();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        vector.add(4);
        assertEquals(vector, intToArray.apply(1234));

        vector.clear();
        vector.add(0);
        assertEquals(vector, intToArray.apply(0));
    }

    @Test
    public void compose() {
        Function1<Integer, Integer> deg4 = squareInt.compose(squareInt);

        assertEquals(16, (int) deg4.apply(2));
        assertEquals(625, (int) deg4.apply(-5));
        assertEquals(0, (int) deg4.apply(0));

        Function1<Integer, ArrayList<Integer>> squareToArray = squareInt.compose(intToArray);

        vector.clear();
        vector.add(2);
        vector.add(5);
        assertEquals(vector, squareToArray.apply(5));

        vector.clear();
        vector.add(1);
        vector.add(0);
        vector.add(8);
        vector.add(9);
        assertEquals(vector, squareToArray.apply(-33));

        Function1<String, Integer> counterASquare = counterA.compose(squareInt);

        assertEquals(36, (int) counterASquare.apply("1a2a3a4a5a6a"));
        assertEquals(4, (int) counterASquare.apply("California Sun"));

    }

    static class CounterA implements Function1<String, Integer> {
        @Override
        public Integer apply(String s) {
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ('a')) {
                    count++;
                }
            }
            return count;
        }
    }

    static class IntToArray implements Function1<Integer, ArrayList<Integer>> {
        public ArrayList<Integer> apply(Integer n) {
            ArrayList<Integer> vector = new ArrayList<Integer>();
            do {
                int digit = n % 10;
                vector.add(0, digit);
                n /= 10;
            } while (n > 0);
            return vector;
        }
    }

}