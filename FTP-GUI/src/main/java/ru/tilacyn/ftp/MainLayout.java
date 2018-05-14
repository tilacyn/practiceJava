package ru.tilacyn.ftp;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.ArrayList;

public class MainLayout {
    private Scene scene;
    private FlowPane flowPane;
    private Controller controller;

    public MainLayout(Controller controller) throws IOException {
        this.controller = controller;
        draw();
        flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);
        scene = new Scene(flowPane, 300, 300);
    }

    public Scene createScene() {
        return scene;
    }

    private void draw() throws IOException {
        ArrayList<Client.File> files = controller.list();
        for (final Client.File file : files) {
            Button b = new Button(file.getName());
            b.setOnAction(value -> {
                if (file.isDir()) {
                    controller.setFileName(file.getName());
                    try {
                        draw();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        System.out.println(controller.get(file.getName()).getName());
                        System.out.println(controller.get(file.getName()).getContent().length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            flowPane.getChildren().add(b);
        }
    }

}
