package com.panko.apod;

import com.panko.apod.service.SceneService;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private final SceneService sceneService = new SceneService();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        sceneService.makeInitialSetupAndLaunch(primaryStage);
    }
}
