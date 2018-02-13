package ru.tilacyn.dijkstra;

import org.junit.Test;
import ru.tilacyn.graph.GraphTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class DijkstraTest {
    private GraphTest gt = new GraphTest();

    @Test
    public void graph1() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph1, 0.6);
        dijkstra.evaluate();
        assertArrayEquals(new int[]{0, 1, 2, 4}, dijkstra.getDistances());
        assertEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3)),
                dijkstra.getInvasionOrder());

    }

    @Test
    public void graph2() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph2, 0.7);
        dijkstra.evaluate();
        assertArrayEquals(new int[]{0, 4, 3, 1, 6}, dijkstra.getDistances());
        assertEquals(new ArrayList<>(Arrays.asList(0, 3, 2, 1, 4)),
                dijkstra.getInvasionOrder());
    }

    @Test
    public void graph3() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph3, 0.8);
        dijkstra.evaluate();
        for (int i : dijkstra.getInvasionOrder()) {
            System.out.print(i + " ");
        }
        assertArrayEquals(new int[]{0, 4, 4, 5, 6, 8, 9, 12}, dijkstra.getDistances());
        assertTrue(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7))
                .equals(dijkstra.getInvasionOrder()) ||
                new ArrayList<>(Arrays.asList(0, 2, 1, 3, 4, 5, 6, 7))
                        .equals(dijkstra.getInvasionOrder()));
    }

    @Test
    public void graph2Attempts() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph2, 0.9);
        dijkstra.evaluate();
        assertEquals(dijkstra.getAttempts().get(4).size(), 1);
    }

    @Test
    public void graph3Attempts() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph3, 0.5);
        dijkstra.evaluate();
        for (ArrayList<Boolean> al : dijkstra.getAttempts()) {
            for (int i = 0; i < al.size(); i++) {
                if (i == al.size() - 1) {
                    assertTrue(al.get(i));
                } else {
                    assertFalse(al.get(i));
                }
            }
        }
    }

    @Test
    public void graph1Paths() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph1, 0.5);
        dijkstra.evaluate();
        assertEquals(new ArrayList<>(Collections.singletonList(0)), dijkstra.getPath(0));
        assertEquals(new ArrayList<>(Arrays.asList(0, 1)), dijkstra.getPath(1));
        assertEquals(new ArrayList<>(Arrays.asList(0, 2)), dijkstra.getPath(2));
        assertEquals(new ArrayList<>(Arrays.asList(0, 2, 3)), dijkstra.getPath(3));
    }

    @Test
    public void graph2Paths() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph2, 0.3);
        dijkstra.evaluate();
        assertEquals(new ArrayList<>(Collections.singletonList(0)), dijkstra.getPath(0));
        assertEquals(new ArrayList<>(Arrays.asList(0, 3, 2, 1)), dijkstra.getPath(1));
        assertEquals(new ArrayList<>(Arrays.asList(0, 3, 2)), dijkstra.getPath(2));
        assertEquals(new ArrayList<>(Arrays.asList(0, 3)), dijkstra.getPath(3));
        assertEquals(new ArrayList<>(Arrays.asList(0, 3, 4)), dijkstra.getPath(4));
    }

    @Test
    public void graph3Paths() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph3, 0.8);
        dijkstra.evaluate();
        assertEquals(new ArrayList<>(Collections.singletonList(0)), dijkstra.getPath(0));
        assertEquals(new ArrayList<>(Arrays.asList(0, 1)), dijkstra.getPath(1));
        assertEquals(new ArrayList<>(Arrays.asList(0, 2)), dijkstra.getPath(2));
        assertEquals(new ArrayList<>(Arrays.asList(0, 1, 3)), dijkstra.getPath(3));
        assertEquals(new ArrayList<>(Arrays.asList(0, 2, 4)), dijkstra.getPath(4));
        assertEquals(new ArrayList<>(Arrays.asList(0, 2, 4, 5)), dijkstra.getPath(5));
        assertEquals(new ArrayList<>(Arrays.asList(0, 2, 4, 6)), dijkstra.getPath(6));
        assertEquals(new ArrayList<>(Arrays.asList(0, 2, 4, 6, 7)), dijkstra.getPath(7));
    }
}