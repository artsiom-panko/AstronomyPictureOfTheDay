package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.service.ApiKeyService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class KeyInputController {

    @FXML
    private TextField newApiKey;

    private RootController rootController;
    private final ApiKeyService apiKeyService = new ApiKeyService();

    public static final String KEY_INPUT_SCENE_PATH =
            "/com/panko/astronomy_picture_of_the_day/key-input-scene.fxml";

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void saveNewApiKey() {
        boolean wasKeySavedSuccessfully = apiKeyService.saveKey("nasa.api.key", newApiKey.getText());

        if (wasKeySavedSuccessfully) {
            rootController.process();
        }
    }
}
