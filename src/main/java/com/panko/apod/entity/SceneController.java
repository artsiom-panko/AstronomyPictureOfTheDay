package com.panko.apod.entity;

import com.panko.apod.service.SceneService;

public interface SceneController {
    SceneService SCENE_SERVICE = null;

    void setSceneService(SceneService sceneService);
}
