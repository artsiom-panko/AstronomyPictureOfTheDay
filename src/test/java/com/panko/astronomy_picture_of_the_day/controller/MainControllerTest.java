package com.panko.astronomy_picture_of_the_day.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;
import java.util.Objects;

class MainControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {
        try {
            return FXMLLoader.load(Objects.requireNonNull(getClass().getResource("src/main/resources/com/panko/astronomy_picture_of_the_day/main-scene.fxml")));
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    @Test
    public void initialize() {
        TextField firstname = find("#imageDescription");
        firstname.setText("123");
    }
}