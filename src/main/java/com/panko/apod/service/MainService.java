package com.panko.apod.service;

import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainService {

    public void openHyperlinkInBrowser(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(((Hyperlink) event.getTarget()).getText()));
    }
}
