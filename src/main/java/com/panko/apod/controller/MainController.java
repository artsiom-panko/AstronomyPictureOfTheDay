package com.panko.apod.controller;

import com.panko.apod.MainApplication;
import com.panko.apod.entity.Picture;
import com.panko.apod.service.ApiService;
import com.panko.apod.service.HttpResponseHandlerService;
import com.panko.apod.util.ImageSaver;
import com.panko.apod.util.PreferencesManager;
import com.panko.apod.util.WallpaperChanger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import static com.panko.apod.controller.PictureDescriptionController.PICTURE_DESCRIPTION_SCENE_PATH;
import static com.panko.apod.service.MainService.NASA_API_KEY;
import static com.panko.apod.util.PreferencesManager.NUMBER_OF_ROCKET_LAUNCHES;

public class MainController {

    private Stage primaryStage;

    @FXML
    private HBox infoBlock;
    @FXML
    private BorderPane rootContainer;
    @FXML
    private Text numberOfRocketLaunches;

    private final ImageSaver imageSaver = new ImageSaver();

    private final ApiService apiService = new ApiService();
    private final PreferencesManager preferencesManager = new PreferencesManager();
    private final HttpResponseHandlerService httpResponseHandlerService = new HttpResponseHandlerService();

    private static final System.Logger logger = System.getLogger(MainController.class.getName());

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void launchMainThread() {
        String apiKey = preferencesManager.readKey(NASA_API_KEY);
        apiKey = null;

        if (apiKey == null || apiKey.isBlank()) {
            loadKeySettingsScene();
        } else {
            // TODO Rename method
            load(apiKey);
        }
    }

    public void load(String apiKey) {
        logger.log(System.Logger.Level.INFO, "Load loading scene");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource("/scene/loading-scene.fxml"));
        Pane loadingScene;
        try {
            loadingScene = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        infoBlock.setVisible(false);
        showLaunchesCounter();
        rootContainer.setCenter(loadingScene);

        new Thread(() -> {
            HttpResponse<String> httpResponse = apiService.sendHttpRequest(apiKey);
            if (httpResponse != null && httpResponse.statusCode() == 200) {
                Picture picture = httpResponseHandlerService.parseHttpResponseToPicture(httpResponse);
                if (!imageSaver.savePictureToFolder(picture)) {
                    Platform.runLater(() -> {
                        showPictureSaveAlert();
                        loadKeySettingsScene();
                    });
                } else {
                    WallpaperChanger.setScreenImage(picture);
                    Platform.runLater(() -> {
                        loadPictureDescriptionScene(picture);
                        updateAndShowLaunchesCounter();
                        infoBlock.setVisible(true);
                    });
                }
            } else {
                Platform.runLater(() -> {
                    String errorMessage;
                    if (httpResponse == null) {
                        errorMessage = "Connection problem. \nPlease, try later.";
                    } else {
                        errorMessage = null;
                    }
                    showHttpRequestAlert(errorMessage);
                    loadKeySettingsScene();
                });
            }
        }).start();
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

    public void loadKeySettingsScene() {
        try {
            logger.log(System.Logger.Level.INFO, "Load key input scene");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/settings-scene.fxml"));
            Pane container = loader.load();

            SettingsController settingsController = loader.getController();
            settingsController.setRootController(this);
            settingsController.setRootStage(primaryStage);

            rootContainer.setCenter(container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadKeyInputSceneFXML(Event event) {
        loadKeySettingsScene();
    }

    public void loadPictureDescriptionScene(Picture picture) {
        try {
            logger.log(System.Logger.Level.INFO, "Load picture description scene");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(PICTURE_DESCRIPTION_SCENE_PATH));
            Pane vboxContainer = loader.load();

            PictureDescriptionController pictureDescriptionController = loader.getController();
            pictureDescriptionController.showPictureDescription(picture);
            pictureDescriptionController.setPrimaryContainer(rootContainer);

            rootContainer.setCenter(vboxContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showAboutAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Astronomy picture of the day");

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(logo);

        alert.setHeaderText(null);
        alert.setGraphic(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource("/scene/about.fxml"));
        Pane aboutScene = null;
        try {
            aboutScene = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        alert.getDialogPane().setContent(aboutScene);

        alert.showAndWait();
    }

    private void showPictureSaveAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Error");

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(logo);

        alert.setHeaderText("Error during image loading");
        alert.setGraphic(null);

        alert.getDialogPane().setContentText(
                String.format("Error during saving image to selected folder: %s %nPlease, select another folder and try again.",
                        preferencesManager.readKey(PreferencesManager.PICTURES_FOLDER)));

        alert.show();
    }

    private void showHttpRequestAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Error");

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(logo);

        alert.setHeaderText("Error during image loading");
        alert.setGraphic(null);

        alert.getDialogPane().setContentText(errorMessage);

        alert.showAndWait();
    }
}