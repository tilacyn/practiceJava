package ru.tilacyn.list;

import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {
    @Test
    public void size() throws Exception {
        List l = new List();
        assertEquals(0, l.size());
        l.add("a", "b");
        assertEquals(1, l.size());
        l.add("a", "b");
        assertEquals(2, l.size());
        l.add("a", "b");
        assertEquals(3, l.size());
        l.add("a", "b");
        assertEquals(4, l.size());
        l.add("a", "b");
        assertEquals(5, l.size());
        l.remove("b");
        assertEquals(5, l.size());
        l.remove("a");
        assertEquals(4, l.size());
        l.add("s", "t");
        assertEquals(5, l.size());

    }

    @Test
    public void isEmpty() throws Exception {
        List l = new List();
        assertTrue(l.isEmpty());
        l.add(" ", " ");
        assertFalse(l.isEmpty());
        l.remove(" ");
        assertTrue(l.isEmpty());
        l.add("hello", "bye");
        assertFalse(l.isEmpty());
        l.remove(" ");
        assertFalse(l.isEmpty());
        l.remove("hello");
        assertTrue(l.isEmpty());
    }

    @Test
    public void getHeadKey() throws Exception {
        List l = new List();
        assertEquals(null, l.getHeadKey());
        l.add("a", " ");
        assertEquals("a", l.getHeadKey());
        l.add("b", " ");
        assertEquals("b", l.getHeadKey());
        l.add("a", " ");
        assertEquals("a", l.getHeadKey());
        l.add(" ", " ");
        assertEquals(" ", l.getHeadKey());
        l.add("Lamerica", " ");
        assertEquals("Lamerica", l.getHeadKey());
        l.remove("Lamerica");
        assertEquals(" ", l.getHeadKey());
    }

    @Test
    public void add() {
        List l = new List();
        l.add("1", "2");
        l.add("2", "!");
        assertTrue(l.contains("1"));
        assertTrue(l.contains("2"));
        assertFalse(l.contains("!"));
        assertFalse(l.contains("3"));


        l.add("1", "2");
        l.add("2", "!");
        assertTrue(l.contains("1"));
        assertTrue(l.contains("2"));
        assertFalse(l.contains("!"));
        assertFalse(l.contains("3"));

        l.add("-1", "-1");
        l.add("-1", "-1");
        l.add("100", "-1");

        assertTrue(l.contains("100"));
        assertTrue(l.contains("-1"));
        assertFalse(l.contains("3"));
        assertFalse(l.contains("0"));
        assertTrue(l.contains("1"));
        assertTrue(l.contains("2"));
    }

    @Test
    public void remove() {
        List l = new List();
        l.add("1", "2");
        l.remove("1");
        l.add("3", "4");
        l.add("5", "6");
        l.remove("3");

    }

    @Test
    public void contains() {
        List l = new List();
        l.add("1", "2");
        assertTrue(l.contains("1"));
        l.add("3", "4");
        assertTrue(l.contains("3"));
        l.remove("1");
        assertFalse(l.contains("1"));
    }

    @Test
    public void get() {
        List l = new List();
        l.add("1", "2");
        assertEquals(l.get("1"), "2");
        l.add("3", "4");
        assertEquals(l.get("3"), "4");
        l.remove("1");
        assertEquals(l.get("1"), null);
    }
}