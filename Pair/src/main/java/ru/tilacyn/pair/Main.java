package ru.tilacyn.pair;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    private static int n;


    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("Bad input!");
            return;
        }

        n = Integer.parseInt(args[0]);


        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        Controller controller = new Controller(n);
        GameLayout gameLayout = new GameLayout(controller);
        Logic logic = new Logic(controller);


        controller.setGameLayout(gameLayout);
        controller.setLogic(logic);

        primaryStage.setScene(gameLayout.createScene());

        primaryStage.setTitle("Pairs");
        primaryStage.show();
    }
}
