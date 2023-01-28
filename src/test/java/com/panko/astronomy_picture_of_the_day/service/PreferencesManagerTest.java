package com.panko.astronomy_picture_of_the_day.service;

import com.panko.astronomy_picture_of_the_day.util.PreferencesManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreferencesManagerTest {

    private final static String NASA_API_KEY = "nasa.api.key";

    PreferencesManager preferencesManager = new PreferencesManager();

    @Test
    void readKey() {
        String key = preferencesManager.readKey(NASA_API_KEY);

        assertNotNull(key);
    }
}