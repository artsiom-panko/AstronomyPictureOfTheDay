package com.panko.apod.service;

import com.panko.apod.MainApplication;
import com.panko.apod.controller.MainController;
import com.panko.apod.entity.AppScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static com.panko.apod.entity.AppScene.ROOT;

public class SceneService {
    private BorderPane mainPane;
    private MainController mainController;

//    private static final System.Logger logger = System.getLogger(SceneService.class.getName());
//
//    Map<String, AppScene> loadedAppSciencesMap = new HashMap<>();
//    private final List<AppScene> loadedAppSciences = new ArrayList<>();
//
//    public void makeInitialSetupAndLaunch(Stage primaryStage) {
////        AppScene appScene = getAppScene(ROOT);
////
////        mainPane = (BorderPane) appScene.getPane();
////        MainController mainController = (MainController) appScene.getController();
////
//////        mainController.setStage(primaryStage);
////        mainController.setSceneService(this);
//
//        //
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(MainApplication.class.getResource(AppScene.ROOT.getPath()));
//        try {
//            mainPane = loader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        mainController = loader.getController();
//        mainController.setSceneService(this);
//        //
//
//        primaryStage.setTitle("Astronomy picture of the day");
//        primaryStage.getIcons().add(new Image(Objects.requireNonNull(SceneService.class.getResourceAsStream("/img/logo.png"))));
//        primaryStage.setScene(new Scene(mainPane));
//        primaryStage.show();
//
//        launchMainThread();
//    }
//
//    public void launchMainThread() {
//        mainController.launchMainThread();
//    }
//
//    public void showScene(AppScene appScene) {
//        AppScene loadedAppScene = getAppScene(appScene);
//
//        mainPane.setCenter(loadedAppScene.getPane());
//    }
//
//    public AppScene getAppScene(AppScene appScene) {
//        logger.log(System.Logger.Level.INFO, "Start loading scene: {0}", appScene.name());
//
//        if (loadedAppSciencesMap.containsKey(appScene.name())) {
//            return loadedAppSciencesMap.get(appScene.name());
//        } else {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApplication.class.getResource(appScene.getPath()));
//
//            try {
//                appScene.setPane(loader.load());
////                appScene.setController(loader.getController());
//                appScene.setSceneService(this);
//
//            } catch (IOException e) {
//                // TODO what to do?
//                // showAlertAndCloseApp();
//                throw new RuntimeException();
//            }
//
//            loadedAppSciencesMap.put(appScene.name(), appScene);
//
//            return appScene;
//        }
//    }
}
