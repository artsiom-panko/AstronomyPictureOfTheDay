package com.panko.apod.entity;

import com.panko.apod.service.SceneService;

// TODO Do we really need a separated interface for SceneController?
public interface SceneController {
    SceneService SCENE_SERVICE = null;

    void setSceneService(SceneService sceneService);
}
