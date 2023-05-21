package com.panko.apod.service;

import com.panko.apod.MainApplication;
import com.panko.apod.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneService {

    public static final String SCENE_ROOT = "/scene/root-scene.fxml";
    public static final String SCENE_ABOUT = "/scene/about.fxml";
    public static final String SCENE_LOADING = "/scene/loading-scene.fxml";
    public static final String SCENE_SETTINGS = "/scene/settings-scene.fxml";
    public static final String SCENE_DESCRIPTION = "/scene/picture-description-scene.fxml";

    private Stage mainStage;
    private static final System.Logger logger = System.getLogger(SceneService.class.getName());

//    public SceneService(Stage mainStage) {
//        this.mainStage = mainStage;
//    }

    public void startMainScene(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SCENE_ROOT));
        Parent rootScene = loader.load();

        mainStage = primaryStage;
        mainStage.setTitle("Astronomy picture of the day");
        mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png"))));
        mainStage.setScene(new Scene(rootScene));
        mainStage.show();

        MainController mainController = loader.getController();
        mainController.launchMainThread();
    }

    public void showScene(String sceneName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
        Pane container = loadScene(sceneName);

//        SettingsController settingsController = loader.getController();
//        settingsController.setRootController(this);
//        settingsController.setRootStage(primaryStage);

//        mainPane.setCenter(container);
    }

    public Pane loadScene(String sceneName) {
        logger.log(System.Logger.Level.INFO, "Start loading scene: {0}", sceneName);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(sceneName));
        try {
            return loader.load();
        } catch (IOException e) {
            // TODO what to do?
//            showAlertAndCloseApp();
            throw new RuntimeException();
        }
    }
}
