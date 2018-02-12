package ru.tilacyn.main;

import ru.tilacyn.dijkstra.Dijkstra;
import ru.tilacyn.graph.Graph;

import java.io.*;
import java.util.*;

/**
 * a console util for calculating on graph
 * main method accepts String[] array that should contain exactly two strings
 * first - name of input file
 * second - name of output file
 * if args.length differ then InputMismatchException is thrown
 * <p>
 * Information in the input file should be presented this way:
 * n m
 * k // should be separated by dot eg 1.0 instead of 1,0
 * w_1 w_2 ... w_n
 * i_1 j_1 len_1
 * ...
 * i_m j_m len_m
 * <p>
 * n, m - sizes of graph
 * w_i - "i" vertex weight
 * [i_k, j_k, len_k] - edge description
 * <p>
 * as a result Dijkstra evaluation is called
 * and attempts, distances and invasion order are printed
 * (for detailed information look at Dijkstra documentation)
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 2) {
            throw new InputMismatchException("Bad Input\nTry again!\n<input filename> <output filename>\n");
        }
        String inputFileName = args[0];
        String outputFileName = args[1];

        Scanner in = new Scanner(new File(inputFileName));
        in.useLocale(Locale.US);
        PrintWriter out = new PrintWriter(new File(outputFileName));

        int n = in.nextInt();
        int m = in.nextInt();

        ArrayList<ArrayList<Integer>> g = new ArrayList<>();
        TreeMap<Graph.Edge, Integer> edges = new TreeMap<>();
        int[] weight = new int[n];


        double k = in.nextDouble();
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
        Dijkstra dijkstra = new Dijkstra(graph, k);
        dijkstra.evaluate();

        ArrayList<ArrayList<Boolean>> attempts = dijkstra.getAttempts();

        for (int i = 0; i < n; i++) {
            for (Boolean attempt : attempts.get(i)) {
                if (attempt) {
                    out.print("y ");
                } else {
                    out.print("n ");
                }
            }
            out.println();
        }

        for (int e : dijkstra.getInvasionOrder()) {
            out.print(e + 1 + " ");
        }
        out.println();

        for (int d : dijkstra.getDistances()) {
            out.print(d + " ");
        }
        out.println();
        out.close();

    }
}
