package com.panko.apod.service;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.panko.apod.service.SceneService.SCENE_ABOUT;

public class AlertService {
    private final SceneService sceneService = new SceneService();

    // TODO hyperlinks are not working :(
    public void showAboutAlert() {
        Alert alert = createAlert(Alert.AlertType.INFORMATION, "About Astronomy picture of the day");

        Pane aboutScene = sceneService.getScenePane(SCENE_ABOUT);
        Text textNode = (Text) aboutScene.lookup("#versionValue");
        textNode.setText("Version: " + new UpdateCheckService().getCurrentAppVersion());

        alert.getDialogPane().setContent(aboutScene);
        alert.setHeaderText(null);
        alert.setGraphic(null);

        alert.showAndWait();
    }

    public void showUpdateAlert(String latestReleaseHtmlLink, String latestReleaseDescription) {
        Hyperlink hyperlink = new Hyperlink("Click here to download");
        hyperlink.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI(latestReleaseHtmlLink));
            } catch (IOException | URISyntaxException e) {
                // TODO
                throw new RuntimeException(e);
            }
        });

        Platform.runLater(() -> {
            Alert alert = createAlert(Alert.AlertType.INFORMATION, "Information");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setHeaderText("New version is now available");

            alert.getDialogPane().setContent(hyperlink);
            alert.getDialogPane().setContent(new TextArea(latestReleaseDescription));

            alert.showAndWait();
        });
    }

    public void showWarningAlert(String alertHeader, String alertMessage) {
        Platform.runLater(() -> {
            Alert alert = createAlert(Alert.AlertType.WARNING, "Warning");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

            alert.setHeaderText(alertHeader);
            alert.setContentText(alertMessage);

            alert.showAndWait();
        });
    }

    public void showWarningAlert(String alertHeader, Exception exception) {
        Platform.runLater(() -> {
            Alert alert = createAlert(Alert.AlertType.WARNING, "Warning");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

            setStacktraceInfo(alert, alertHeader, exception);

            alert.showAndWait();
        });
    }

    public void showErrorAlertAndCloseApp(String errorMessage, Exception exception) {
        Platform.runLater(() -> {
            Alert alert = createAlert(Alert.AlertType.ERROR, "Error");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

            setStacktraceInfo(alert, errorMessage, exception);

            alert.showAndWait();
            Platform.exit();
        });
    }

    private Alert createAlert(Alert.AlertType alertType, String alertTitle) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(logo);

        return alert;
    }

    private void setStacktraceInfo(Alert alert, String errorMessage, Exception exception) {
        alert.setHeaderText(errorMessage);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);

        TextArea textArea = new TextArea(stringWriter.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        alert.getDialogPane().setExpandableContent(expContent);
    }
}
