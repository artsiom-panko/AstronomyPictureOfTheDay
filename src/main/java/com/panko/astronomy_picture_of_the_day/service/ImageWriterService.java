package com.panko.astronomy_picture_of_the_day.service;

import com.panko.astronomy_picture_of_the_day.entity.Picture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageWriterService {
    private static final System.Logger logger = System.getLogger(ImageWriterService.class.getName());

    public boolean writePictureToFolder(Picture picture) {
        try {
            BufferedImage image = ImageIO.read(new URL(picture.getImgUrl()));

            String[] splitUrl = picture.getImgUrl().split("/");
            String fileNameAndFormat = splitUrl[splitUrl.length - 1];
            String filePathAndName = "C:/Users/artsi/OneDrive/Pictures/space/" + fileNameAndFormat;
            picture.setLocalPath(filePathAndName);

            ImageIO.write(image, "jpg", new File(filePathAndName));

            logger.log(System.Logger.Level.DEBUG, "Image was successfully saved to: {0}", filePathAndName);
        } catch (IOException e) {
            logger.log(System.Logger.Level.ERROR, "Error during Image saving: {0}", e.getMessage());
            throw new RuntimeException(e);
        }

        return true;
    }
}
