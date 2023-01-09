package com.panko.astronomy_picture_of_the_day.service;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenService {
    private final Scene main;
    private static final HashMap<String, Pane> screenMap = new HashMap<>();

    public ScreenService(Scene main) {
        this.main = main;
    }

    public static void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    public static void removeScreen(String name) {
        screenMap.remove(name);
    }

    protected void activate(String name) {
        main.setRoot(screenMap.get(name));
    }
}
