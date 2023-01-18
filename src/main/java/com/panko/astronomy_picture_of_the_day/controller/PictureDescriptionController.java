package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.MainApplication;
import com.panko.astronomy_picture_of_the_day.entity.Picture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PictureDescriptionController {

    @FXML
    private Text pictureTitle;
    @FXML
    private TextArea pictureDescription;

    private BorderPane primaryContainer;

    public void setPrimaryContainer(BorderPane container) {
        this.primaryContainer = container;
    }

    public static final String PICTURE_DESCRIPTION_SCENE_PATH =
            "/com/panko/astronomy_picture_of_the_day/scene/picture-description-scene.fxml";

    public void showPictureDescription(Picture picture) {
        pictureTitle.setText(picture.getTitle());
        pictureDescription.setText(picture.getDescription().concat("\n").concat(generateCopyrightText(picture)));
    }

    private String generateCopyrightText(Picture picture) {
        return String.format("Copyright © %d by %s", picture.getDate().getYear(), picture.getCopyright());
    }

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
            primaryContainer.setCenter(descriptionScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
