package ru.tilacyn.graph;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.tilacyn.graph.GraphTest.Sources.*;

import java.util.*;

public class GraphTest {

    /**
     * a nested class which is used both in GraphTest and DijkstraTest
     * it contains Graph parameters examples
     */
    public static class Sources {
        public static <T> ArrayList<T> arrayToAL(T[] array) {
            ArrayList<T> al = new ArrayList<>();
            Collections.addAll(al, array);
            return al;
        }

        private static void put(int i, int j, int len, Map<Graph.Edge, Integer> map) {
            map.put(new Graph.Edge(i, j), len);
            map.put(new Graph.Edge(j, i), len);
        }

        static final ArrayList<ArrayList<Integer>> g1 = new ArrayList<>();
        static final int[] w1 = new int[]{1, 1, 1, 1};
        static final TreeMap<Graph.Edge, Integer> e1 = new TreeMap<>();

        static final ArrayList<ArrayList<Integer>> g2 = new ArrayList<>();
        static final int[] w2 = new int[]{1, 1, 1, 1, 0};
        static final TreeMap<Graph.Edge, Integer> e2 = new TreeMap<>();

        static final ArrayList<ArrayList<Integer>> g3 = new ArrayList<>();
        static final int[] w3 = new int[]{2, 3, 4, 1, 10, 12, 1, 10};
        static final TreeMap<Graph.Edge, Integer> e3 = new TreeMap<>();


        static {
            g1.add(arrayToAL(new Integer[]{1, 2}));
            g1.add(arrayToAL(new Integer[]{0}));
            g1.add(arrayToAL(new Integer[]{0, 3}));
            g1.add(arrayToAL(new Integer[]{2}));

            put(0, 1, 1, e1);
            put(0, 2, 2, e1);
            put(2, 3, 2, e1);

            g2.add(arrayToAL(new Integer[]{1, 2, 3}));
            g2.add(arrayToAL(new Integer[]{0, 2}));
            g2.add(arrayToAL(new Integer[]{0, 1, 3}));
            g2.add(arrayToAL(new Integer[]{2, 0, 4}));
            g2.add(arrayToAL(new Integer[]{3}));

            put(0, 3, 1, e2);
            put(0, 2, 5, e2);
            put(0, 1, 5, e2);
            put(3, 2, 2, e2);
            put(1, 2, 1, e2);
            put(3, 4, 5, e2);

            g3.add(arrayToAL(new Integer[]{1, 2})); // 0
            g3.add(arrayToAL(new Integer[]{0, 3, 4})); // 1
            g3.add(arrayToAL(new Integer[]{0, 4, 7})); // 2
            g3.add(arrayToAL(new Integer[]{1, 5})); // 3
            g3.add(arrayToAL(new Integer[]{1, 5, 6, 2})); // 4
            g3.add(arrayToAL(new Integer[]{4, 3})); // 5
            g3.add(arrayToAL(new Integer[]{4, 7})); // 6
            g3.add(arrayToAL(new Integer[]{6, 2})); // 7

            put(0, 1, 4, e3);
            put(0, 2, 4, e3);
            put(1, 3, 1, e3);
            put(1, 4, 3, e3);
            put(2, 4, 2, e3);
            put(2, 7, 10, e3);
            put(3, 5, 5, e3);
            put(4, 5, 2, e3);
            put(4, 6, 3, e3);
            put(7, 6, 3, e3);
        }
    }

    public final Graph graph1 = new Graph(g1, w1, e1);
    public final Graph graph2 = new Graph(g2, w2, e2);
    public final Graph graph3 = new Graph(g3, w3, e3);


    @Test
    public void getNeighbours() throws Exception {
        assertEquals(graph1.getNeighbours(0), Sources.arrayToAL(new Integer[]{1, 2}));
        assertEquals(graph1.getNeighbours(1), Sources.arrayToAL(new Integer[]{0}));
        assertEquals(graph1.getNeighbours(2), Sources.arrayToAL(new Integer[]{0, 3}));
        assertEquals(graph1.getNeighbours(3), Sources.arrayToAL(new Integer[]{2}));
    }

    @Test
    public void getDistance() throws Exception {
        checkEdges(graph1, e1);
        checkEdges(graph2, e2);
        checkEdges(graph3, e3);
    }

    private void checkEdges(Graph graph, Map<Graph.Edge, Integer> e) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (e.containsKey(new Graph.Edge(i, j))) {
                    assertEquals((int) e.get(new Graph.Edge(i, j)), graph.getDistance(i, j));
                }
            }
        }
    }

    @Test
    public void getWeight() throws Exception {
        for (int i = 0; i < 4; i++) {
            assertEquals(w1[i], graph1.getWeight(i));
        }
    }

}