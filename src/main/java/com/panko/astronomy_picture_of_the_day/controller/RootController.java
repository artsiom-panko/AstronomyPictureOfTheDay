package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.MainApplication;
import com.panko.astronomy_picture_of_the_day.entity.Picture;
import com.panko.astronomy_picture_of_the_day.util.ApplicationPropertiesManager;
import com.panko.astronomy_picture_of_the_day.service.ApiService;
import com.panko.astronomy_picture_of_the_day.service.HttpResponseHandlerService;
import com.panko.astronomy_picture_of_the_day.util.ImageSaver;
import com.panko.astronomy_picture_of_the_day.util.WallpaperChanger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.Thread;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.panko.astronomy_picture_of_the_day.controller.KeyInputController.KEY_INPUT_SCENE_PATH;
import static com.panko.astronomy_picture_of_the_day.controller.PictureDescriptionController.PICTURE_DESCRIPTION_SCENE_PATH;
import static com.panko.astronomy_picture_of_the_day.service.MainService.NASA_API_KEY;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 */
public class RootController {

    private Stage primaryStage;
    private BorderPane rootContainer;

    private final ImageSaver imageSaver = new ImageSaver();

    private final ApiService apiService = new ApiService();
    private final ApplicationPropertiesManager applicationPropertiesManager = new ApplicationPropertiesManager();
    private final HttpResponseHandlerService httpResponseHandlerService = new HttpResponseHandlerService();

    public Stage getRootStage() {
        return primaryStage;
    }

    public Pane getRootContainer() {
        return rootContainer;
    }

    private Service<Void> backgroundThread;

    public void process() {
        String apiKey = applicationPropertiesManager.readKey(NASA_API_KEY);

        if (apiKey == null || apiKey.isBlank()) {
            loadKeyInputScene(rootContainer);
        } else {
//            Image loadingGif = new Image(new File("src/main/resources/com/panko/astronomy_picture_of_the_day/img/rocketBottomToTop.gif").toURI().toString(), true);
//            ImageView imageView = new ImageView(loadingGif);
//            rootContainer.setCenter(imageView);

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Picture picture = new Picture();
                    picture.setDescription("This cosmic expanse of dust, gas, and stars covers some 6 degrees on the sky in the heroic constellation Perseus. At upper left in the gorgeous skyscape is the intriguing young star cluster IC 348 and neighboring Flying Ghost Nebula with clouds of obscuring interstellar dust cataloged as Barnard 3 and 4. At right, another active star forming region NGC 1333 is connected by dark and dusty tendrils on the outskirts of the giant Perseus Molecular Cloud, about 850 light-years away. Other dusty nebulae are scattered around the field of view, along with the faint reddish glow of hydrogen gas. In fact, the cosmic dust tends to hide the newly formed stars and young stellar objects or protostars from prying optical telescopes. Collapsing due to self-gravity, the protostars form from the dense cores embedded in the molecular cloud. At the molecular cloud's estimated distance, this field of view would span over 90 light-years.");
                    loadPictureDescriptionScene(picture);
                    return null;
                }
            };

            task.run();
//
//            new Thread(() -> {
//                Platform.runLater(() -> {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                    System.err.println("HERE!");
//                    rootContainer.setLeft(new Label("LEFT"));
//
//                    Picture picture = new Picture();
//                    picture.setDescription("This cosmic expanse of dust, gas, and stars covers some 6 degrees on the sky in the heroic constellation Perseus. At upper left in the gorgeous skyscape is the intriguing young star cluster IC 348 and neighboring Flying Ghost Nebula with clouds of obscuring interstellar dust cataloged as Barnard 3 and 4. At right, another active star forming region NGC 1333 is connected by dark and dusty tendrils on the outskirts of the giant Perseus Molecular Cloud, about 850 light-years away. Other dusty nebulae are scattered around the field of view, along with the faint reddish glow of hydrogen gas. In fact, the cosmic dust tends to hide the newly formed stars and young stellar objects or protostars from prying optical telescopes. Collapsing due to self-gravity, the protostars form from the dense cores embedded in the molecular cloud. At the molecular cloud's estimated distance, this field of view would span over 90 light-years.");
//                    loadPictureDescriptionScene(picture);
//                });
//            }).start();



//            ProgressBar progressBar = new ProgressBar(0);
//            VBox vBox = new VBox(progressBar);
//            Scene scene = new Scene(vBox, 960, 600);
//
//            primaryStage.setScene(scene);
//            primaryStage.show();
//
//            Thread taskThread = new Thread(() -> {
//                double progress = 0;
//                for (int i = 0; i < 10; i++) {
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    progress += 0.1;
//                    final double reportedProgress = progress;
//
//                    Platform.runLater(() -> progressBar.setProgress(reportedProgress));
//                }
//            });
//
//            taskThread.start();



//
////            HttpResponse<String> httpResponse = apiService.sendHttpRequest(apiKey);
////            Picture picture = httpResponseHandlerService.handleResponse(httpResponse);
////            if ("RU".equals(applicationPropertiesManager.readKey(ApplicationPropertiesManager.LANGUAGE))) {
////                apiService.sendHttpRequest()
////            }
//
//
////            imageSaver.savePictureToFolder(picture);
////            WallpaperChanger.setScreenImage(picture);
        }
    }

    public void loadKeyInputScene(Pane pane) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(KEY_INPUT_SCENE_PATH));
            Pane container = loader.load();

            KeyInputController keyInputController = loader.getController();
            keyInputController.setRootController(this);
            keyInputController.setRootStage(getRootStage());

            rootContainer.setTop(null);
            rootContainer.setCenter(container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPictureDescriptionScene(Picture picture) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(PICTURE_DESCRIPTION_SCENE_PATH));
            Pane vboxContainer = loader.load();

            PictureDescriptionController pictureDescriptionController = loader.getController();
            pictureDescriptionController.showPictureDescription(picture.getDescription());

            rootContainer.setCenter(vboxContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRootScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Astronomy picture of the day");

        this.primaryStage.setMaxWidth(500);
        this.primaryStage.setMaxHeight(600);
        this.primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/panko/astronomy_picture_of_the_day/img/logo.png"))));

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("scene/root-scene.fxml"));
            rootContainer = loader.load();

//            Label left = createLabel("left");
//            left.setPrefHeight(100);
//            left.setStyle("-fx-background-color: #BBBBBB;");
//            rootContainer.setLeft(left);
//
//            Label right = createLabel("right");
//            right.setPrefHeight(100);
//            right.setStyle("-fx-background-color: #CCCCCC;");
//            rootContainer.setRight(right);

//            Label bottom = createLabel("bottom");
//            bottom.setPrefHeight(100);
//            bottom.setStyle("-fx-background-color: #AAAFFF;");
//            rootContainer.setBottom(bottom);

            Scene scene = new Scene(rootContainer);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Label createLabel(String text) {
        Label label = new Label(text);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return label;
    }
}