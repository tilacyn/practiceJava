package ru.tilacyn.dijkstra;

import org.jetbrains.annotations.NotNull;
import ru.tilacyn.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * class for evaluating on graph
 * situation that is emulated
 * we have a set of used vertices
 * and on each step we find the closest vertex to this set
 * and emulate a battle for this vertex:
 * each of its used neighbours sends an army, and army proportions equal vertex weights
 * so defenders also have an army
 * each time invaders want to conquer a new vertex and a battle result is random
 * but probabilty is equal to (INVADORS ARMY)/(INVADORS ARMY + DEFENDERS ARMY)
 * as a result the vertex is conquered and the process goes on
 *
 * Dijkstra algorithm with difficulty O (n^2) is used for the solution
 */
public class Dijkstra {
    private ArrayList<ArrayList<Boolean>> attempts;
    private Graph graph;
    private int[] d;
    private boolean[] used;
    private ArrayList<Integer> invasionOrder;

    /**
     * the only constructor with graph as a parameter
     * @param g graph
     */
    public Dijkstra(@NotNull Graph g) {
        graph = g;
        d = new int[graph.n];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[0] = 0;
        attempts = new ArrayList<>(graph.n);
        used = new boolean[graph.n];
        invasionOrder = new ArrayList<>();
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

        int invadors = 0;
        int defenders = graph.getWeight(i);
        for (int neighbour : graph.getNeighbours(i)) {
            if (used[neighbour]) {
                invadors += graph.getWeight(neighbour);
            }
        }


        double probability = (double) invadors / (double) (invadors + defenders);
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
                    //System.out.println("distance relax: " + i + " " + neighbour);
                }
            }
        }
    }

    /**
     * @return distances to the vertex with number 1
     */
    public int[] getDistances() {
        return d;
    }

    /**
     * element of the big ArrayList with number "i" is a small list of attempts to conquer vertex
     * with number "i"
     * @return all the calculated attempts
     */
    public ArrayList<ArrayList<Boolean>> getAttempts() {
        return attempts;
    }


    /**
     * @return an ArrayList, where vertex numbers are in the order of invasion
     */
    public ArrayList<Integer> getInvasionOrder() {
        return invasionOrder;
    }
}
