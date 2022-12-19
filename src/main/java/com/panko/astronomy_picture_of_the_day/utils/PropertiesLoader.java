package com.panko.astronomy_picture_of_the_day.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.panko.astronomy_picture_of_the_day.service.MainService.NASA_API_KEY;

public class PropertiesLoader {

    public static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        }

        Object nasaProp = properties.get(NASA_API_KEY);
        if (nasaProp.toString().isBlank()) {
            String envKey = System.getenv(NASA_API_KEY);
            if (envKey == null || envKey.isBlank()) {
                throw new RuntimeException("Api key not was not found in application.properties or in Environment variables");
            }
            properties.setProperty(NASA_API_KEY, envKey);
        }

        return properties;
    }
}
