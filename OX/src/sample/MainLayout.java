package sample;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

/**
 * class that contains Layouts that refer to the main scene
 *
 */
public class MainLayout {
    private FlowPane fp = new FlowPane();
    private Controller controller;
    private Button newGame = new Button("New Game");
    private Button continueGame = new Button("Continue");
    private Button playBot = new Button("Play with Bot");
    private Text statistics = new Text("Statistics will be shown here");
    private Scene scene = new Scene(fp, 400, 375);

    /**
     * set all the dependencies and parameters
     */
    {
        newGame.setOnAction(value -> controller.start());
        continueGame.setOnAction(value -> controller.continueGame());
        playBot.setOnAction(value -> controller.startWithBot());

        fp.setOrientation(Orientation.VERTICAL);
        fp.getChildren().add(newGame);
        fp.getChildren().add(continueGame);
        fp.getChildren().add(playBot);
        fp.getChildren().add(statistics);

        FlowPane.setMargin(newGame, new Insets(50.0, 50.0, 50.0, 120.0));
        FlowPane.setMargin(continueGame, new Insets(8.0, 50.0, 50.0, 120.0));
        FlowPane.setMargin(playBot, new Insets(8.0, 0.0, 0.0, 120.0));
        FlowPane.setMargin(statistics, new Insets(10.0, 50.0, 50.0, 50.0));
    }

    /**
     * returns a scene with main layouts
     * @return a scene with main layouts
     */
    public Scene createScene() {
        return scene;
    }


    /**
     * creates a new MainLayout class bound to the specified controller
     * @param controller specified Controller
     */
    public MainLayout(Controller controller) {
        this.controller = controller;
    }

    /**
     * changes statistics that is shown on the main scene
     */
    public void addStatistics() {
        statistics.setText("X won in " + controller.getXWins() + " games, " +
        "O won in " + controller.getOWins() + " games, Draws in " + controller.getDraws() + " games");
    }
}
