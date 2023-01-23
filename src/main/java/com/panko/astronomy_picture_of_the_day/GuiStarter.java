package com.panko.astronomy_picture_of_the_day;

import com.panko.astronomy_picture_of_the_day.controller.RootController;
import javafx.application.Application;
import javafx.stage.Stage;

// This class is necessary for avoiding "Error: JavaFX runtime components are missing,
// and are required to run this application".
// The trick is to use an extra starter class that does not inherit from javafx.application.Application
public class GuiStarter extends Application {
    RootController rootController = new RootController();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        rootController.initRootScene(primaryStage);
        rootController.process();
    }
}
