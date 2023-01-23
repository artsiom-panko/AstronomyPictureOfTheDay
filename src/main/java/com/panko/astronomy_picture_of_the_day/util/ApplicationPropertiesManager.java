package com.panko.astronomy_picture_of_the_day.util;

import java.io.*;
import java.util.Properties;

public class ApplicationPropertiesManager {
    public static final String LANGUAGE = "language";
    public static final String NASA_API_KEY = "nasa.api.key";
    public static final String PICTURES_FOLDER = "pictures.folder";

    private static final String APPLICATION_PROPERTIES = "/application.properties";

    public String readKey(String key) {
        try (InputStream inputStream = getClass().getResourceAsStream(APPLICATION_PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            return properties.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveKey(String key, String value) {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream(APPLICATION_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(APPLICATION_PROPERTIES)) {
            properties.setProperty(key, value);
            properties.store(fileOutputStream, null);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
