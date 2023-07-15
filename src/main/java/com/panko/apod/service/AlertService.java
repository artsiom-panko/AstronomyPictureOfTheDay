package com.panko.apod.service;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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

    /**
     * Shows Info-level alert window with "about" info: app version, author, links.
     */
    public void showAboutAlert() {
        Alert alert = createAlert(Alert.AlertType.INFORMATION, "About Astronomy picture of the day");
        Pane aboutScene = sceneService.getScenePane(SCENE_ABOUT);

        alert.getDialogPane().setContent(aboutScene);
        alert.setHeaderText(null);
        alert.setGraphic(null);

        alert.showAndWait();
    }

    /**
     * Shows Info-level alert message about the availability of a new app version.
     *
     * <p> Provides a link for new release downloading.
     */
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

    /**
     * Shows Warning-level alert message to notify user about non-critical app problems.
     *
     * <p> Provides detailed error message.
     *
     * <p> Doesn't stop app execution.
     */
    public void showWarningAlert(String alertHeader, String alertMessage) {
        Platform.runLater(() -> {
            Alert alert = createAlert(Alert.AlertType.WARNING, "Warning");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

            alert.setHeaderText(alertHeader);
            alert.setContentText(alertMessage);

            alert.showAndWait();
        });
    }

    /**
     * Shows Warning-level alert message to notify user about non-critical app problems.
     *
     * <p> Provides exception stack trace.
     *
     * <p> Doesn't stop app execution.
     */
    public void showWarningAlert(String alertHeader, Exception exception) {
        Platform.runLater(() -> {
            Alert alert = createAlert(Alert.AlertType.WARNING, "Warning");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

            setStacktraceInfo(alert, alertHeader, exception);

            alert.showAndWait();
        });
    }

    /**
     * Shows Error-level alert message to notify user about critical, major app problems,
     * which make impossible to continue running the app.
     *
     * <p> Provides exception stack trace.
     *
     * <p> Stop app execution and close the app.
     */
    public void showErrorAlertAndCloseApp(String errorMessage, Exception exception) {
        Platform.runLater(() -> {
            Alert alert = createAlert(Alert.AlertType.ERROR, "Error");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

            setStacktraceInfo(alert, errorMessage, exception);

            alert.showAndWait();
        });
        Thread.currentThread().stop();
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
