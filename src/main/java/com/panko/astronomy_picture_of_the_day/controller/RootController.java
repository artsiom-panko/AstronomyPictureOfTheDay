package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.MainApplication;
import com.panko.astronomy_picture_of_the_day.entity.Picture;
import com.panko.astronomy_picture_of_the_day.service.ApiService;
import com.panko.astronomy_picture_of_the_day.service.HttpResponseHandlerService;
import com.panko.astronomy_picture_of_the_day.util.ImageSaver;
import com.panko.astronomy_picture_of_the_day.util.PreferencesManager;
import com.panko.astronomy_picture_of_the_day.util.WallpaperChanger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.panko.astronomy_picture_of_the_day.controller.PictureDescriptionController.PICTURE_DESCRIPTION_SCENE_PATH;
import static com.panko.astronomy_picture_of_the_day.service.MainService.NASA_API_KEY;

public class RootController {

    private Stage primaryStage;

    @FXML
    private BorderPane rootContainer;

    private final ImageSaver imageSaver = new ImageSaver();

    private final ApiService apiService = new ApiService();
    private final PreferencesManager preferencesManager = new PreferencesManager();
    private final HttpResponseHandlerService httpResponseHandlerService = new HttpResponseHandlerService();

    private static final System.Logger logger = System.getLogger(RootController.class.getName());

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void process() {
        String apiKey = preferencesManager.readKey(NASA_API_KEY);

        if (apiKey == null || apiKey.isBlank()) {
            loadKeySettingsScene();
        } else {
            logger.log(System.Logger.Level.INFO, "Load loading scene");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("/scene/loading-scene.fxml"));
            Pane loadingScene;
            try {
                loadingScene = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            rootContainer.setCenter(loadingScene);

            new Thread(() -> {
                HttpResponse<String> httpResponse = apiService.sendHttpRequest(apiKey);
                if (httpResponse.statusCode() == 200) {
                    Picture picture = httpResponseHandlerService.handleResponse(httpResponse);
                    if (!imageSaver.savePictureToFolder(picture)) {
                        showPictureSaveAlert();
                        loadKeySettingsScene();
                    }
                    WallpaperChanger.setScreenImage(picture);

                    Platform.runLater(() -> loadPictureDescriptionScene(picture));
                } else {
                    Platform.runLater(() -> {
                        showHttpRequestAlert(httpResponse);
                        loadKeySettingsScene();
                    });
                }
            }).start();
        }
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

        alert.getDialogPane().setContentText("Error during saving image to selected folder. Please, select another folder and try again.");

        alert.showAndWait();
    }

    private void showHttpRequestAlert(HttpResponse<String> httpResponse) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Error");

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(logo);

        alert.setHeaderText("Error during image loading");
        alert.setGraphic(null);

        alert.getDialogPane().setContentText(new JSONObject(httpResponse.body())
                .getJSONObject("error")
                .get("message").toString());

        alert.showAndWait();
    }
}