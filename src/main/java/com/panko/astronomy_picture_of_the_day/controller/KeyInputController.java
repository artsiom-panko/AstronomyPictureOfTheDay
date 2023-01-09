package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.MainApplication;
import com.panko.astronomy_picture_of_the_day.service.ApiKeyService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

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

    public void loadKeyInputScene(Pane pane) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(KEY_INPUT_SCENE_PATH));
            VBox personOverview = (VBox) loader.load();

            pane.getChildren().add(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getApiKeyValue(String apiKeyName) {
        return apiKeyService.readKey(apiKeyName);
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
