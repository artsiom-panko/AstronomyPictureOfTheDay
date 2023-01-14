package com.panko.astronomy_picture_of_the_day.util;

import java.io.*;
import java.util.Properties;

public class ApplicationPropertiesManager {
    private static final String APPLICATION_PROPERTIES = "src/main/resources/application.properties";

    public String readKey(String key) {
        try (FileInputStream fileInputStream = new FileInputStream(APPLICATION_PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);

            return properties.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveKey(String key, String value) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(APPLICATION_PROPERTIES)) {
            properties.load(fileInputStream);
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
