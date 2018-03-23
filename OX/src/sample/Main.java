package sample;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * main class that starts the application and switches between two main scenes:
 * gameLayout scene and mainLayout scene
 */
public class Main extends Application {
    private Controller controller = new Controller();
    private GameLayout gameLayout = new GameLayout(controller);
    private MainLayout mainLayout = new MainLayout(controller);
    private Logic logic = new Logic(controller);

    private Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        controller.setGameLayout(gameLayout);
        controller.setMainLayout(mainLayout);
        controller.setMain(this);
        controller.setLogic(logic);

        backToMain();

        primaryStage.show();
    }

    /**
     * sets Game Layout Scene to the main screen
     */
    public void startGame() {
        primaryStage.setTitle("Field");
        primaryStage.setScene(gameLayout.createScene());
    }

    /**
     * sets Main Layout Scene to the main screen
     */
    public void backToMain() {
        primaryStage.setTitle("Main");
        primaryStage.setScene(mainLayout.createScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
