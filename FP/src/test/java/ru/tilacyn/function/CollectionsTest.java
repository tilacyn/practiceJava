package ru.tilacyn.function;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

public class CollectionsTest {
    @Test
    public void map() {
        Collections collection = new Collections();
        ArrayList<Integer> vector = new ArrayList<Integer>();
        ArrayList<Integer> source = new ArrayList<Integer>();
        source.add(1);
        source.add(2);
        source.add(3);

        vector.add(1);
        vector.add(4);
        vector.add(9);
        assertEquals(vector, collection.map(x -> x * x, source));

        ArrayList<String> sourceString = new ArrayList<String>();
        sourceString.add("a");
        sourceString.add("aba");
        sourceString.add("ababa");

        vector.clear();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        assertEquals(vector, collection.map(new Function1Test.CounterA(), sourceString));
    }

    @Test
    public void filter() throws Exception {
        Predicate<String> isShorter5 = s -> s.length() < 5;
        Predicate<Integer> is2 = x -> x == 2;


        Collections collection = new Collections();
        ArrayList<Integer> vector = new ArrayList<Integer>();
        ArrayList<Integer> source = new ArrayList<Integer>();
        source.add(1);
        source.add(2);
        source.add(3);

        vector.clear();
        vector.add(2);
        assertEquals(vector, collection.filter(is2, source));

        ArrayList<String> sourceString = new ArrayList<String>();
        sourceString.add("a");
        sourceString.add("aba");
        sourceString.add("ababa");

        ArrayList<String> vectorString = new ArrayList<String>();

        vectorString.clear();
        vectorString.add("a");
        vectorString.add("aba");
        assertEquals(vectorString, collection.filter(isShorter5, sourceString));
    }

    @Test
    public void takeWhile() throws Exception {
        Predicate<String> isShorter5 = s -> s.length() < 5;
        Predicate<Integer> is2 = x -> x == 2;


        Collections collection = new Collections();
        ArrayList<Integer> vector = new ArrayList<Integer>();
        ArrayList<Integer> source = new ArrayList<Integer>();
        source.add(1);
        source.add(2);
        source.add(3);

        vector.clear();
        assertEquals(vector, collection.takeWhile(is2, source));

        ArrayList<String> sourceString = new ArrayList<String>();
        sourceString.add("a");
        sourceString.add("aba");
        sourceString.add("ababa");

        ArrayList<String> vectorString = new ArrayList<String>();

        vectorString.clear();
        vectorString.add("a");
        vectorString.add("aba");
        assertEquals(vectorString, collection.takeWhile(isShorter5, sourceString));

    }

    @Test
    public void takeUnless() throws Exception {
        Predicate<String> isShorter5 = s -> s.length() < 5;
        Predicate<Integer> is2 = x -> x == 2;


        Collections collection = new Collections();
        ArrayList<Integer> vector = new ArrayList<Integer>();
        ArrayList<Integer> source = new ArrayList<Integer>();
        source.add(1);
        source.add(2);
        source.add(3);

        vector.clear();
        vector.add(1);

        assertEquals(vector, collection.takeUnless(is2, source));

        ArrayList<String> sourceString = new ArrayList<String>();
        sourceString.add("a");
        sourceString.add("aba");
        sourceString.add("ababa");

        ArrayList<String> vectorString = new ArrayList<String>();

        vectorString.clear();
        assertEquals(vectorString, collection.takeUnless(isShorter5, sourceString));

    }

    @Test
    public void foldl() throws Exception {
        Collections collection = new Collections();
        ArrayList<Integer> source = new ArrayList<Integer>();
        Function2<Integer, Integer, Integer> plus = (x, y) -> x + y;

        source.add(1);
        source.add(2);
        source.add(3);
        assertEquals(6, (int) collection.foldl(plus, 0, source));
    }

    @Test
    public void foldr() throws Exception {
        Collections collection = new Collections();
        ArrayList<Integer> source = new ArrayList<Integer>();
        Function2<Integer, Integer, Integer> plus = (x, y) -> x + y;

        source.add(1);
        source.add(2);
        source.add(3);
        assertEquals(6, (int) collection.foldr(plus, 0, source));
    }

}