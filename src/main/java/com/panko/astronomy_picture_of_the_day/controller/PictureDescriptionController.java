package com.panko.astronomy_picture_of_the_day.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class PictureDescriptionController {

    @FXML
    private TextArea pictureDescription;

    public static final String PICTURE_DESCRIPTION_SCENE_PATH =
            "/com/panko/astronomy_picture_of_the_day/scene/picture-description-scene.fxml";

    public void showPictureDescription(String description) {
        pictureDescription.setText(description);
    }
}
