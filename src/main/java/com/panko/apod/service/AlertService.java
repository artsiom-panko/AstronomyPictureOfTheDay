package com.panko.apod.service;

import com.panko.apod.entity.AppScene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.Objects;

public class AlertService {

    private final SceneService2 sceneService2 = new SceneService2();

    // TODO Remove 'Message' text from the alert
    public void showAboutAlert() {
        Alert alert = createAlert(Alert.AlertType.INFORMATION, "About Astronomy picture of the day");

        AppScene appScene = sceneService2.getAppScene(null);

        alert.getDialogPane().setContent(appScene.getPane());
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
