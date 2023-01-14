package com.panko.astronomy_picture_of_the_day.service;

import com.panko.astronomy_picture_of_the_day.util.ApplicationPropertiesManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationPropertiesManagerTest {

    private final static String NASA_API_KEY = "nasa.api.key";

    ApplicationPropertiesManager applicationPropertiesManager = new ApplicationPropertiesManager();

    @Test
    void readKey() {
        String key = applicationPropertiesManager.readKey(NASA_API_KEY);

        assertNotNull(key);
    }
}