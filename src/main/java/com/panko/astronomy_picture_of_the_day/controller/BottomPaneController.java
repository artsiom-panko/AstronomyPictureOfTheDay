package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BottomPaneController {

    @FXML
    private void showAboutPage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Astronomy picture of the day");

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/panko/astronomy_picture_of_the_day/img/logo.png")));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(logo);

        alert.setHeaderText(null);
        alert.setGraphic(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource("/com/panko/astronomy_picture_of_the_day/scene/about.fxml"));
        Pane aboutScene = null;
        try {
            aboutScene = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        alert.getDialogPane().setContent(aboutScene);

        alert.showAndWait();
    }

    @FXML
    private void showSettingsPage() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource("/com/panko/astronomy_picture_of_the_day/scene/picture-description-scene.fxml"));
        Pane descriptionScene = null;
        try {
            descriptionScene = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
