package com.panko.apod.controller;

import com.panko.apod.service.MainService;
import com.panko.apod.service.SceneService;
import com.panko.apod.service.SettingsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private TextField apiKeyField;

    private SceneService sceneService;

    private final MainService mainService = new MainService();
    private final SettingsService settingsService = new SettingsService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingsService.initializeSettingsScene(apiKeyField);
    }

    @FXML
    private void openHyperlink(ActionEvent event) throws URISyntaxException, IOException {
        mainService.openHyperlinkInBrowser(event);
    }

    @FXML
    private void saveSettings() {
        if (settingsService.saveSettings(apiKeyField)) {
            sceneService.launchMainThread();
        } else {
            sceneService.showSettingsScene();
        }
    }

    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }
}
