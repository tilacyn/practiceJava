package ru.tilacyn.function;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;
import java.io.*;
import java.lang.String;


public class Function2Test {

    private ArrayList<Integer> vector = new ArrayList<Integer>();
    private Function1Test.IntToArray intToArray = new Function1Test.IntToArray();
    private Function1<Integer, Integer> squareInt = x -> x * x;
    private Function1<Double, Double> polynom = x -> x + 2 * x * x + 3 * x * x * x;
    private Function1Test.CounterA counterA = new Function1Test.CounterA();
    private Function2<Integer, Integer, Integer> sumSquare = (x, y) -> x * x + y * y;


    @Test
    public void apply() throws Exception {

        assertEquals(8, (int) sumSquare.apply(2, 2));
        assertEquals(41, (int) sumSquare.apply(-4, 5));
        assertEquals(961, (int) sumSquare.apply(0, 31));

    }

    @Test
    public void compose() throws Exception {
        Function2<Integer, Integer, ArrayList<Integer>> sumSquareToArray = sumSquare.compose(intToArray);

        vector.clear();
        vector.add(4);
        vector.add(0);
        vector.add(9);
        assertEquals(vector, sumSquareToArray.apply(3, 20));

        Function2<Double, Double, Double> minus = ((x, y) -> x - y);
        Function1<Double, Double> squareDouble = (x -> x * x);
        Function2<Double, Double, Double> minusSquare = minus.compose(squareDouble);

        assertEquals(6.25, (double) minusSquare.apply(4.5, 2.0), 0.01);
        assertEquals(1, (double) minusSquare.apply(3.0, 4.0), 0.1);

    }

    @Test
    public void bind2() {
        Function1<Integer, Integer> newSquare = sumSquare.bind2(0);
        for (int i = -50; i < 50; i++) {
            assertEquals(newSquare.apply(i), squareInt.apply(i));
        }

        Function2<Double, Double, Double> minus = ((x, y) -> x - y);
        for (double i = -50; i < 50; i++) {
            assertEquals(i, (double) minus.bind2(0.0).apply((double) i), 0.1);
        }
    }

    @Test
    public void bind1() {
        Function1<Integer, Integer> newSquare = sumSquare.bind1(0);
        for (int i = -50; i < 50; i++) {
            assertEquals(newSquare.apply(i), squareInt.apply(i));
        }


        Function2<Double, Double, Double> minus = ((x, y) -> x - y);
        for (double i = -50; i < 50; i++) {
            assertEquals(-i, (double) minus.bind1(0.0).apply(i), 0.1);
        }
    }

    @Test
    public void curry() {
        Function1<Integer, Integer> newSquare = sumSquare.curry(0);
        for (int i = -50; i < 50; i++) {
            assertEquals(newSquare.apply(i), squareInt.apply(i));
        }

        Function2<Double, Double, Double> minus = ((x, y) -> x - y);
        for (double i = -50; i < 50; i++) {
            assertEquals(i, (double) minus.curry(0.0).apply((double) i), 0.1);
        }
    }

}