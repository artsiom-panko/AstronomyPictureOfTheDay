package com.panko.apod.service;

import com.panko.apod.entity.Picture;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.time.LocalDate;

public class HttpResponseService {
    private static final System.Logger logger = System.getLogger(HttpResponseService.class.getName());

    public Picture parseHttpResponseToPicture(HttpResponse<String> response) {
        Picture picture = new Picture();
        JSONObject responseBody = new JSONObject(response.body());

        picture.setDate(LocalDate.parse(responseBody.getString("date")));
        picture.setTitle(responseBody.getString("title"));
        picture.setImgUrl(responseBody.getString("hdurl"));
        picture.setType(responseBody.getString("media_type"));
        picture.setDescription(responseBody.getString("explanation"));

        if (responseBody.has("copyright")) {
            picture.setCopyright(responseBody.getString("copyright"));
        } else {
            picture.setCopyright("NASA");
        }

        if (!"image".equals(picture.getType())) {
            throw new RuntimeException("Unsupported image type: " + picture.getType());
        }

        logger.log(System.Logger.Level.INFO, "Parsing result: {0}", picture);

        return picture;
    }
}
