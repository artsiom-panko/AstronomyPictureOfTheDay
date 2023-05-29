package com.panko.apod.service;

import com.panko.apod.MainApplication;
import com.panko.apod.controller.MainController;
import com.panko.apod.entity.SceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneService {

    public static final String SCENE_ABOUT = "/scene/about.fxml";
    public static final String SCENE_ROOT = "/scene/root-scene.fxml";
    public static final String SCENE_LOADING = "/scene/loading-scene.fxml";
    public static final String SCENE_SETTINGS = "/scene/settings-scene.fxml";
    public static final String SCENE_DESCRIPTION = "/scene/picture-description-scene.fxml";

    private BorderPane mainPane;
    private MainController mainController;

    public void makeInitialSetupAndLaunch(Stage primaryStage) {
//        AppScene appScene = getAppScene(ROOT);
//
//        mainPane = (BorderPane) appScene.getPane();
//        MainController mainController = (MainController) appScene.getController();
//
////        mainController.setStage(primaryStage);
//        mainController.setSceneService(this);

        //
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(SCENE_ROOT));
        try {
            mainPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainController = loader.getController();
        mainController.setSceneService(this);
        //

        primaryStage.setTitle("Astronomy picture of the day");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(SceneService.class.getResourceAsStream("/img/logo.png"))));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();

        launchMainThread();
    }

    public void launchMainThread() {
        mainController.launchMainThread();
    }

    public void showScene(String scenePath) {
        VBox vBox;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(scenePath));

        try {
            vBox = loader.load();
            SceneController controller = loader.getController();

            if (controller != null) {
                controller.setSceneService(this);
            }

        } catch (IOException e) {
            throw new RuntimeException();
        }

        mainPane.setCenter(vBox);
    }
}
