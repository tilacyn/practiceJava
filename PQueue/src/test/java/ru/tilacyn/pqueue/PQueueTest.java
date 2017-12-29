package ru.tilacyn.pqueue;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class PQueueTest {
    @Test
    public void add() throws Exception {

        PQueue<Integer> pQueue = new PQueue<>();
        pQueue.add(1);
        pQueue.print();
        pQueue.add(4);
        pQueue.print();
        pQueue.add(2);
        pQueue.print();
        pQueue.add(3);
        pQueue.print();
        pQueue.add(5);
        pQueue.print();
        assertEquals((Integer) 1, pQueue.poll());
    }

    @Test
    public void addOne() throws Exception {
        PQueue<Integer> pQueue = new PQueue<>();
        for (int i = 1; i < 50; i++) {
            pQueue.add(i);
            pQueue.print();
            assertEquals((Integer) i, pQueue.poll());
        }

    }

    @Test
    public void poll() throws Exception {
        PQueue<Character> pQueue;
        pQueue = new PQueue<>();
        pQueue.add('a');
        pQueue.add('c');
        pQueue.add('d');
        pQueue.add('x');
        pQueue.add('e');
        pQueue.print();
        assertEquals(pQueue.poll(), (Character) 'a');
        pQueue.print();
        assertEquals(pQueue.poll(), (Character) 'c');
        pQueue.print();
        assertEquals(pQueue.poll(), (Character) 'd');
        pQueue.print();
        assertEquals(pQueue.poll(), (Character) 'e');
        pQueue.print();
        assertEquals(pQueue.poll(), (Character) 'x');

    }

    @Test
    public void size() throws Exception {
        PQueue<Integer> pQueue = new PQueue<>();
        assertEquals(pQueue.size(), 0);
        pQueue.add(1);
        assertEquals(pQueue.size(), 1);
        pQueue.add(100);
        assertEquals(pQueue.size(), 2);
        pQueue.add(51);
        assertEquals(pQueue.size(), 3);
        pQueue.poll();
        assertEquals(pQueue.size(), 2);
        pQueue.poll();
        assertEquals(pQueue.size(), 1);
        pQueue.poll();
        assertEquals(pQueue.size(), 0);
    }

    @Test
    public void clear() throws Exception {
    }

    @Test
    public void iterator() throws Exception {
    }

}