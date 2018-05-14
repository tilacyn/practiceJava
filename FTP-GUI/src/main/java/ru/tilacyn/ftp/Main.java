package ru.tilacyn.ftp;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parameters parameters = getParameters();
        String host = parameters.getRaw().get(0);
        int port = Integer.parseInt(parameters.getRaw().get(1));

        Controller controller = new Controller(host, port);
        MainLayout mainLayout = new MainLayout(controller);

        controller.setMainLayout(mainLayout);

        primaryStage.setTitle("FTP-GUI");
        primaryStage.setScene(mainLayout.createScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
