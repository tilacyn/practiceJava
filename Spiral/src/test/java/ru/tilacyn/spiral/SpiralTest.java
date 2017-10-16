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
        int[][] arr = new int[3][3];
        arr[0][0] = 7;
        arr[0][1] = 8;
        arr[0][2] = 9;
        arr[1][0] = 6;
        arr[1][1] = 1;
        arr[1][2] = 2;
        arr[2][0] = 5;
        arr[2][1] = 4;
        arr[2][2] = 3;
        Spiral spiral = new Spiral();
        int[] res = spiral.printSpiral(arr);
        for(int i = 0 ; i < 9; i++) {
            assertEquals(res[i], i + 1);
        }



        arr = new int[5][5];
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                arr[i][j] = 5 * i + j;
            }
        }

        res = spiral.printSpiral(arr);
        assertEquals(12, res[0]);
        assertEquals(13, res[1]);
        assertEquals(18, res[2]);
        assertEquals(17, res[3]);
        assertEquals(16, res[4]);
        assertEquals(11, res[5]);
        assertEquals(6, res[6]);
        assertEquals(7, res[7]);
        assertEquals(8, res[8]);
        assertEquals(9, res[9]);
        assertEquals(14, res[10]);
        assertEquals(19, res[11]);
        assertEquals(24, res[12]);
        assertEquals(23, res[13]);
        assertEquals(22, res[14]);
        assertEquals(21, res[15]);
        assertEquals(20, res[16]);
        assertEquals(15, res[17]);
        assertEquals(10, res[18]);
        assertEquals(5, res[19]);
        assertEquals(0, res[20]);
        assertEquals(1, res[21]);
        assertEquals(2, res[22]);
        assertEquals(3, res[23]);
        assertEquals(4, res[24]);
    }

    /**
     * an additional function to test sortByCols: it creates a random matrix but in the first row
     * each element is unique and in range [0, n - 1]
     * and checks whether a result matrix has a first row like [0, 1, 2, ..., n - 1]
     * @param n - square side
     */


    public static void randomTest(int n) {
        int[][] arr = new int[n][n];
        ArrayList<Integer> notUsed = new ArrayList();

        Random random = new Random();

        for(int i = 0; i < n; i++) {
            notUsed.add(i);
        }
        for(int i = 0; i < n; i++) {
            int j = Math.abs(random.nextInt()) % notUsed.size();
            arr[0][i] = notUsed.get(j);

            Integer tmp = notUsed.remove(j);
        }
        for(int i = 1; i < n; i++) {
            for(int j = 0; j < n; j++) {
                arr[i][j] = random.nextInt();
            }
        }

        Spiral spiral = new Spiral();

        int[][] res = spiral.sortByCols(arr);

        for(int i = 0; i < n; i++)
            assertEquals(res[0][i], i);
    }

    @Test
    public void sortByCols() {
        int[][] arr = new int[3][3];
        arr[0][0] = 3;
        arr[0][1] = 2;
        arr[0][2] = 1;
        arr[1][0] = 6;
        arr[1][1] = 5;
        arr[1][2] = 4;
        arr[2][0] = 9;
        arr[2][1] = 8;
        arr[2][2] = 7;
        Spiral spiral = new Spiral();
        int[][] res = spiral.sortByCols(arr);

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {
                assertEquals(res[i][j], i * 3 + j + 1);
            }

        for(int i = 1; i < 50; i++) {
            randomTest(i);
        }

    }

}