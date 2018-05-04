package ru.tilacyn.pair;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Logic {
    private Controller controller;
    private ArrayList<ArrayList<Integer>> field;
    private int n;

    private boolean pressed = false;
    private int pressedI;
    private int pressedJ;

    private boolean[][] used;

    public Logic(@NotNull Controller controller) {
        this.controller = controller;
        n = controller.getN();
        field = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            field.add(new ArrayList<>());
        }

        used = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                used[i][j] = false;
            }
        }

        fillField();
    }

    private void fillField() {
        ArrayList<Integer> buffer = new ArrayList<>(n * n);

        for (int i = 0; i < n * n / 2; i++) {
            buffer.add(i);
        }

        for (int i = 0; i < n * n / 2; i++) {
            buffer.add(i);
        }

        Collections.shuffle(buffer);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                field.get(i).add(buffer.get(i * n + j));
            }
        }

    }

    public void press(int i, int j) {
        if (!pressed) {
            if(used[i][j]) {
                return;
            }
            controller.set(i, j);

            pressedI = i;
            pressedJ = j;
            pressed = true;
        } else {
            if ((i != pressedI || j != pressedJ) &&
                    (int) field.get(i).get(j) == field.get(pressedI).get(pressedJ)) {
                controller.success(i, j, pressedI, pressedJ);
                used[i][j] = true;
                used[pressedI][pressedI] = true;
            } else {
                controller.reset(pressedI, pressedJ);
            }
            pressed = false;
        }
    }

    public ArrayList<ArrayList<Integer>> getField() {
        return field;
    }
}
