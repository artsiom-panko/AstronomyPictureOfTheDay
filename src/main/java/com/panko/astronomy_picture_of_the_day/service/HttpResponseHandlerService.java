package com.panko.astronomy_picture_of_the_day.service;

import com.panko.astronomy_picture_of_the_day.entity.Picture;
import org.json.JSONObject;

import java.net.http.HttpResponse;

public class HttpResponseHandlerService {

    public Picture handleResponse(HttpResponse<String> response) {
        Picture picture = new Picture();

        JSONObject responseBody = new JSONObject(response.body());

        picture.setTitle(responseBody.getString("title"));
        picture.setImgUrl(responseBody.getString("hdurl"));
        picture.setType(responseBody.getString("media_type"));
        picture.setDescription(responseBody.getString("explanation"));

        if (!"image".equals(picture.getType())) {
            throw new RuntimeException("Unsupported image type: " + picture.getType());
        }

        return picture;
    }
}
