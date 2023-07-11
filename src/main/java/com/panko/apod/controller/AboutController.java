package com.panko.apod.controller;

import com.panko.apod.service.UpdateCheckService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    @FXML
    private Text versionValue;

    @FXML
    private void openHyperlink(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(((Hyperlink) event.getTarget()).getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Double currentAppVersion = new UpdateCheckService().getCurrentAppVersion();

        if (currentAppVersion != null) {
            versionValue.setText("Version: " + currentAppVersion);
        }
    }
}
