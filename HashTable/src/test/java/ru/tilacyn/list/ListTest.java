package ru.tilacyn.list;

import org.junit.Assert.*;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;



public class ListTest{

    @Test
    public void makeList() {
        List l = new List();
        assertEquals(l.head, null);
    }

    @Test
    public void add() {
        List l = new List();
        l.add("1", "2");
        assertEquals(l.head.key, "1");
        assertEquals(l.head.s, "2");
        assertEquals(l.head.prev, null);
        assertEquals(l.head.next, null);
        assertEquals(l.head.next, null);
        l.add("3", "4");
        assertEquals(l.head.prev, null);
        assertEquals(l.head.s, "4");
        assertEquals(l.head.key, "3");
        assertEquals(l.head.next.key, "1");
    }

    @Test
    public void remove() {
        List l = new List();
        l.add("1", "2");
        l.remove("1");
        assertEquals(l.head, null);
        l.add("3", "4");
        l.add("5", "6");
        l.remove("3");
        assertEquals(l.head.key, "5");
    }

    @Test
    public void contains() {
        List l = new List();
        l.add("1", "2");
        assert l.contains("1");
        l.add("3", "4");
        assert l.contains("3");
        l.remove("1");
        assert !(l.contains("1"));
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