package ru.tilacyn.main;

import ru.tilacyn.dijkstra.Dijkstra;
import ru.tilacyn.graph.Graph;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * a console util for calculating on graph
 * Graph should be given as a console input
 * this way:
 * n m
 * k
 * w_1 w_2 ... w_n
 * i_1 j_1 len_1
 * ...
 * i_m j_m len_m
 *
 * n, m - sizes of graph
 * w_i - "i" vertex weight
 * [i_k, j_k, len_k] - edge description
 *
 * as a result Dijkstra evaluation is called
 * and attempts, distances and invasion order are printed
 * (for detailed information look at Dijkstra documentation)
 */
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        ArrayList<ArrayList<Integer>> g = new ArrayList<>();
        TreeMap<Graph.Edge, Integer> edges = new TreeMap<>();
        int[] weight = new int[n];

        in.nextInt();
        for (int i = 0; i < n; i++) {
            weight[i] = in.nextInt();
            g.add(new ArrayList<>());
        }

        for (int p = 0; p < m; p++) {
            int i = in.nextInt(), j = in.nextInt(), len = in.nextInt();
            i--;
            j--;

            g.get(i).add(j);
            g.get(j).add(i);

            edges.put(new Graph.Edge(i, j), len);
            edges.put(new Graph.Edge(j, i), len);
        }

        Graph graph = new Graph(g, weight, edges);
        Dijkstra dijkstra = new Dijkstra(graph);
        dijkstra.evaluate();


        ArrayList<ArrayList<Boolean>> attempts = dijkstra.getAttempts();

        for (int i = 0; i < n; i++) {
            for (Boolean attempt : attempts.get(i)) {
                if (attempt) {
                    System.out.print("y ");
                } else {
                    System.out.print("n ");
                }
            }
            System.out.println();
        }

        for (int e : dijkstra.getInvasionOrder()) {
            System.out.print(e + 1 + " ");
        }
        System.out.println();

        for (int d : dijkstra.getDistances()) {
            System.out.print(d + " ");
        }
        System.out.println();

    }
}
