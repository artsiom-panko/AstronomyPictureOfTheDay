package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.MainApplication;
import com.panko.astronomy_picture_of_the_day.entity.Picture;
import com.panko.astronomy_picture_of_the_day.util.ApplicationPropertiesManager;
import com.panko.astronomy_picture_of_the_day.service.ApiService;
import com.panko.astronomy_picture_of_the_day.service.HttpResponseHandlerService;
import com.panko.astronomy_picture_of_the_day.util.ImageSaver;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.panko.astronomy_picture_of_the_day.controller.KeyInputController.KEY_INPUT_SCENE_PATH;
import static com.panko.astronomy_picture_of_the_day.controller.PictureDescriptionController.PICTURE_DESCRIPTION_SCENE_PATH;
import static com.panko.astronomy_picture_of_the_day.service.MainService.NASA_API_KEY;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 */
public class RootController {

    private Stage rootStage;
    private BorderPane rootContainer;

    private final ImageSaver imageSaver = new ImageSaver();

    private final ApiService apiService = new ApiService();
    private final ApplicationPropertiesManager applicationPropertiesManager = new ApplicationPropertiesManager();
    private final HttpResponseHandlerService httpResponseHandlerService = new HttpResponseHandlerService();

    public Stage getRootStage() {
        return rootStage;
    }

    public Pane getRootContainer() {
        return rootContainer;
    }

    public void process() {
        String apiKey = applicationPropertiesManager.readKey(NASA_API_KEY);

        if (apiKey == null || apiKey.isBlank()) {
            loadKeyInputScene(rootContainer);
        } else {
//            HttpResponse<String> httpResponse = apiService.sendApiService(apiKey);
//            Picture picture = httpResponseHandlerService.handleResponse(httpResponse);
//            imageSaver.savePictureToFolder(picture);
//            WallpaperChanger.setScreenImage(picture);
            Picture picture = new Picture();
            picture.setDescription("This cosmic expanse of dust, gas, and stars covers some 6 degrees on the sky in the heroic constellation Perseus. At upper left in the gorgeous skyscape is the intriguing young star cluster IC 348 and neighboring Flying Ghost Nebula with clouds of obscuring interstellar dust cataloged as Barnard 3 and 4. At right, another active star forming region NGC 1333 is connected by dark and dusty tendrils on the outskirts of the giant Perseus Molecular Cloud, about 850 light-years away. Other dusty nebulae are scattered around the field of view, along with the faint reddish glow of hydrogen gas. In fact, the cosmic dust tends to hide the newly formed stars and young stellar objects or protostars from prying optical telescopes. Collapsing due to self-gravity, the protostars form from the dense cores embedded in the molecular cloud. At the molecular cloud's estimated distance, this field of view would span over 90 light-years.");

            loadPictureDescriptionScene(picture);
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

            rootContainer.setPadding(new Insets(5));
            rootContainer.setCenter(vboxContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRootScene(Stage rootStage) {
        this.rootStage = rootStage;
        this.rootStage.setTitle("Astronomy picture of the day");

        this.rootStage.setMaxWidth(500);
        this.rootStage.setMaxHeight(600);
        this.rootStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/panko/astronomy_picture_of_the_day/img/logo.png"))));

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("scene/root-scene.fxml"));
            rootContainer = loader.load();
            rootContainer.setPadding(new Insets(5));

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
            rootStage.setScene(scene);
            rootStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Label createLabel(String text){
        Label label = new Label(text);
        BorderPane.setMargin(label, new Insets(5));
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return label;
    }
}