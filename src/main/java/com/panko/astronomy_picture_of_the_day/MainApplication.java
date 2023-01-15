package com.panko.astronomy_picture_of_the_day;

import com.panko.astronomy_picture_of_the_day.controller.RootController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    public static final String API_KEY_INPUT_SCENE = "api-key-input-scene";
    public static final String IMAGE_DESCRIPTION_SCENE = "image-description-scene";

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
