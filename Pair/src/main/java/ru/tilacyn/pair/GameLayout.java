package ru.tilacyn.pair;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

public class GameLayout {
    private Controller controller;

    private BorderPane bp = new BorderPane();
    private GridPane grid = new GridPane();
    private Scene scene = new Scene(bp, 300, 300);
    private int n;
    private Button[][] cells;


    {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                final int ii = i;
                final int jj = j;
                cells[i][j] = new Button("wow");
                cells[i][j].setOnAction(value -> controller.press(ii, jj));
                grid.add(cells[i][j], i, j);
            }
        }

        bp.setCenter(grid);
    }

    public GameLayout(@NotNull Controller controller) {
        this.controller = controller;
        n = controller.getN();
        cells = new Button[n][n];
    }

    public void success(int i1, int j1, int i2, int j2) {
        cells[i1][j1].setText("u");
        cells[i2][j2].setText("u");
    }

    public void set(int i, int j) {
        cells[i][j].setText("a");
    }

    public void reset(int i, int j) {
        cells[i][j].setText("");
    }


    /**
     * returns a scene with game layouts
     *
     * @return a scene with game layouts
     */
    public @NotNull Scene createScene() {
        return scene;
    }

}
