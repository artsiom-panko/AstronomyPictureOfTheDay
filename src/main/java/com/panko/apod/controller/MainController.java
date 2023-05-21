package com.panko.apod.controller;

import com.panko.apod.MainApplication;
import com.panko.apod.entity.Picture;
import com.panko.apod.service.AlertService;
import com.panko.apod.service.ApiService;
import com.panko.apod.service.HttpResponseParsingService;
import com.panko.apod.util.PictureSaver;
import com.panko.apod.util.PreferencesManager;
import com.panko.apod.util.WallpaperChanger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;

import static com.panko.apod.util.PreferencesManager.NASA_API_KEY;
import static com.panko.apod.util.PreferencesManager.NUMBER_OF_ROCKET_LAUNCHES;
import static com.panko.apod.controller.PictureDescriptionController.PICTURE_DESCRIPTION_SCENE_PATH;

public class MainController {

    private Stage primaryStage;

    @FXML
    private HBox infoBlock;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Text numberOfRocketLaunches;

    private final PictureSaver pictureSaver = new PictureSaver();

    private final AlertService alertService = new AlertService();
    private final ApiService apiService = new ApiService();
    private final PreferencesManager preferencesManager = new PreferencesManager();
    private final HttpResponseParsingService httpResponseParsingService = new HttpResponseParsingService();

    private static final String SCENE_ABOUT = "/scene/about.fxml";
    private static final String SCENE_LOADING = "/scene/loading-scene.fxml";
    private static final String SCENE_SETTINGS = "/scene/settings-scene.fxml";
    private static final String SCENE_DESCRIPTION = "/scene/picture-description-scene.fxml";

    private static final System.Logger logger = System.getLogger(MainController.class.getName());

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void launchMainThread() {
        String apiKey = preferencesManager.readKey(NASA_API_KEY);

        if (apiKey == null || apiKey.isBlank()) {
            showScene(SCENE_SETTINGS);
        } else {
            proceedMainThread(apiKey);
        }
    }

    public void proceedMainThread(String apiKey) {
        showScene(SCENE_LOADING);

        infoBlock.setVisible(false);
        showLaunchesCounter();

        new Thread(() -> {
            HttpResponse<String> httpResponse = apiService.sendHttpRequest(apiKey);
            if (httpResponse != null && httpResponse.statusCode() == 200) {
                Picture picture = httpResponseParsingService.parseHttpResponseToPicture(httpResponse);
                saveAndShowPicture(picture);
            } else {
                showAlertAndCloseApp(httpResponse);
            }
        }).start();
    }

    private Pane loadScene(String sceneName) {
        logger.log(System.Logger.Level.INFO, "Start loading scene: {0}", sceneName);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(sceneName));
        try {
            return loader.load();
        } catch (IOException e) {
            // TODO what to do?
//            showAlertAndCloseApp();
            throw new RuntimeException();
        }
    }


    // TODO to reproduce and look
    private void showAlertAndCloseApp(HttpResponse<String> httpResponse) {
        Platform.runLater(() -> {
            String errorMessage;
            if (httpResponse == null) {
                errorMessage = "Connection problem. \nPlease, try later.";
            } else {
                errorMessage = null;
            }
            alertService.showErrorAlert(errorMessage);
            showScene(SCENE_SETTINGS);

            // TODO Add App auto closing
        });
    }

    private void saveAndShowPicture(Picture picture) {
        if (!pictureSaver.savePictureToFolder(picture)) {
            Platform.runLater(() -> {
                alertService.showErrorAlert(String.format(
                        "Error during saving image to selected folder: %s %nPlease, select another folder and try again.",
                        preferencesManager.readKey(PreferencesManager.PICTURES_FOLDER)));

                showScene(SCENE_SETTINGS);
            });
        } else {
            WallpaperChanger.setScreenImage(picture);
            Platform.runLater(() -> {
                showPictureDescriptionScene(picture);
                updateAndShowLaunchesCounter();
                infoBlock.setVisible(true);
            });
        }
    }

    private void updateAndShowLaunchesCounter() {
        String numberOfLaunches = Optional
                .ofNullable(preferencesManager.readKey(NUMBER_OF_ROCKET_LAUNCHES))
                .orElse("0");

        String incrementedNumberOfLaunches = String.valueOf(Integer.parseInt(numberOfLaunches) + 1);

        preferencesManager.saveKey(
                NUMBER_OF_ROCKET_LAUNCHES,
                incrementedNumberOfLaunches);

        showLaunchesCounter();
    }

    private void showLaunchesCounter() {
        String numberOfLaunches = Optional
                .ofNullable(preferencesManager.readKey(NUMBER_OF_ROCKET_LAUNCHES))
                .orElse("0");

        numberOfRocketLaunches.setText(String.format("Rocket launches: %s", numberOfLaunches));
    }

    public void showScene(String sceneName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
        Pane container = loadScene(sceneName);

//        SettingsController settingsController = loader.getController();
//        settingsController.setRootController(this);
//        settingsController.setRootStage(primaryStage);

        mainPane.setCenter(container);
    }

    @FXML
    public void showSettingsScene() {
        showScene(SCENE_SETTINGS);
    }

    public void showPictureDescriptionScene(Picture picture) {
        try {
            logger.log(System.Logger.Level.INFO, "Load picture description scene");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(PICTURE_DESCRIPTION_SCENE_PATH));
            Pane vboxContainer = loader.load();

            PictureDescriptionController pictureDescriptionController = loader.getController();
            pictureDescriptionController.showPictureDescription(picture);
            pictureDescriptionController.setPrimaryContainer(mainPane);

            mainPane.setCenter(vboxContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showAboutAlert() {
        alertService.showAboutAlert();
    }
}