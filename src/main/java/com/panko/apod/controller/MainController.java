package com.panko.apod.controller;

import com.panko.apod.entity.Picture;
import com.panko.apod.entity.SceneController;
import com.panko.apod.service.*;
import com.panko.apod.util.PictureSaver;
import com.panko.apod.util.PreferencesManager;
import com.panko.apod.util.WallpaperChanger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
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
    private Text numberOfRocketLaunches;

    private SceneService sceneService;

    private final ApiService apiService = new ApiService();
    private final PictureSaver pictureSaver = new PictureSaver();
    private final AlertService alertService = new AlertService();
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
            try {
                System.out.println("1. " + Thread.currentThread());
                HttpResponse<String> httpResponse = apiService.sendHttpRequest(apiKey);
                Picture picture = httpResponseParsingService.parseHttpResponseToPicture(httpResponse);

                pictureSaver.savePictureToFolder(picture);

                WallpaperChanger.setScreenImage(picture);

                Platform.runLater(() -> {
                    System.out.println("2. " + Thread.currentThread());
                    sceneService.showPictureDescriptionScene(picture);
                    updateAndShowLaunchesCounter();
                    infoBlock.setVisible(true);

                    new UpdateCheckService().showNewUpdateIfAvailable();
                });

            } catch (Exception exception) {
                alertService.showErrorAlertAndCloseApp(
                        "Unknown error", exception);
            }
        }).start();
        System.out.println("3. " + Thread.currentThread());
    }

    // TODO Split to two separate methods
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

    @FXML
    private void showAboutAlert() throws IOException {
        alertService.showAboutAlert();
    }

    @Override
    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }
}