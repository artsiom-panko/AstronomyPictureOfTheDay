package com.panko.apod.util;

import java.util.prefs.Preferences;

/**
 * PreferencesManager provides a way to save app configurations (preferences),
 * for example user settings, api key, installed path, but not large data.
 *
 * <p> Preferences are key / values pairs where the key is an arbitrary name for the preference.
 * The value can be a boolean, string, int of another primitive type.
 *
 * <p> The actual storage of the data is dependent on the platform.
 */
public class PreferencesManager {
    public static final String APP_LANGUAGE = "appLanguage";
    public static final String NASA_API_KEY = "nasaApiKey";
    public static final String APP_ABSOLUTE_PATH = "appAbsolutePath";
    public static final String NUMBER_OF_APP_LAUNCHES = "numberOfAppLaunches";

    private static final System.Logger logger = System.getLogger(PreferencesManager.class.getName());

    private final Preferences preferences = Preferences.userRoot().node(this.getClass().getName());

    public String readKey(String key) {
        logger.log(System.Logger.Level.INFO, "Read property by key: " + key);
        return preferences.get(key, null);
    }

    public void saveKey(String key, String value) {
        logger.log(System.Logger.Level.INFO, String.format("Save property, key: %s, value: %s ", key, value));
        preferences.put(key, value);
    }

    public void removeKey(String key) {
        logger.log(System.Logger.Level.INFO, "Remove property by key: " + key);
        preferences.remove(key);
    }
}
