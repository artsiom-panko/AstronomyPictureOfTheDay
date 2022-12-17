package com.panko.astronomy_picture_of_the_day.service;


import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

class ApiServiceTest {
    ApiService apiService = new ApiService();

    @Test
    void sendApiService() {
        HttpResponse<String> stringHttpResponse = apiService.sendApiService();

        System.out.println(stringHttpResponse);
    }
}