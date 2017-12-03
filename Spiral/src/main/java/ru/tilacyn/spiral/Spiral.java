package ru.tilacyn.spiral;

/**
 * a class with two static methods processing matrix
 */
public class Spiral {

    /**
     * Puts matrix elements in the spiral order eg.
     * (1, 1), (1, 2), (2, 2), (2, 1), (2, 0), (1, 0), (0, 0) etc.
     * for matrix: 3x3
     *
     * @param array - matrix
     * @return res - a one-dimensional array with matrix elements
     * that have been put in the order described
     */
    public static int[] printSpiral(int array[][]) {
        int n = array.length;
        int curi = n / 2;
        int curj = n / 2;
        int countPassed = 0;
        int[] res = new int[n * n];
        if (n == 1) {
            res[0] = array[0][0];
            return res;
        }

        res[countPassed++] = array[curi][curj++];
        res[countPassed++] = array[curi][curj];

        for (int i = 2; i < n; i += 2) {

            for (int j = 0; j < i - 1; j++) {
                curi++;
                res[countPassed++] = array[curi][curj];
            }

            for (int j = 0; j < i; j++) {
                curj--;
                res[countPassed++] = array[curi][curj];
            }

            for (int j = 0; j < i; j++) {
                curi--;
                res[countPassed++] = array[curi][curj];
            }
            int k = i + 1;
            if (i == n - 1) {
                k--;
            }

            for (int j = 0; j < k; j++) {
                curj++;
                res[countPassed++] = array[curi][curj];
            }
        }
        return res;
    }

    /**
     * sorts matrix columns by the first elements
     * this function guarantees that al the columns, that have the same first elements
     * will be left in the original order
     *
     * @param array - matrix
     * @return result matrix
     */
    public static int[][] sortByCols(int array[][]) {
        int n = array.length;
        int[] firstRow = new int[n];
        int[] firstRowIndexes = new int[n];
        int[][] res = new int[n][n];

        for (int i = 0; i < n; i++) {
            firstRow[i] = array[0][i];
            firstRowIndexes[i] = i;
        }

        for (int i = 0; i < n; i++) {
            int min = Integer.MAX_VALUE;
            int minPos = 0;
            for (int j = i; j < n; j++) {
                if (firstRow[j] < min) {
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

        for (int j = 0; j < n; j++) {
            int col = firstRowIndexes[j];
            for (int i = 0; i < n; i++) {
                res[i][j] = array[i][col];
            }
        }
        return res;
    }
}
