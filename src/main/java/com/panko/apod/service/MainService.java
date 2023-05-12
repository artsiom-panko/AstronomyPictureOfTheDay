package com.panko.apod.service;

import com.panko.apod.entity.Picture;
import com.panko.apod.util.PreferencesManager;
import com.panko.apod.util.ImageSaver;

import java.net.http.HttpResponse;

public class MainService {
    private final ApiService apiService = new ApiService();
    private final PreferencesManager preferencesManager = new PreferencesManager();
    private final ImageSaver imageSaver = new ImageSaver();
    private final HttpResponseHandlerService responseHandlerService = new HttpResponseHandlerService();

    public static final String NASA_API_KEY = "nasa.api.key";

//    public void launchMainThread() {
//
//        Picture picture = mainService.launchMainThread();
//        imageDescription.setText(picture.getDescription());
//
//
//        HttpResponse<String> httpResponse = apiService.sendHttpRequest(key);
//        Picture picture = responseHandlerService.parseHttpResponseToPicture(httpResponse);
//        imageWriterService.writePictureToFolder(picture);
//        WallpaperChangerService.setScreenImage(picture);
//
//        return picture;
//    }
//
//    public void test() {
//        String key = apiKeyService.readKey(NASA_API_KEY);
//        HttpResponse<String> httpResponse = apiService.sendHttpRequest(key);
//        Picture picture = responseHandlerService.parseHttpResponseToPicture(httpResponse);
//        imageWriterService.writePictureToFolder(picture);
//        WallpaperChangerService.setScreenImage(picture);
//
//        return picture;
//    }
}
