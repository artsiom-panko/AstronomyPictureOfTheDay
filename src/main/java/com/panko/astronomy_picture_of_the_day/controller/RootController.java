package com.panko.astronomy_picture_of_the_day.controller;

import com.panko.astronomy_picture_of_the_day.MainApplication;
import com.panko.astronomy_picture_of_the_day.entity.Picture;
import com.panko.astronomy_picture_of_the_day.service.ApiService;
import com.panko.astronomy_picture_of_the_day.service.HttpResponseHandlerService;
import com.panko.astronomy_picture_of_the_day.util.ImageSaver;
import com.panko.astronomy_picture_of_the_day.util.PreferencesManager;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.panko.astronomy_picture_of_the_day.controller.PictureDescriptionController.PICTURE_DESCRIPTION_SCENE_PATH;
import static com.panko.astronomy_picture_of_the_day.service.MainService.NASA_API_KEY;

public class RootController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        process();
    }

    public void process() {
        String apiKey = preferencesManager.readKey(NASA_API_KEY);

        if (apiKey == null || apiKey.isBlank()) {
            loadKeyInputScene();
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
                Picture picture = httpResponseHandlerService.handleResponse(httpResponse);
//                if ("RU".equals(applicationPropertiesManager.readKey(ApplicationPropertiesManager.LANGUAGE))) {
////                    apiService.sendHttpRequest()
//                }

//                picture.setDescription("This cosmic expanse of dust, gas, and stars covers some 6 degrees on the sky in the heroic constellation Perseus. At upper left in the gorgeous skyscape is the intriguing young star cluster IC 348 and neighboring Flying Ghost Nebula with clouds of obscuring interstellar dust cataloged as Barnard 3 and 4. At right, another active star forming region NGC 1333 is connected by dark and dusty tendrils on the outskirts of the giant Perseus Molecular Cloud, about 850 light-years away. Other dusty nebulae are scattered around the field of view, along with the faint reddish glow of hydrogen gas. In fact, the cosmic dust tends to hide the newly formed stars and young stellar objects or protostars from prying optical telescopes. Collapsing due to self-gravity, the protostars form from the dense cores embedded in the molecular cloud. At the molecular cloud's estimated distance, this field of view would span over 90 light-years. This cosmic expanse of dust, gas, and stars covers some 6 degrees on the sky in the heroic constellation Perseus. At upper left in the gorgeous skyscape is the intriguing young star cluster IC 348 and neighboring Flying Ghost Nebula with clouds of obscuring interstellar dust cataloged as Barnard 3 and 4. At right, another active star forming region NGC 1333 is connected by dark and dusty tendrils on the outskirts of the giant Perseus Molecular Cloud, about 850 light-years away. Other dusty nebulae are scattered around the field of view, along with the faint reddish glow of hydrogen gas. In fact, the cosmic dust tends to hide the newly formed stars and young stellar objects or protostars from prying optical telescopes. Collapsing due to self-gravity, the protostars form from the dense cores embedded in the molecular cloud. At the molecular cloud's estimated distance, this field of view would span over 90 light-years.");
//                picture.setDescription("This cosmic expanse of dust, gas, and stars covers some 6 degrees on the sky in the heroic constellation Perseus. At upper left in the gorgeous skyscape is the intriguing young star cluster IC 348 and neighboring Flying Ghost Nebula with clouds of obscuring interstellar dust cataloged as Barnard 3 and 4. ");
//                imageSaver.savePictureToFolder(picture);
//                WallpaperChanger.setScreenImage(picture);

                Platform.runLater(() -> {
                    loadPictureDescriptionScene(picture);
//                    loadBottomPaneScene();
                });
            }).start();
        }
    }

    public void loadKeyInputScene() {
        try {
            logger.log(System.Logger.Level.INFO, "Load key input scene");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/key-input-scene.fxml"));
            Pane container = loader.load();

            KeyInputController keyInputController = loader.getController();
            keyInputController.setRootController(this);
            keyInputController.setRootStage(primaryStage);

            rootContainer.setCenter(container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadKeyInputSceneFXML(Event event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loadKeyInputScene();
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

    public void loadBottomPaneScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("/scene/bottom-pane.fxml"));
            Pane bottomPane = loader.load();

            rootContainer.setBottom(bottomPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showAboutPage() {
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
}