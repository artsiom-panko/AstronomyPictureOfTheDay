package com.panko.astronomy_picture_of_the_day.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiKeyServiceTest {

    private final static String NASA_API_KEY = "nasa.api.key";

    ApiKeyService apiKeyService = new ApiKeyService();

    @Test
    void readKey() {
        String key = apiKeyService.readKey(NASA_API_KEY);

        assertNotNull(key);
    }
}