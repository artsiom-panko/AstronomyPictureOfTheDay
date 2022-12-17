package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.entity.Picture;
import com.panko.astronomy_picture_of_the_day.service.MainService;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MainController {
    MainService mainService = new MainService();

    @FXML
    private Text welcomeText;

    public void getImage() {
        Picture picture = mainService.process();
        welcomeText.setText(picture.getDescription());
    }

    @FXML
    public void initialize() {
        Picture picture = mainService.process();
        welcomeText.setText(picture.getDescription());
    }
}