package com.panko.astronomy_picture_of_the_day.service;

import com.panko.astronomy_picture_of_the_day.entity.Picture;

import java.net.http.HttpResponse;

public class MainService {
    private final ApiService apiService = new ApiService();
    private final ApiKeyService apiKeyService = new ApiKeyService();
    private final ImageWriterService imageWriterService = new ImageWriterService();
    private final HttpResponseHandlerService responseHandlerService = new HttpResponseHandlerService();

    private final static String NASA_API_KEY = "nasa.api.key";

    public Picture process() {
        String key = apiKeyService.readKey(NASA_API_KEY);
        HttpResponse<String> httpResponse = apiService.sendApiService(key);
        Picture picture = responseHandlerService.handleResponse(httpResponse);
        imageWriterService.writePictureToFolder(picture);
        WallpaperChangerService.setScreenImage(picture);

        return picture;
    }
}
