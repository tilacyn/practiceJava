package ru.tilacyn.pair;

import org.jetbrains.annotations.NotNull;

public class Controller {
    private Logic logic;
    private GameLayout gameLayout;
    private int n;

    public int getN() {
        return n;
    }

    public Controller(int n) {
        this.n = n;
    }

    public void press(int i, int j) {
        logic.press(i, j);
    }

    public void success(int i1, int j1, int i2, int j2) {
        gameLayout.success(i1, j1, i2, j2);
    }

    public void set(int i, int j) {
        gameLayout.set(i, j);
    }

    public void reset(int i, int j) {
        gameLayout.reset(i, j);
    }

    /**
     * binds this controller to the Logic object
     *
     * @param logic Logic object
     */
    public void setLogic(@NotNull Logic logic) {
        this.logic = logic;
    }

    /**
     * binds this controller to the GameLayout object
     *
     * @param gameLayout GameLayout object
     */
    public void setGameLayout(@NotNull GameLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

}
