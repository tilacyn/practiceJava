package ru.tilacyn.spiral;

import java.util.*;

/**
 * a class with two static methods processing matrix
 */

public class Spiral {

    /**
     * Puts matrix elements in the spiral order eg.
     * (1, 1), (1, 2), (2, 2), (2, 1), (2, 0), (1, 0), (0, 0) etc.
     * for matrix: 3x3
     * @param arr - matrix
     * @return res - a one-dimensional array with matrix elements
     * that have been put in the order described
     */

    public static int[] printSpiral(int arr[][]) {
        int n = arr.length;
        int curi = n / 2;
        int curj = n / 2;
        int countPassed = 0;
        int[] res = new int[n * n];
        if(n == 1) {
            res[0] = arr[0][0];
            return res;
        }

        res[countPassed++] = arr[curi][curj++];
        res[countPassed++] = arr[curi][curj];

        for(int i = 2; i < n; i += 2) {

            for(int j = 0; j < i - 1; j++) {
                curi++;
                res[countPassed++] = arr[curi][curj];
            }

            for(int j = 0; j < i; j++) {
                curj--;
                res[countPassed++] = arr[curi][curj];
            }

            for(int j = 0; j < i; j++) {
                curi--;
                res[countPassed++] = arr[curi][curj];
            }
            int k = i + 1;
            if(i == n - 1)
                k--;

            for(int j = 0; j < k; j++) {
                curj++;
                res[countPassed++] = arr[curi][curj];
            }
        }
        return res;
    }

    /**
     * sorts matrix columns by the first elements
     * @param arr - matrix
     * @return result matrix
     */

    public static int[][] sortByCols(int arr[][]) {
        int n = arr.length;
        int[] firstRow = new int[n];
        int[] firstRowIndexes = new int[n];
        int[][] res = new int[n][n];

        for(int i = 0; i < n; i++) {
            firstRow[i] = arr[0][i];
            firstRowIndexes[i] = i;
        }

        for(int i = 0; i < n; i++) {
            int min = 100;
            int minPos = 0;
            for(int j = i; j < n; j++) {
                if(firstRow[j] < min) {
                    min = firstRow[j];
                    minPos = j;
                }
            }

            int tmp = firstRowIndexes[i];
            firstRowIndexes[i] = firstRowIndexes[minPos];
            firstRowIndexes[minPos] = tmp;

            firstRow[minPos] = firstRow[i];
            firstRow[i] = min;
        }

        Arrays.sort(firstRow);
        for(int j = 0; j < n; j++) {
            int col = firstRowIndexes[j];
            for(int i = 0; i < n; i++) {
                res[i][j] = arr[i][col];
            }
        }
        return res;
    }
}
