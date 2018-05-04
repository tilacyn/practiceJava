package ru.tilacyn.pair;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class LogicTest {
    private void checkPress(int n) {
        Controller controller = mock(Controller.class);

        when(controller.getN()).thenReturn(n);

        Logic logic = new Logic(controller);

        ArrayList<ArrayList<Integer>> field = logic.getField();

        int s = field.get(0).get(0);


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(field.get(i).get(j) + " ");
            }
            System.out.println();
        }

        int ii = 0 , jj = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i > 0 || j > 0) && field.get(i).get(j) == s) {
                    ii = i;
                    jj = j;
                    logic.press(i, j);
                    logic.press(0, 0);
                    break;
                }
            }
        }

        verify(controller, times(1)).success(0, 0, ii, jj);
    }

    @Test
    public void test() {
        for (int i = 5 ; i < 10; i++) {
            checkPress(2 * i);
        }
    }


}