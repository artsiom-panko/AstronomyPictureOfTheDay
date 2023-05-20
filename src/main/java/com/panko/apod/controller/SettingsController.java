package com.panko.apod.controller;

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
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

import static com.panko.apod.util.PreferencesManager.*;

public class SettingsController implements Initializable {

    @FXML
    private TextField newApiKey;
    @FXML
    private ToggleGroup languageGroup;

    private Stage primaryStage;
    private MainController mainController;

    private final PreferencesManager preferencesManager = new PreferencesManager();

    public void setRootController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setRootStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

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

        mainController.launchMainThread();
    }
}
