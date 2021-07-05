package ru.tilacyn.ox;

import org.jetbrains.annotations.NotNull;

/**
 * a controller that provides class connection
 */
public class Controller {
    private Logic logic;
    private GameLayout gameLayout;
    private MainLayout mainLayout;
    private Main main;

    private boolean bot = false;

    int XWins = 0;
    int OWins = 0;
    int draws = 0;


    /**
     * starts a new hot seat game
     */
    public void start() {
        bot = false;
        resetField();
        logic.resetField();
        main.startGame();
    }

    /**
     * continues current game
     */
    public void continueGame() {
        main.startGame();
    }

    /**
     * starts a new game with bot
     */
    public void startWithBot() {
        bot = true;
        resetField();
        logic.resetField();
        main.startGame();
    }

    /**
     * returns back to the main scene from game scene
     */
    public void backToMain() {
        main.backToMain();
    }

    /**
     * resets only the field that is shown in UI
     */
    public void resetField() {
        gameLayout.redraw();
    }

    /**
     * processes pressing on the exact button
     *
     * @param i - button row number
     * @param j - button column number
     */
    public void press(int i, int j) {
        if (!bot) {
            logic.press(i, j);
        } else {
            logic.pressWithBot(i, j);
        }
    }

    /**
     * draws changes that happened after a move
     *
     * @param i             row number
     * @param j             column number
     * @param currentPlayer player that made a move number
     */
    public void draw(int i, int j, int currentPlayer) {
        gameLayout.draw(i, j, currentPlayer);
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

    /**
     * binds this controller to the MainLayout object
     *
     * @param mainLayout MainLayout object
     */
    public void setMainLayout(@NotNull MainLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    /**
     * binds this controller to the Main object
     *
     * @param main Main object
     */
    public void setMain(@NotNull Main main) {
        this.main = main;
    }

    /**
     * changes the statistics according to the last game result
     *
     * @param winner 1 if X won, 2 if O won, -1 if draw occurred
     */
    public void addStatistics(int winner) {
        if (winner == 1) {
            XWins++;
        } else if (winner == 2) {
            OWins++;
        } else {
            draws++;
        }
        mainLayout.addStatistics();
    }

    /**
     * returns number of X wins
     *
     * @return number of X winds
     */
    public int getXWins() {
        return XWins;
    }

    /**
     * returns number of O wins
     *
     * @return number of O wins
     */
    public int getOWins() {
        return OWins;
    }

    /**
     * returns number of draws
     *
     * @return number of draws
     */
    public int getDraws() {
        return draws;
    }
}
