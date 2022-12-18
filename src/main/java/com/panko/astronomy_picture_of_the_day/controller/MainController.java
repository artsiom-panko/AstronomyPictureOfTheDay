package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.entity.Picture;
import com.panko.astronomy_picture_of_the_day.service.MainService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MainController {
    MainService mainService = new MainService();

    @FXML
    private TextArea imageDescription;

    @FXML
    public void initialize() {
        Picture picture = mainService.process();
        imageDescription.setText(picture.getDescription());
    }

//    @FXML
//    public void initialize() {
//        imageDescription.setText("Returning from beyond the Moon, on December 11 the Orion spacecraft entered Earth's atmosphere at almost 11 kilometers per second. That's half the speed of the grain of dust that created this long fireball meteor when it entered the atmosphere on December 13, near the peak of the annual Geminid meteor shower. As our fair planet makes its yearly pass through the dust trail of mysterious asteroid 3200 Phaethon, the parallel tracks of all Geminid meteors appear to radiate from a point in the constellation Gemini. But the twin stars of Gemini hide just behind the trees on the left in this night skyscape from the beautiful Blue Moon Valley, Yunnan, China. Reflected in the still waters of the mountain lake, stars of the constellation Orion are rising near center. Captured before moonrise, dazzling Mars is still the brightest celestial beacon in the scene. Returning from beyond the Moon, on December 11 the Orion spacecraft entered Earth's atmosphere at almost 11 kilometers per second. That's half the speed of the grain of dust that created this long fireball meteor when it entered the atmosphere on December 13, near the peak of the annual Geminid meteor shower. As our fair planet makes its yearly pass through the dust trail of mysterious asteroid 3200 Phaethon, the parallel tracks of all Geminid meteors appear to radiate from a point in the constellation Gemini. But the twin stars of Gemini hide just behind the trees on the left in this night skyscape from the beautiful Blue Moon Valley, Yunnan, China. Reflected in the still waters of the mountain lake, stars of the constellation Orion are rising near center. Captured before moonrise, dazzling Mars is still the brightest celestial beacon in the scene.");
//    }
}