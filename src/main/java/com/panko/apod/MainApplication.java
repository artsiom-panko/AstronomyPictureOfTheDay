package com.panko.apod;

import com.panko.apod.controller.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/root-scene.fxml"));
        Parent root = loader.load();

        RootController rootController = loader.getController();
        rootController.setStage(primaryStage);

        primaryStage.setTitle("Astronomy picture of the day");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png"))));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        rootController.process();
    }
}