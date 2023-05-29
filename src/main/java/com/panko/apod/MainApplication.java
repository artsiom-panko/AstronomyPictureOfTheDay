package com.panko.apod;

import com.panko.apod.service.SceneService;
import com.panko.apod.service.SceneService2;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private final SceneService2 sceneService2 = new SceneService2();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        sceneService2.makeInitialSetupAndLaunch(primaryStage);
    }
}
