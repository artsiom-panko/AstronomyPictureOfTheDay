package com.panko.apod.controller;

import com.panko.apod.service.AlertService;
import com.panko.apod.service.HttpRequestService;
import com.panko.apod.service.SceneService;
import com.panko.apod.util.PreferencesManager;
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
import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

import static com.panko.apod.util.PreferencesManager.NASA_API_KEY;
import static com.panko.apod.util.PreferencesManager.APP_ABSOLUTE_PATH;

public class SettingsController implements Initializable {
    @FXML
    private TextField apiKeyField;

    private SceneService sceneService;

    private final PreferencesManager preferencesManager = new PreferencesManager();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String key = preferencesManager.readKey(NASA_API_KEY);

        if (key != null && !key.isEmpty()) {
            apiKeyField.setText(key);
        }
    }

    @FXML
    private void openHyperlink(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(((Hyperlink) event.getTarget()).getText()));
    }

    @FXML
    private void saveSettings() {
        Path applicationAbsolutePath = FileSystems.getDefault().getPath("").toAbsolutePath();
        String picturesPath = applicationAbsolutePath.toString().concat("\\pictures\\");

        String apiKey = apiKeyField.getText();

        if (isApiKeyValid(apiKey)) {
            preferencesManager.saveKey(APP_ABSOLUTE_PATH, picturesPath);
            preferencesManager.saveKey(NASA_API_KEY, apiKey);

            sceneService.launchMainThread();
        } else {
            new AlertService().showWarningAlert("Invalid API key",
                    "Provided API key is wrong or disabled. Please, double-check entered API key or generate a new one.");

//            sceneService.showScene(SceneService.SCENE_SETTINGS);
            sceneService.showSettingsScene();
        }
    }

    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    private boolean isApiKeyValid(String apiKeyToCheck) {
        if (apiKeyToCheck.isBlank()) {
            return false;
        }

        if (apiKeyToCheck.equals(preferencesManager.readKey(NASA_API_KEY))) {
            return true;
        }

        HttpResponse<String> httpResponse = new HttpRequestService().sendHttpGetRequestToNasa(apiKeyToCheck);

        return !httpResponse.body().contains("API_KEY_INVALID");
    }
}
