package com.panko.apod.controller;

import com.panko.apod.entity.SceneController;
import com.panko.apod.service.AlertService;
import com.panko.apod.service.ApiService;
import com.panko.apod.service.SceneService;
import com.panko.apod.util.PreferencesManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

import static com.panko.apod.util.PreferencesManager.*;

public class SettingsController implements Initializable, SceneController {

    @FXML
    private TextField newApiKey;
    @FXML
    private ToggleGroup languageGroup;

    private Stage primaryStage;
    private SceneService sceneService;

    private final PreferencesManager preferencesManager = new PreferencesManager();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String key = preferencesManager.readKey(NASA_API_KEY);

        if (key != null && !key.isEmpty()) {
            newApiKey.setText(key);
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
        String enteredApiKey = newApiKey.getText();

        if (!enteredApiKey.isBlank() && isApiKeyValid(enteredApiKey)) {
            preferencesManager.saveKey(PICTURES_FOLDER, picturesPath);
            preferencesManager.saveKey(NASA_API_KEY, newApiKey.getText());
//        preferencesManager.saveKey(LANGUAGE, ((RadioButton) languageGroup.getSelectedToggle()).getText());

            sceneService.launchMainThread();
        } else {
            new AlertService().showWarningAlert("Invalid API key",
                    "Provided API key is wrong or disabled. Please, double-check entered API key or generate a new one.");

            sceneService.showScene(SceneService.SCENE_SETTINGS);
        }
    }

    @Override
    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    private boolean isApiKeyValid(String apiKey) {
        ApiService apiService = new ApiService();
        HttpResponse<String> httpResponse = apiService.sendHttpRequest(apiKey);

        return !httpResponse.body().contains("API_KEY_INVALID");
    }
}
