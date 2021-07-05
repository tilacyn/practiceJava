package ru.tilacyn.ox;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains and operates with layouts that refer to the game scene
 * layouts:
 * 1. a button to exit and go to the main scene
 * 2. text field "Game Field"
 * 3. a game field with nine cells
 */
public class GameLayout {
    private Controller controller;

    private GridPane grid = new GridPane();
    private BorderPane bp = new BorderPane();
    private Button backToMenu = new Button("Back to Menu");
    private Text gameFieldHead = new Text("Game Field");
    private Button[][] cells = new Button[3][3];
    private Scene scene = new Scene(bp, 300, 275);

    private static final int fieldSize = 3;

    /*
     * set dependencies and parameters
     */ {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                final int ii = i;
                final int jj = j;
                cells[i][j] = new Button("");
                cells[i][j].setOnAction(value -> controller.press(ii, jj));
                grid.add(cells[i][j], i, j);
            }
        }
        bp.setCenter(grid);
        bp.setTop(gameFieldHead);
        bp.setBottom(backToMenu);

        backToMenu.setOnAction(value -> controller.backToMain());

        BorderPane.setMargin(gameFieldHead, new Insets(10.0, 50.0, 50.0, 120.0));
        BorderPane.setMargin(grid, new Insets(50.0, 50.0, 50.0, 120.0));
        BorderPane.setMargin(backToMenu, new Insets(0.0, 0.0, 50.0, 100.0));
    }

    /**
     * creates a new GameLayout bound ot the specified controller
     *
     * @param controller specified Controller
     */
    public GameLayout(@NotNull Controller controller) {
        this.controller = controller;
    }

    /**
     * returns a scene with game layouts
     *
     * @return a scene with game layouts
     */
    public Scene createScene() {
        return scene;
    }


    /**
     * changes the exact cell in the field according to the player that made a move
     *
     * @param i             row number
     * @param j             column number
     * @param currentPlayer player that makes a move
     */
    public void draw(int i, int j, int currentPlayer) {
        if (currentPlayer == 1) {
            cells[i][j].setText("X");
        } else {
            cells[i][j].setText("O");
        }
    }

    /**
     * makes all the cells empty
     */
    public void redraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].setText("");
            }
        }
    }

}
