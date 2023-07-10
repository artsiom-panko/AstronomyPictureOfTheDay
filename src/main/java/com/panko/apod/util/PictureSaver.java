package com.panko.apod.util;

import com.panko.apod.entity.Picture;
import com.panko.apod.service.AlertService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.panko.apod.util.PreferencesManager.APP_ABSOLUTE_PATH;

public class PictureSaver {
    private final AlertService alertService = new AlertService();
    private final PreferencesManager preferencesManager = new PreferencesManager();

    private static final System.Logger logger = System.getLogger(PictureSaver.class.getName());

    public void savePictureToFolder(Picture picture) {
        try {
            BufferedImage image = ImageIO.read(new URL(picture.getImgUrl()));

            String[] splitUrl = picture.getImgUrl().split("/");
            String fileName = splitUrl[splitUrl.length - 1];
            String absolutePath = preferencesManager.readKey(APP_ABSOLUTE_PATH)
                    .concat(fileName);
            picture.setLocalPath(absolutePath);

            File fileFir = new File(absolutePath);
            fileFir.mkdirs();

            ImageIO.write(image, "jpg", fileFir);

            logger.log(System.Logger.Level.INFO, "Image was successfully saved to: {0}", absolutePath);
        } catch (IOException exception) {
            // TODO think about keeping try-catch block inside each Class or moving to MainController
            logger.log(System.Logger.Level.ERROR, "Error during Image saving: {0}", exception.getMessage());
            alertService.showErrorAlertAndCloseApp("Error during Image saving", exception);
        }
    }
}
