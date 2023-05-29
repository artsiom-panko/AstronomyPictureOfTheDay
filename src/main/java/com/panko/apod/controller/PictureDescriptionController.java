package com.panko.apod.controller;

import com.panko.apod.entity.Picture;
import com.panko.apod.entity.SceneController;
import com.panko.apod.service.SceneService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class PictureDescriptionController implements SceneController {

    @FXML
    private Text pictureTitle;
    @FXML
    private TextArea pictureDescription;

    private SceneService sceneService;

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

    @Override
    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }
}
