package com.panko.apod.service;

import java.io.IOException;
import java.lang.System.Logger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

// TODO Rename this class
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

            HttpResponse<String> httpResponse = HttpClient
                    .newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            logger.log(Logger.Level.INFO, "Response Headers: {0} \n Body: {1}",
                    httpResponse.headers(), httpResponse.body());

            return httpResponse;
        } catch (HttpTimeoutException | URISyntaxException exception) {
            new AlertService().showErrorAlertAndCloseApp(
                    "Picture service is not available now.\nPlease, try later", exception);
        } catch (IOException exception) {
            new AlertService().showErrorAlertAndCloseApp(
                    "Unknown IOException exception.\nPlease, try later", exception);
        } catch (InterruptedException exception) {
            // TODO fix that catch
            new AlertService().showErrorAlertAndCloseApp(
                    "Unknown InterruptedException.\nPlease, try later", exception);
        }

        return null;
    }

    public HttpResponse<String> sendHttpGetRequest(String URI) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(URI))
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();

            logger.log(Logger.Level.INFO, "Request: {0}",
                    httpRequest.toString());

            HttpResponse<String> httpResponse = HttpClient
                    .newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            logger.log(Logger.Level.INFO, "Response Headers: {0} \n Body: {1}",
                    httpResponse.headers(), httpResponse.body());

            return httpResponse;
        } catch (HttpTimeoutException | URISyntaxException exception) {
            new AlertService().showErrorAlertAndCloseApp(
                    "Picture service is not available now.\nPlease, try later", exception);
        } catch (IOException exception) {
            new AlertService().showErrorAlertAndCloseApp(
                    "Unknown IOException exception.\nPlease, try later", exception);
        } catch (InterruptedException exception) {
            // TODO fix that catch
            new AlertService().showErrorAlertAndCloseApp(
                    "Unknown InterruptedException.\nPlease, try later", exception);
        }

        return null;
    }
}
