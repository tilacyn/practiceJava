package ru.tilacyn.dijkstra;

import org.jetbrains.annotations.NotNull;
import ru.tilacyn.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * class for evaluating on graph
 * situation that is emulated
 * we have a set of used vertices
 * and on each step we find the closest vertex to this set
 * and emulate a battle for this vertex:
 * each of its used neighbours sends an army, and army proportions equal vertex weights
 * so defenders also have an army
 * each time invaders want to conquer a new vertex and a battle result is random
 * but probability is equal to k * (INVADERS ARMY)/(INVADERS ARMY + DEFENDERS ARMY)
 * where k is the specified in constructor number of type double
 * as a result the vertex is conquered and the process goes on
 * <p>
 * Dijkstra algorithm with difficulty O (n^2) is used for the solution
 */
public class Dijkstra {
    private ArrayList<ArrayList<Boolean>> attempts;
    private Graph graph;
    private int[] d;
    private boolean[] used;
    private ArrayList<Integer> invasionOrder;
    private double k;
    private int[] from;

    /**
     * the only constructor with graph and double k as parameters
     *
     * @param g graph
     * @param k double number which is multiplied to the real probability of battle result
     */
    public Dijkstra(@NotNull Graph g, double k) {
        graph = g;
        d = new int[graph.n];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[0] = 0;
        attempts = new ArrayList<>(graph.n);
        used = new boolean[graph.n];
        invasionOrder = new ArrayList<>();
        this.k = k;
        from = new int[graph.n];
    }

    /**
     * function that evaluates everything
     * after calling this function everything is calculated and can be used
     */
    public void evaluate() {
        for (int i = 0; i < graph.n; i++) {
            int mini = 0;
            int mind = Integer.MAX_VALUE;
            for (int j = 0; j < graph.n; j++) {
                if (d[j] < mind && !used[j]) {
                    mini = j;
                    mind = d[j];
                }
            }
            processVertex(mini);
        }
    }

    private void processVertex(int i) {
        used[i] = true;
        invasionOrder.add(i);

        int invaders = 0;
        int defenders = graph.getWeight(i);
        for (int neighbour : graph.getNeighbours(i)) {
            if (used[neighbour]) {
                invaders += graph.getWeight(neighbour);
            }
        }


        double probability = (double) invaders / (double) (invaders + defenders);
        probability *= k;

        if (i == 0) {
            probability = 1.0;
        }

        double random = Math.random();
        ArrayList<Boolean> ab = new ArrayList<>();
        while (random > probability) {
            ab.add(false);
            random = Math.random();
        }
        ab.add(true);

        attempts.add(ab);

        for (int neighbour : graph.getNeighbours(i)) {
            if (!used[neighbour]) {
                if (d[neighbour] > d[i] + graph.getDistance(i, neighbour)) {
                    d[neighbour] = d[i] + graph.getDistance(i, neighbour);
                    from[neighbour] = i;
                }
            }
        }
    }

    /**
     * returns distances to the vertex with number 1
     *
     * @return an int[] array of distances to the vertex with number 1
     */
    public int[] getDistances() {
        return d;
    }

    /**
     * element of the big ArrayList with number "i" is a small list of attempts to conquer vertex
     * with number "i"
     *
     * @return all the calculated attempts
     */
    public ArrayList<ArrayList<Boolean>> getAttempts() {
        return attempts;
    }


    /**
     * returns vertex numbers in order of invasion
     *
     * @return an ArrayList of vertex numbers, which are in the order of invasion
     */
    public ArrayList<Integer> getInvasionOrder() {
        return invasionOrder;
    }


    /**
     * returns full path from the vertex number 0 to the vertex number i
     * path is guaranteed to be the shortest in the graph
     *
     * @param i specified vertex number
     * @return an ArrayList<Integer> of vertex numbers in the path
     */
    public ArrayList<Integer> getPath(int i) {
        ArrayList<Integer> path = new ArrayList<>();
        path.add(i);
        while (i != 0) {
            path.add(from[i]);
            i = from[i];
        }
        Collections.reverse(path);
        return path;
    }
}
