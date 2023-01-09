package com.panko.astronomy_picture_of_the_day.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiKeyService {

    private final Properties properties = new Properties();

    private static final String APPLICATION_PROPERTIES = "application.properties";

    public String readKey(String key) {
        try (InputStream inputStream = getClass().getResourceAsStream("/".concat(APPLICATION_PROPERTIES))) {
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        if (nasaApiKey == null) {
//            return null;
//        } else {
//            String envKey = System.getenv("nasa.api.key");
//            if (envKey == null || envKey.isBlank()) {
//                throw new RuntimeException("Api key not was not found in application.properties or in Environment variables");
//            }
//            return envKey;
//        }
    }

    public boolean saveKey(String key, String value) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/application.properties")) {
            properties.setProperty(key, value);
            properties.store(fileOutputStream, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
