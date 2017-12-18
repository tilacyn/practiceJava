package ru.tilacyn.maybe;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MaybeTest {

    private class Cmp implements Comparable<Cmp> {

        public int k;

        Cmp(int k) {
            this.k = k;
        }

        public int compareTo(Cmp other) {
            if (k < other.k) {
                return 1;
            } else if (k == other.k) {
                return 0;
            }
            return -1;
        }
    }

    @Test
    public void just() throws Maybe.EmptyMaybeException {
        Maybe<Integer> maybe1 = Maybe.just(4);
        Maybe<Integer> maybe2 = Maybe.just(6);
        assertEquals(maybe1.get(), (Integer) 4);
        assertEquals(maybe2.get(), (Integer) 6);
    }


    @Test(expected = Maybe.EmptyMaybeException.class)
    public void nothing1() throws Maybe.EmptyMaybeException {
        Maybe.nothing().get();
    }


    @Test(expected = Maybe.EmptyMaybeException.class)
    public void get() throws Maybe.EmptyMaybeException {
        assertEquals(Maybe.just(1).get(), new Integer(1));
        assertEquals(Maybe.just(new Cmp(1)).get().k, 1);
        assertEquals(Maybe.just('c').get(), new Character('c'));
        assertEquals(Maybe.just("lol").get(), "lol");

        Maybe.nothing().get();
    }


    @Test
    public void isPresent() throws Exception {

        assertTrue(Maybe.just(1).isPresent());
        assertTrue(Maybe.just(new Cmp(1)).isPresent());
        assertTrue(Maybe.just("lol").isPresent());
        assertTrue(Maybe.just('c').isPresent());

        assertFalse(Maybe.just(null).isPresent());
    }

    @Test
    public void map() throws Exception {
        Maybe<Integer> maybe;
        maybe = Maybe.just(5);
        assertEquals(maybe.map(x -> Math.abs(x)).get(), (Integer) 5);
        maybe = Maybe.just(-5);
        assertEquals(maybe.map(x -> Math.abs(x)).get(), (Integer) 5);

        maybe = Maybe.just(4);

        assertEquals(maybe.map(x -> Math.sqrt(x)).get(), (Double) 2.0);
    }

    @Test(expected = Maybe.EmptyMaybeException.class)
    public void read() throws Exception {
        File fin = new File("in");
        File fout = new File("fout");

        try (FileInputStream fis = new FileInputStream(fin);
             FileOutputStream fos = new FileOutputStream(fout)) {
            Maybe.read(fis, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }


        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fout)));

        ArrayList<Maybe<Integer>> array = new ArrayList<Maybe<Integer>>();
        String s;
        while ((s = reader.readLine()) != null) {
            try {
                int n = Integer.parseInt(s);
                array.add(Maybe.just(n).map(x -> x * x));
            } catch (Exception e) {
                array.add(Maybe.nothing());
            }
        }
        assertEquals(array.get(0).get(), (Integer) 1);
        array.get(1).get();
        assertEquals(array.get(2).get(), (Integer) 2);
        assertEquals(array.get(3).get(), (Integer) 3);

    }
}

