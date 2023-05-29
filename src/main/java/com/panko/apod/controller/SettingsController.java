package com.panko.apod.controller;

import com.panko.apod.MainApplication;
import com.panko.apod.entity.AppScene;
import com.panko.apod.entity.Controller;
import com.panko.apod.entity.ControllerInterface;
import com.panko.apod.service.SceneService;
import com.panko.apod.service.SceneService2;
import com.panko.apod.util.PreferencesManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

import static com.panko.apod.util.PreferencesManager.NASA_API_KEY;
import static com.panko.apod.util.PreferencesManager.PICTURES_FOLDER;

public class SettingsController extends Controller implements Initializable, ControllerInterface {

    @FXML
    private TextField newApiKey;
    @FXML
    private ToggleGroup languageGroup;

    private SceneService2 sceneService2;

    public void setSceneService2(SceneService2 sceneService2) {
        this.sceneService2 = sceneService2;
    }

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
    private void saveNewApiKey() {
        Path applicationAbsolutePath = FileSystems.getDefault().getPath("").toAbsolutePath();
        String folderWithPicturesPath = applicationAbsolutePath.toString().concat("\\pictures.jpg\\");

        preferencesManager.saveKey(NASA_API_KEY, newApiKey.getText());
        preferencesManager.saveKey(PICTURES_FOLDER, folderWithPicturesPath);
//        preferencesManager.saveKey(LANGUAGE, ((RadioButton) languageGroup.getSelectedToggle()).getText());

        sceneService2.launchMainThread();
    }
}
