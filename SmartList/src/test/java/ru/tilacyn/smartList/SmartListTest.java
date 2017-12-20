package ru.tilacyn.smartList;

import org.junit.Test;

import static org.junit.Assert.*;

public class SmartListTest {
    @Test
    public void set() throws Exception {
    }

    @Test
    public void remove() throws Exception {
        SmartList<Integer> smart = new SmartList<>();
        smart.add(1);
        smart.add(2);
        smart.add(3);
        smart.add(4);

        smart.remove(2);

        assertFalse(smart.contains(3));
        assertTrue(smart.contains(4));
    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void size() throws Exception {
        SmartList<Integer> smart = new SmartList<>();
        assertEquals(smart.size(), 0);
        smart.add(1);
        assertEquals(smart.size(), 1);
        smart.add(2);
        assertEquals(smart.size(), 2);
        smart.add(3);
        assertEquals(smart.size(), 3);
        smart.add(4);

        assertEquals(smart.size(), 4);
    }

    @Test
    public void contains() throws Exception {
        SmartList<Integer> smart = new SmartList<>();
        smart.add(1);
        smart.add(2);
        smart.add(3);
        assertTrue(smart.contains(3));
        smart.add(4);
        assertTrue(smart.contains(1));
        assertTrue(smart.contains(2));
        assertTrue(smart.contains(3));
        assertTrue(smart.contains(4));
        assertTrue(smart.contains(1));
        assertFalse(smart.contains(5));

    }

    @Test
    public void add() throws Exception {
        SmartList<Integer> smart = new SmartList<>();
        smart.add(1);
        smart.add(2);
        smart.add(3);
        assertTrue(smart.contains(3));
        smart.add(4);
        assertTrue(smart.contains(1));

        assertEquals(smart.get(0), (Integer) 1);

    }

    @Test
    public void add1() throws Exception {
    }

}