package com.panko.astronomy_picture_of_the_day.service;

import com.panko.astronomy_picture_of_the_day.utils.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;

public class ApiKeyService {

    public String readKey(String key) {
        try {
            Properties properties = PropertiesLoader.loadProperties();
            String nasaApiKey = properties.getProperty(key);

            if (nasaApiKey == null || nasaApiKey.isBlank()) {
                return null;
            } else {
                return nasaApiKey;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
