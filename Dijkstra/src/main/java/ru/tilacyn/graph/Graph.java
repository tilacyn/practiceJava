package ru.tilacyn.graph;

import java.util.ArrayList;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

/**
 * class that contains graph
 * it contains a nested class edge
 * to store edge length in map with keys of type edge and values of type Integer
 * also an ArrayList of neighbours is contained for each vertex
 * graph is not orientated
 * graph is weighted: each vertex has weight so there is an array of vertex weights
 * vertex numeration is [0, ..., n - 1]
 */
public class Graph {

    /**
     * final fields
     * n: number of vertices
     * n has been made public not to create getters or extra fields in class Dijkstra
     */
    public final int n;

    private ArrayList<ArrayList<Integer>> g;
    private int[] weight;
    private Map<Edge, Integer> edges;

    /**
     * Graph constructor, which is the only constructor in this class
     *
     * @param g      List of lists of neighbours
     * @param weight array of weights
     * @param edges  map of edge lengths
     */
    public Graph(@NotNull ArrayList<ArrayList<Integer>> g,
                 @NotNull int[] weight,
                 @NotNull Map<Edge, Integer> edges) {
        this.g = g;
        this.n = g.size();
        this.weight = weight;
        this.edges = edges;
    }

    /**
     * returns neighbours of vertex with specified number
     *
     * @param i number of vertex
     * @return all the neighbours of the vertice with number i
     */
    public ArrayList<Integer> getNeighbours(int i) {
        return g.get(i);
    }

    /**
     * returns distance between vertices with specified numbers
     *
     * @param i first vertex
     * @param j second vertex
     * @return distance between these vertices or Integer.MAX_VALUE in case they are not neighbours
     */
    public int getDistance(int i, int j) {
        if (edges.containsKey(new Edge(i, j))) {
            return edges.get(new Edge(i, j));
        }
        return Integer.MAX_VALUE;
    }

    /**
     * returns weight of the vertex with specified number
     *
     * @param i number of vertex
     * @return weight of the vertex with this number
     */
    public int getWeight(int i) {
        return weight[i];
    }

    /**
     * Nested class Edge implements interface comparable
     * so that we could store them in TreeMap for example
     * each Object contains two Integers: (to, from)
     * constructor and method compareTo are trivial
     */
    public static class Edge implements Comparable<Edge> {
        private int i;
        private int j;

        public Edge(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public int compareTo(@NotNull Edge o) {
            if (i == o.i) {
                return Integer.compare(j, o.j);
            } else {
                if (i > o.i) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }
}
