package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.MainApplication;
import com.panko.astronomy_picture_of_the_day.entity.Picture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class PictureDescriptionController {

    @FXML
    private Text pictureDescription;

    public static final String PICTURE_DESCRIPTION_SCENE_PATH =
            "/com/panko/astronomy_picture_of_the_day/picture-description-scene.fxml";

    public void loadPictureDescriptionScene(BorderPane rootContainer, Picture picture) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(PICTURE_DESCRIPTION_SCENE_PATH));
            Pane vboxContainer = loader.load();

            pictureDescription.setText(picture.getDescription());

//            rootContainer.setCenter(vboxContainer);

            Text text = new Text();
            text.setText(picture.getDescription());

            VBox vBox = new VBox();
            vBox.getChildren().add(text);

//            rootContainer.setCenter(vboxContainer);
            rootContainer.setBottom(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
