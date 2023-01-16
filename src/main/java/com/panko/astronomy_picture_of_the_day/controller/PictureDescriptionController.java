package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.entity.Picture;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.Date;

public class PictureDescriptionController {

    @FXML
    private Text pictureTitle;
    @FXML
    private TextArea pictureDescription;

    public static final String PICTURE_DESCRIPTION_SCENE_PATH =
            "/com/panko/astronomy_picture_of_the_day/scene/picture-description-scene.fxml";

    public void showPictureDescription(Picture picture) {
        pictureTitle.setText(picture.getTitle());
        pictureDescription.setText(picture.getDescription().concat("\n").concat(generateCopyrightText(picture)));
    }

    private String generateCopyrightText(Picture picture) {
        return String.format("Copyright © %d by %s", picture.getDate().getYear(), picture.getCopyright());
    }
}
