package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.util.PreferencesManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static com.panko.astronomy_picture_of_the_day.util.PreferencesManager.*;

public class SettingsController implements Initializable {

    @FXML
    private TextField newApiKey;
    @FXML
    private TextField selectedFolderDirectory;
    @FXML
    private ToggleGroup languageGroup;

    private Stage primaryStage;
    private RootController rootController;

    private final PreferencesManager preferencesManager = new PreferencesManager();

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

    public void setRootStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String key = preferencesManager.readKey(NASA_API_KEY);
        String folder = preferencesManager.readKey(PICTURES_FOLDER);

        if (key != null && !key.isEmpty()) {
            newApiKey.setText(key);
        }

        if (folder != null && !folder.isEmpty()) {
            selectedFolderDirectory.setText(folder);
        }
    }

    @FXML
    private void saveNewApiKey() {
        preferencesManager.saveKey(NASA_API_KEY, newApiKey.getText());
        preferencesManager.saveKey(PICTURES_FOLDER, selectedFolderDirectory.getText());
//        preferencesManager.saveKey(LANGUAGE, ((RadioButton) languageGroup.getSelectedToggle()).getText());

        rootController.process();
    }

    @FXML
    private void selectFolderDirectory() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Some Directories");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File dir = directoryChooser.showDialog(primaryStage);
        if (dir != null) {
            selectedFolderDirectory.setText(dir.getAbsolutePath());
        } else {
            selectedFolderDirectory.setText(null);
        }
    }
}
