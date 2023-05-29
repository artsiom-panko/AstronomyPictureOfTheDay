package com.panko.apod.entity;

import com.panko.apod.service.SceneService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public enum AppScene {
    ROOT ("/scene/root-scene.fxml"),
    ABOUT ("/scene/about.fxml"),
    LOADING ("/scene/loading-scene.fxml"),
    SETTINGS ("/scene/settings-scene.fxml"),
    DESCRIPTION ("/scene/picture-description-scene.fxml");

    private Pane pane;
    private String path;

    private Controller controller;
    private FXMLLoader fxmlLoader;
    private SceneService sceneService;


    public SceneService getSceneService() {
        return sceneService;
    }

    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    AppScene(String path) {
        this.path = path;
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public void setFxmlLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public Pane getPane() {
        return pane;
    }

    public String getPath() {
        return path;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
