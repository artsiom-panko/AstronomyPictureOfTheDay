package com.panko.apod.entity;

import com.panko.apod.service.SceneService;

public abstract class Controller {
    SceneService sceneService;

    public SceneService getSceneService() {
        return sceneService;
    }

    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }
}
