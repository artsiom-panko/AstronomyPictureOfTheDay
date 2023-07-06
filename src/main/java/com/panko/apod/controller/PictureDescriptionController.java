package com.panko.apod.controller;

import com.panko.apod.entity.Picture;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class PictureDescriptionController {

    @FXML
    private Text pictureTitle;
    @FXML
    private TextArea pictureDescription;

    public void showPictureDescription(Picture picture) {
        pictureTitle.setText(picture.getTitle());
        pictureDescription.setText(picture
                .getDescription()
                .concat("\n")
                .concat(generateCopyrightText(picture)));
    }

    private String generateCopyrightText(Picture picture) {
        return String.format("Copyright © %d by %s", picture.getDate().getYear(), picture.getCopyright());
    }
}
