package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.entity.Picture;
import com.panko.astronomy_picture_of_the_day.service.MainService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    MainService mainService = new MainService();

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        Picture picture = mainService.process();
        welcomeText.setText(picture.getDescription());
    }
}