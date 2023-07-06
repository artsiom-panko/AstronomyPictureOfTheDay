package com.panko.apod.service;

import com.panko.apod.MainApplication;
import com.panko.apod.controller.MainController;
import com.panko.apod.controller.PictureDescriptionController;
import com.panko.apod.controller.SettingsController;
import com.panko.apod.entity.Picture;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

// TODO refactor this class, should be min number of .load calls
public class SceneService {
    public static final String SCENE_ABOUT = "/scene/about.fxml";
    public static final String SCENE_ROOT = "/scene/root-scene.fxml";
    public static final String SCENE_LOADING = "/scene/loading-scene.fxml";
    public static final String SCENE_SETTINGS = "/scene/settings-scene.fxml";
    public static final String SCENE_DESCRIPTION = "/scene/picture-description-scene.fxml";

    private BorderPane mainPane;
    private MainController mainController;

    public void launchMainThread() {
        mainController.launchMainThread();
    }

    public void makeInitialSetupAndLaunch(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(SCENE_ROOT));
        try {
            mainPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainController = loader.getController();
        mainController.setSceneService(this);

        primaryStage.setTitle("Astronomy picture of the day");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(SceneService.class.getResourceAsStream("/img/logo.png"))));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();

        launchMainThread();
    }

    public Pane getScenePane(String scenePath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(scenePath));

        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void showScene(String scenePath) {
        VBox vBox;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(scenePath));

        try {
            vBox = loader.load();
//            SceneController controller = loader.getController();
//
//            if (controller != null) {
//                controller.setSceneService(this);
//            }

        } catch (IOException e) {
            throw new RuntimeException();
        }

        mainPane.setCenter(vBox);
    }

    // TODO get rid of this
    public void showPictureDescriptionScene(Picture picture) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(SceneService.SCENE_DESCRIPTION));
            Pane vboxContainer = loader.load();

            PictureDescriptionController pictureDescriptionController = loader.getController();
            pictureDescriptionController.showPictureDescription(picture);

            mainPane.setCenter(vboxContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSettingsScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(SceneService.SCENE_SETTINGS));
            Pane vboxContainer = loader.load();

            SettingsController settingsController = loader.getController();
            settingsController.setSceneService(this);

            mainPane.setCenter(vboxContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
