package com.panko.astronomy_picture_of_the_day.service;

import com.panko.astronomy_picture_of_the_day.entity.Picture;

import java.net.http.HttpResponse;

public class MainService {
    private final ApiService apiService = new ApiService();
    private final ImageWriterService imageWriterService = new ImageWriterService();
    private final HttpResponseHandlerService responseHandlerService = new HttpResponseHandlerService();

    public Picture process() {
        HttpResponse<String> httpResponse = apiService.sendApiService();
        Picture picture = responseHandlerService.handleResponse(httpResponse);
        imageWriterService.writePictureToFolder(picture);
        WallpaperChangerService.setScreenImage(picture);

        return picture;
    }
}
