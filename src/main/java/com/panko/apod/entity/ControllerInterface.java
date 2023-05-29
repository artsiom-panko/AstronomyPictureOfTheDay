package com.panko.apod.entity;

import com.panko.apod.service.SceneService;
import com.panko.apod.service.SceneService2;

public interface ControllerInterface {
    SceneService2 sceneService2 = null;

    void setSceneService2(SceneService2 sceneService2);
}
