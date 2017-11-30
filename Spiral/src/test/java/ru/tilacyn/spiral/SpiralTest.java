package ru.tilacyn.spiral;

import org.junit.Test;

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
        for(int i = 0 ; i < 9; i++){
            assertEquals(res[i], i + 1);
        }
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

    }

}