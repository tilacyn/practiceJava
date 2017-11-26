package ru.tilacyn.set;

import java.util.Random.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class SetTest {

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
    public void add() {

        Set<Integer> setInt = new Set<Integer>();

        assert setInt.add(1);
        assert setInt.add(2);
        assert setInt.add(3);
        assert setInt.add(4);
        assert setInt.add(5);
        assertFalse(setInt.add(5));
        assert setInt.contains(1);
        assert setInt.contains(2);
        assert setInt.contains(3);
        assert setInt.contains(4);
        assert setInt.contains(5);
        assertFalse(setInt.contains(6));

        Set<String> setStr = new Set<String>();

        assert setStr.add("1");
        assert setStr.add("lol");
        assert setStr.add("kek");
        assert setStr.add("wow");
        assert setStr.add("omg");
        assert setStr.add("superkek");
        assert setStr.add("rofl");

        assert setStr.contains("1");
        assert setStr.contains("lol");
        assert setStr.contains("kek");
        assert setStr.contains("wow");
        assert setStr.contains("omg");
        assert setStr.contains("rofl");

        assertFalse(setStr.add("1"));
        assertFalse(setStr.add("lol"));
        assertFalse(setStr.add("kek"));
        assertFalse(setStr.add("omg"));
        assertFalse(setStr.add("1"));

        assert setStr.contains("1");
        assert setStr.contains("lol");
        assert setStr.contains("kek");
        assert setStr.contains("wow");
        assert setStr.contains("omg");
        assert setStr.contains("rofl");

        Set<Cmp> setCmp = new Set<Cmp>();


        assert setCmp.add(new Cmp(1));
        assert setCmp.add(new Cmp(109));
        assertFalse(setCmp.add(new Cmp(1)));
        assert setCmp.add(new Cmp(11));
        assert setCmp.add(new Cmp(223));

        assert setCmp.contains(new Cmp(1));
        assert setCmp.contains(new Cmp(109));
        assert setCmp.contains(new Cmp(11));
        assert setCmp.contains(new Cmp(223));

    }

    @Test
    public void contains() {
        Set<Integer> setInt = new Set<Integer>();

        setInt.add(-1);
        setInt.add(-10);
        setInt.add(-100);
        setInt.add(-1000);

        for(int i = -1000; i < 1000; i++){
            if(i == -1 || i == -10 || i == -100 || i == -1000) {
                assert setInt.contains(i);
            } else {
                assertFalse(setInt.contains(i));
            }
        }

    }

    @Test
    public void size() {
        Set<Integer> setInt = new Set<Integer>();

        setInt.add(0);

        for(int i = 1; i < 100; i++) {
            setInt.add(i);
            assertEquals(setInt.size(), i + 1);
            int k = Math.abs(new java.util.Random().nextInt()) % i;
            setInt.add(k);
            assertEquals(setInt.size(), i + 1);
        }

        Set<Cmp> setCmp = new Set<Cmp>();

        setCmp.add(new Cmp(0));

        for(int i = 1; i < 100; i++) {
            setCmp.add(new Cmp(i));
            assertEquals(setCmp.size(), i + 1);
            int k = Math.abs(new java.util.Random().nextInt()) % i;
            setCmp.add(new Cmp(i));
            assertEquals(setCmp.size(), i + 1);
        }

    }

}