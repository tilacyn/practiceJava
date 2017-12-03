package ru.tilacyn.spiral;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * test class for Spiral
 */
public class SpiralTest {
    @Test
    public void printSpiral() {
        int[][] arr = {{7, 8, 9}, {6, 1, 2}, {5, 4, 3}};
        Spiral spiral = new Spiral();
        int[] res = spiral.printSpiral(arr);
        for (int i = 0; i < 9; i++) {
            assertEquals(res[i], i + 1);
        }


        arr = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                arr[i][j] = 5 * i + j;
            }
        }

        int[] expected = {12, 13, 18, 17, 16, 11, 6, 7, 8, 9,
                14, 19, 24, 23, 22, 21, 20, 15, 10, 5, 0, 1, 2, 3, 4};

        res = spiral.printSpiral(arr);

        assertArrayEquals(expected, res);
    }

    /**
     * an additional function to test sortByCols: it creates a random matrix but in the first row
     * each element is unique and in range [0, n - 1]
     * and checks whether a result matrix has a first row like [0, 1, 2, ..., n - 1]
     *
     * @param n - square side
     */


    public static void randomTest(int n) {
        int[][] arr = new int[n][n];
        ArrayList<Integer> notUsed = new ArrayList();

        Random random = new Random();

        for (int i = 0; i < n; i++) {
            notUsed.add(i);
        }
        for (int i = 0; i < n; i++) {
            int j = Math.abs(random.nextInt()) % notUsed.size();
            arr[0][i] = notUsed.get(j);

            Integer tmp = notUsed.remove(j);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = random.nextInt();
            }
        }

        Spiral spiral = new Spiral();

        int[][] res = spiral.sortByCols(arr);

        for (int i = 0; i < n; i++)
            assertEquals(res[0][i], i);
    }

    @Test
    public void sortByCols() {
        int[][] arr = {{3, 2, 1}, {6, 5, 4}, {9, 8, 7}};

        Spiral spiral = new Spiral();
        int[][] res = spiral.sortByCols(arr);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                assertEquals(res[i][j], i * 3 + j + 1);
            }

        for (int i = 1; i < 50; i++) {
            randomTest(i);
        }

        int[][] arr1 = {{1, 1, 1}, {5, 10, 7}, {0, 3, 2}};

        assertArrayEquals(arr1, spiral.sortByCols(arr1));

        int[][] arr2 = {{1, -90, 1}, {100, 100, 23}, {55, 34, 0}};

        int[][] res2 = {{-90, 1, 1}, {100, 100, 23}, {34, 55, 0}};

        assertArrayEquals(res2, spiral.sortByCols(arr2));

        int[][] arr3 = {{4000, 399, 12, -101}, {501, 502, 503, 504}, {0, 1, 89, 3}, {-1, 3, 5, 7}};

        int[][] res3 = {{-101, 12, 399, 4000}, {504, 503, 502, 501}, {3, 89, 1, 0}, {7, 5, 3, -1}};

        assertArrayEquals(res3, spiral.sortByCols(arr3));

    }

}