package com.panko.astronomy_picture_of_the_day.service;

import com.panko.astronomy_picture_of_the_day.MainApplication;
import com.panko.astronomy_picture_of_the_day.controller.PictureDescriptionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class SceneLoaderService {

    public enum Scene {
        DESCRIPTION("/com/panko/astronomy_picture_of_the_day/scene/picture-description-scene.fxml");

        public final String path;

        Scene(String path) {
            this.path = path;
        }
    }

    public final String DESCRIPTION = "/com/panko/astronomy_picture_of_the_day/scene/picture-description-scene.fxml";

    private final Scene main;
    private static final HashMap<String, Pane> screenMap = new HashMap<>();

    public SceneLoaderService(Scene main) {
        this.main = main;
    }

    public <T> T getSceneController(Scene sceneName) {
        loadScene(sceneName);
        return this.controller;
    }

    public void loadScene(Scene sceneName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(sceneName.path));
            Pane vboxContainer = loader.load();

            PictureDescriptionController pictureDescriptionController = loader.getController();
            pictureDescriptionController.showPictureDescription(picture);

            rootContainer.setCenter(vboxContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    public static void removeScreen(String name) {
        screenMap.remove(name);
    }

    protected void activate(String name) {
        main.setRoot(screenMap.get(name));
    }
}
