package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.MainApplication;
import com.panko.astronomy_picture_of_the_day.entity.Picture;
import com.panko.astronomy_picture_of_the_day.service.ApiService;
import com.panko.astronomy_picture_of_the_day.service.HttpResponseHandlerService;
import com.panko.astronomy_picture_of_the_day.util.ImageSaver;
import com.panko.astronomy_picture_of_the_day.util.WallpaperChanger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.http.HttpResponse;

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

    private KeyInputController keyInputController;
    private PictureDescriptionController pictureDescriptionController;

    private final ApiService apiService = new ApiService();
    private final HttpResponseHandlerService httpResponseHandlerService = new HttpResponseHandlerService();

    public Stage getRootStage() {
        return rootStage;
    }

    public Pane getRootContainer() {
        return rootContainer;
    }

    public void process() {
        String apiKey = keyInputController.getApiKeyValue(NASA_API_KEY);

        if (apiKey == null || apiKey.isBlank()) {
            keyInputController.loadKeyInputScene(rootContainer);
        } else {
            HttpResponse<String> httpResponse = apiService.sendApiService(apiKey);
            Picture picture = httpResponseHandlerService.handleResponse(httpResponse);
            imageSaver.savePictureToFolder(picture);
            WallpaperChanger.setScreenImage(picture);

            loadPictureDescriptionScene(picture);
        }
    }

    public void loadPictureDescriptionScene(Picture picture) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(PICTURE_DESCRIPTION_SCENE_PATH));
            Pane vboxContainer = loader.load();
            PictureDescriptionController pictureDescriptionController1 = loader.getController();
            pictureDescriptionController1.showPictureDescription(picture.getDescription());

            rootContainer.setPadding(new Insets(5));
            rootContainer.setCenter(vboxContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRootScene(Stage rootStage) {
        this.rootStage = rootStage;
        this.rootStage.setTitle("Astronomy picture of the day");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource("/com/panko/astronomy_picture_of_the_day/key-input-scene.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        keyInputController = loader.getController();
        keyInputController.setRootController(this);

        loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource("/com/panko/astronomy_picture_of_the_day/picture-description-scene.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pictureDescriptionController = loader.getController();

        try {
            loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("root-scene.fxml"));
            rootContainer = loader.load();

            Scene scene = new Scene(rootContainer);
            rootStage.setScene(scene);
            rootStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}