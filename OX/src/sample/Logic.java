package sample;

import java.util.Random;

/**
 * A class for game logic
 */
public class Logic {
    private int[][] field = new int[3][3];
    private int currentPlayer = 1;

    private Controller controller;

    /**
     * creates a new instance and binds it to the specified controller
     * @param controller specified controller
     */
    public Logic(Controller controller) {
        this.controller = controller;
    }

    /**
     * resets field in game logic (makes all the cells empty)
     */
    public void resetField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = 0;
            }
        }
        currentPlayer = 1;
    }


    /**
     * processes pressing on a button in a hot seat mode
     * @param i row number
     * @param j column number
     */
    public void press(int i, int j) {
        System.out.println(currentPlayer);
        if (i < 0 || j < 0 || i > 2 || j > 2) {
            throw new ArrayIndexOutOfBoundsException("no such a cell");
        }

        if (field[i][j] != 0) {
            return;
        }

        field[i][j] = currentPlayer;

        controller.draw(i, j, currentPlayer);

        if (isEnd()) {
            controller.addStatistics(getResult());
            controller.backToMain();
            controller.resetField();
            resetField();
        } else {
            currentPlayer = 3 - currentPlayer;
        }
    }

    /**
     * processes pressing on a cell in playing with bot mode
     * @param i row number
     * @param j column number
     */
    public void pressWithBot(int i, int j) {
        if (i < 0 || j < 0 || i > 2 || j > 2) {
            throw new ArrayIndexOutOfBoundsException("no such a cell");
        }

        if (field[i][j] != 0) {
            return;
        }

        field[i][j] = currentPlayer;

        controller.draw(i, j, currentPlayer);

        if (isEnd()) {
            controller.addStatistics(getResult());
            controller.backToMain();
            controller.resetField();
            resetField();
        } else {
            currentPlayer = 3 - currentPlayer;
        }

        int x = 1, y = 1;

        Random rand = new Random();

        while (field[x][y] != 0) {
            x = (rand.nextInt() % 3 + 100) % 3;
            y = (rand.nextInt() % 3 + 100) % 3;
        }

        field[x][y] = currentPlayer;

        controller.draw(x, y, currentPlayer);

        if (isEnd()) {
            controller.addStatistics(getResult());
            controller.backToMain();
            controller.resetField();
            resetField();
        } else {
            currentPlayer = 3 - currentPlayer;
        }
    }


    /**
     * checks whether a game has ended
     * @return 1 if X won, 2 if O won, -1 if draw occurred, 0 if game has not ended yet
     */
    public int getResult() {
        for (int i = 1; i <= 2; i++) {
            if (checkRow(0, i) || checkRow(1, i) || checkRow(2, i)) {
                return i;
            }
            if (checkCol(0, i) || checkCol(1, i) || checkCol(2, i)) {
                return i;
            }
            if (field[0][0] == i && field[1][1] == i && field[2][2] == i) {
                return i;
            }
            if (field[0][2] == i && field[1][1] == i && field[2][0] == i) {
                return i;
            }
        }

        boolean hasEmpty = false;

        for (int i = 0; i < 9; i++) {
            if (field[i % 3][i / 3] == 0) {
                hasEmpty = true;
            }
        }

        if (!hasEmpty) {
            return -1;
        }

        return 0;
    }

    /**
     * returns whether game has ended already
     * @return true if game ended else false
     */
    public boolean isEnd() {
        return getResult() != 0;
    }

    private boolean checkRow(int i, int player) {
        return field[i][0] == player && field[i][1] == player && field[i][2] == player;
    }

    private boolean checkCol(int i, int player) {
        return field[0][i] == player && field[1][i] == player && field[2][i] == player;
    }

}
