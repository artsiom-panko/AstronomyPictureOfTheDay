package com.panko.astronomy_picture_of_the_day.service;

import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseHandlerServiceTest {

    HttpResponseHandlerService responseHandlerService = new HttpResponseHandlerService();

    @Test
    void handleResponse() throws IOException {
        Path path = Paths.get("src/test/java/com/panko/astronomy_picture_of_the_day/service/resources/responseBody.json");
        String responseFile = String.join("", Files.readAllLines(path));

        HttpResponse<String> httpResponse = generateHttpResponse(responseFile);
        responseHandlerService.handleResponse(httpResponse);
    }

    private static HttpResponse<String> generateHttpResponse(String body) {
        return new HttpResponse<>() {
            @Override
            public int statusCode() {
                return 0;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return body;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
    }
}