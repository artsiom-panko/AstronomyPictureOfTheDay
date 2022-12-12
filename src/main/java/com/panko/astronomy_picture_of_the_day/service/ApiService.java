package com.panko.astronomy_picture_of_the_day.service;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class ApiService {
    private static final String API_KEY = "5ym3WNJRFkk6Anuc618Qj5OWxL5pPLlyzdrMM8zp";
    private static final String URI = "https://api.nasa.gov/planetary/apod?";

    public String getImage() {

        String filePathAndName = "";

        try {
            URI uri = new URI(URI + "api_key=" + API_KEY);
            System.out.println("URI: " + uri);

            HttpRequest imageMetadataRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.of(25, SECONDS))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(imageMetadataRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject obj = new JSONObject(response.body());

            String imageUrl = obj.getString("hdurl");

            BufferedImage image = ImageIO.read(new URL(imageUrl));

            String[] split = imageUrl.split("/");
            String fileNameAndExtension = split[split.length - 1];
            filePathAndName = "C:/Users/Public/Pictures" + fileNameAndExtension;

            ImageIO.write(image, "jpg", new File(filePathAndName));

            System.out.println("RESPONSE: " + response);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return filePathAndName;
    }
}
