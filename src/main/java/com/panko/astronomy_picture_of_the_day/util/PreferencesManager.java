package com.panko.astronomy_picture_of_the_day.util;

import java.util.prefs.Preferences;

public class PreferencesManager {
    public static final String LANGUAGE = "language";
    public static final String NASA_API_KEY = "nasa.api.key";
    public static final String PICTURES_FOLDER = "folder";

    private static final System.Logger logger = System.getLogger(PreferencesManager.class.getName());

    private final Preferences preferences = Preferences.userRoot().node("astronomy_picture_of_the_day");

    public String readKey(String key) {
        logger.log(System.Logger.Level.INFO, "Read property by key: " + key);
        return preferences.get(key, null);
    }

    public void saveKey(String key, String value) {
        logger.log(System.Logger.Level.INFO, String.format("Save property, key: %s, value: %s ", key, value));
        preferences.put(key, value);
    }
}
