package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.util.ApplicationPropertiesManager;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

import static com.panko.astronomy_picture_of_the_day.util.ApplicationPropertiesManager.*;

public class KeyInputController {

    @FXML
    private TextField newApiKey;
    @FXML
    private TextField selectedFolderDirectory;
    @FXML
    private ToggleGroup languageGroup;

    private Stage primaryStage;
    private RootController rootController;

    private final ApplicationPropertiesManager applicationPropertiesManager = new ApplicationPropertiesManager();

    public static final String KEY_INPUT_SCENE_PATH =
            "/com/panko/astronomy_picture_of_the_day/scene/key-input-scene.fxml";

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

    public void setRootStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void saveNewApiKey() {
        boolean wasKeySavedSuccessfully = applicationPropertiesManager.saveKey(NASA_API_KEY, newApiKey.getText());
        applicationPropertiesManager.saveKey(PICTURES_FOLDER, selectedFolderDirectory.getText());
        applicationPropertiesManager.saveKey(LANGUAGE, ((RadioButton) languageGroup.getSelectedToggle()).getText());

        if (wasKeySavedSuccessfully) {
            rootController.process();
        }
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
