package ru.tilacyn.dijkstra;

import org.junit.Test;
import ru.tilacyn.graph.GraphTest;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DijkstraTest {
    private GraphTest gt = new GraphTest();

    @Test
    public void graph1() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph1);
        dijkstra.evaluate();
        assertArrayEquals(new int[]{0, 1, 2, 4}, dijkstra.getDistances());
        assertEquals(GraphTest.Sources.arrayToAL(new Integer[]{0, 1, 2, 3}),
                dijkstra.getInvasionOrder());

    }

    @Test
    public void graph2() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph2);
        dijkstra.evaluate();
        assertArrayEquals(new int[]{0, 4, 3, 1, 6}, dijkstra.getDistances());
        assertEquals(GraphTest.Sources.arrayToAL(new Integer[]{0, 3, 2, 1, 4}),
                dijkstra.getInvasionOrder());
    }

    @Test
    public void graph3() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph3);
        dijkstra.evaluate();
        for (int i : dijkstra.getInvasionOrder()) {
            System.out.print(i + " ");
        }
        assertArrayEquals(new int[]{0, 4, 4, 5, 6, 8, 9, 12}, dijkstra.getDistances());
        assertTrue(GraphTest.Sources.arrayToAL(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7})
                .equals(dijkstra.getInvasionOrder()) ||
                GraphTest.Sources.arrayToAL(new Integer[]{0, 2, 1, 3, 4, 5, 6, 7})
                        .equals(dijkstra.getInvasionOrder()));
    }

    @Test
    public void graph2Attempts() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph2);
        dijkstra.evaluate();
        assertEquals(dijkstra.getAttempts().get(4).size(), 1);
    }

    @Test
    public void graph3Attempts() throws Exception {
        Dijkstra dijkstra = new Dijkstra(gt.graph3);
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
}