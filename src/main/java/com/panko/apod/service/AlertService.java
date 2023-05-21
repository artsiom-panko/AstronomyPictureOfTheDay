package com.panko.apod.service;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.Objects;

import static com.panko.apod.service.SceneService.SCENE_ABOUT;

public class AlertService {

    private final SceneService sceneService = new SceneService();

    public void showAboutAlert() {
        Alert alert = createAlert(Alert.AlertType.INFORMATION, "About Astronomy picture of the day");

        Pane aboutScene = sceneService.loadScene(SCENE_ABOUT);

        alert.getDialogPane().setContent(aboutScene);
        alert.showAndWait();
    }

    public void showErrorAlert(String errorMessage) {
        Alert alert = createAlert(Alert.AlertType.ERROR, "Error");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        alert.setHeaderText("Error during image loading");

        alert.getDialogPane().setContentText(errorMessage);
        alert.showAndWait();
    }

    public Alert createAlert(Alert.AlertType alertType, String alertTitle) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(logo);

        return alert;
    }
}
