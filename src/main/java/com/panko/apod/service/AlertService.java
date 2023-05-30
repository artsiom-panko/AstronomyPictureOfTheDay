package com.panko.apod.service;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.Objects;

public class AlertService {

    public void showAboutAlert() {
        Alert alert = createAlert(Alert.AlertType.INFORMATION, "About Astronomy picture of the day");

//        Pane aboutScene = sceneService.loadScene(SCENE_ABOUT);
        Pane aboutScene = null;

        alert.getDialogPane().setContent(aboutScene);
        alert.showAndWait();
    }

    public void showErrorAlertAndCloseApp(String errorMessage) {
        Platform.runLater(() -> {
            Alert alert = createAlert(Alert.AlertType.ERROR, "Error");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

            alert.setHeaderText("Critical error");

            alert.getDialogPane().setContentText(errorMessage);
            alert.showAndWait();
            Platform.exit();
        });
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
