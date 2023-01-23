package com.panko.astronomy_picture_of_the_day.service;

import com.panko.astronomy_picture_of_the_day.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class SceneLoaderService {

    public enum Scene {
        DESCRIPTION("/scene/picture-description-scene.fxml");

        public final String path;

        Scene(String path) {
            this.path = path;
        }
    }

    public final String DESCRIPTION = "/scene/picture-description-scene.fxml";

    private final Scene main;
    private static final HashMap<String, Pane> screenMap = new HashMap<>();

    public SceneLoaderService(Scene main) {
        this.main = main;
    }

    public <T> T getSceneController(Scene sceneName) {
        Pane pane = loadScene(sceneName);
        return null;
    }

    public Pane loadScene(Scene sceneName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(sceneName.path));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    public static void removeScreen(String name) {
        screenMap.remove(name);
    }
}
