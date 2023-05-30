package com.panko.apod.util;

import com.panko.apod.entity.Picture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.panko.apod.util.PreferencesManager.PICTURES_FOLDER;

public class PictureSaver {
    private final PreferencesManager preferencesManager = new PreferencesManager();

    private static final System.Logger logger = System.getLogger(PictureSaver.class.getName());

    // TODO add exception handler for this one
    public boolean savePictureToFolder(Picture picture) {
        try {
            BufferedImage image = ImageIO.read(new URL(picture.getImgUrl()));

            String[] splitUrl = picture.getImgUrl().split("/");
            String fileName = splitUrl[splitUrl.length - 1];
            String absolutePath = preferencesManager.readKey(PICTURES_FOLDER)
                    .concat(fileName);
            picture.setLocalPath(absolutePath);

            File fileFir = new File(absolutePath);
            fileFir.mkdirs();

            ImageIO.write(image, "jpg", fileFir);

            logger.log(System.Logger.Level.INFO, "Image was successfully saved to: {0}", absolutePath);
        } catch (IOException e) {
            logger.log(System.Logger.Level.ERROR, "Error during Image saving: {0}", e.getMessage());
            return false;
        }

        return true;
    }
}
