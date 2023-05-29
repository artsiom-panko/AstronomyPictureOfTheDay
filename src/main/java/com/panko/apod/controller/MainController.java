package com.panko.apod.controller;

import com.panko.apod.MainApplication;
import com.panko.apod.entity.Picture;
import com.panko.apod.entity.SceneController;
import com.panko.apod.service.AlertService;
import com.panko.apod.service.ApiService;
import com.panko.apod.service.HttpResponseParsingService;
import com.panko.apod.service.SceneService;
import com.panko.apod.util.PictureSaver;
import com.panko.apod.util.PreferencesManager;
import com.panko.apod.util.WallpaperChanger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Optional;

import static com.panko.apod.util.PreferencesManager.NASA_API_KEY;
import static com.panko.apod.util.PreferencesManager.NUMBER_OF_ROCKET_LAUNCHES;

public class MainController implements SceneController {

    @FXML
    private HBox infoBlock;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Text numberOfRocketLaunches;

    private final PictureSaver pictureSaver = new PictureSaver();

    private SceneService sceneService;

    private final AlertService alertService = new AlertService();
    private final ApiService apiService = new ApiService();
    private final PreferencesManager preferencesManager = new PreferencesManager();
    private final HttpResponseParsingService httpResponseParsingService = new HttpResponseParsingService();

    private static final System.Logger logger = System.getLogger(MainController.class.getName());


    public void launchMainThread() {
        String apiKey = preferencesManager.readKey(NASA_API_KEY);

        if (apiKey == null || apiKey.isBlank()) {
            sceneService.showScene(SceneService.SCENE_SETTINGS);
        } else {
            proceedMainThread(apiKey);
        }
    }

    public void proceedMainThread(String apiKey) {
        sceneService.showScene(SceneService.SCENE_LOADING);

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
            sceneService.showScene(SceneService.SCENE_SETTINGS);

            // TODO Add App auto closing
        });
    }

    private void saveAndShowPicture(Picture picture) {
        if (!pictureSaver.savePictureToFolder(picture)) {
            Platform.runLater(() -> {
                alertService.showErrorAlert(String.format(
                        "Error during saving image to selected folder: %s %nPlease, select another folder and try again.",
                        preferencesManager.readKey(PreferencesManager.PICTURES_FOLDER)));

                sceneService.showScene(SceneService.SCENE_SETTINGS);
            });
        } else {
            WallpaperChanger.setScreenImage(picture);
            Platform.runLater(() -> {
                sceneService.showScene(SceneService.SCENE_DESCRIPTION);
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

    @FXML
    public void showSettingsScene() {
        sceneService.showScene(SceneService.SCENE_SETTINGS);
    }

    public void showPictureDescriptionScene(Picture picture) {
        try {
            logger.log(System.Logger.Level.INFO, "Load picture description scene");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(SceneService.SCENE_DESCRIPTION));
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

    @Override
    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }
}