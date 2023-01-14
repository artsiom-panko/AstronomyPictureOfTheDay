package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.util.ApplicationPropertiesManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class KeyInputController {

    @FXML
    private TextField newApiKey;
    @FXML
    private TextArea selectedFolderDirectory;
    @FXML
    private ToggleGroup language_group;

    private Stage rootStage;
    private RootController rootController;

    private final ApplicationPropertiesManager applicationPropertiesManager = new ApplicationPropertiesManager();

    public static final String KEY_INPUT_SCENE_PATH =
            "/com/panko/astronomy_picture_of_the_day/scene/key-input-scene.fxml";

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

    public void setRootStage(Stage rootStage) {
        this.rootStage = rootStage;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void saveNewApiKey() {
        boolean wasKeySavedSuccessfully = applicationPropertiesManager.saveKey("nasa.api.key", newApiKey.getText());
        applicationPropertiesManager.saveKey("pictures.folder", selectedFolderDirectory.getText());
        applicationPropertiesManager.saveKey("language", ((RadioButton) language_group.getSelectedToggle()).getText());

        if (wasKeySavedSuccessfully) {
            rootController.process();
        }
    }

    @FXML
    private void selectFolderDirectory() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Some Directories");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File dir = directoryChooser.showDialog(rootStage);
        if (dir != null) {
            selectedFolderDirectory.setText(dir.getAbsolutePath());
        } else {
            selectedFolderDirectory.setText(null);
        }
    }
}
