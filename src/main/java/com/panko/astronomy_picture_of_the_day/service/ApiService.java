package com.panko.astronomy_picture_of_the_day.service;

import java.io.IOException;
import java.lang.System.Logger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class ApiService {
    private static final Logger logger = System.getLogger(ApiService.class.getName());
    private static final String NASA_URI = "https://api.nasa.gov/planetary/apod?";

    public HttpResponse<String> sendHttpRequest(String key) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(NASA_URI + "api_key=" + key))
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();

            logger.log(Logger.Level.INFO, "Request: {0}",
                    httpRequest.toString());

            HttpResponse<String> httpResponse = HttpClient.newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            logger.log(Logger.Level.INFO, "Response: {0}",
                    httpResponse.toString());

            return httpResponse;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            logger.log(Logger.Level.ERROR, e.getMessage());
        }

        return null;
    }
}
