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
    public void just() {
        Maybe<Integer> maybe = new Maybe(null);
        Maybe<Integer> maybe1 = maybe.just(4);
        Maybe<Integer> maybe2 = maybe.just(6);
        try {
            assert maybe1.get() == 4;
            assert maybe2.get() == 6;
        } catch(Exception e) {
            assert false;
        }
    }

    @Test
    public void nothing() {
        Maybe<Integer> maybe = new Maybe(null);
        Maybe<Integer> maybe1 = maybe.nothing();
        Maybe<Integer> maybe2 = maybe.nothing();
        try {
            maybe1.get();
            assert false;
        } catch(Exception e) {

        }

        try {
            maybe2.get();
            assert false;
        } catch(Exception e) {

        }

    }

    @Test
    public void get() {
        Maybe<Integer> maybeInt = new Maybe<Integer>(1);
        Maybe<Cmp> maybeCmp = new Maybe<Cmp>(new Cmp(1));
        Maybe<String> maybeString = new Maybe<String>("lol");
        Maybe<Character> maybeChar = new Maybe<Character>('c');

        try{
            assertEquals(maybeInt.get(), new Integer(1));
            assertEquals(maybeCmp.get().k, 1);
            assertEquals(maybeChar.get(), new Character('c'));
            assertEquals(maybeString.get(), "lol");
        } catch(Exception e) {
            assert false;
        }

        maybeInt = new Maybe(null);
        maybeCmp = new Maybe(null);
        maybeString = new Maybe(null);
        maybeChar = new Maybe(null);

        try {
            maybeInt.get();
            assert false;
        } catch(Exception e) {
        }

        try {
            maybeCmp.get();
            assert false;
        } catch(Exception e) {
        }

        try {
            maybeString.get();
            assert false;
        } catch(Exception e) {
        }

        try {
            maybeChar.get();
            assert false;
        } catch(Exception e) {
        }


    }

    @Test
    public void isPresent() throws Exception {
        Maybe<Integer> maybeInt = new Maybe<Integer>(1);
        Maybe<Cmp> maybeCmp = new Maybe<Cmp>(new Cmp(1));
        Maybe<String> maybeString = new Maybe<String>("lol");
        Maybe<Character> maybeChar = new Maybe<Character>('c');

        assertTrue(maybeInt.isPresent());
        assertTrue(maybeCmp.isPresent());
        assertTrue(maybeString.isPresent());
        assertTrue(maybeChar.isPresent());

        maybeInt = new Maybe(null);
        maybeCmp = new Maybe(null);
        maybeString = new Maybe(null);
        maybeChar = new Maybe(null);

        assertFalse(maybeInt.isPresent());
        assertFalse(maybeCmp.isPresent());
        assertFalse(maybeString.isPresent());
        assertFalse(maybeChar.isPresent());

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

    @Test
    public void read() throws Exception {
        File fin = new File("in");
        File fout = new File("fout");
        Maybe.read(new FileInputStream(fin), new FileOutputStream(fout));

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fout)));

        ArrayList<Maybe<Integer>> arr = new ArrayList<Maybe<Integer>>();
        String s;
        while((s = reader.readLine()) != null) {
            try {
                int n = Integer.parseInt(s);
                arr.add(new Maybe<Integer>(n).map(x -> x * x));
            } catch(Exception e) {
                arr.add(Maybe.nothing());
            }
        }
    }

}