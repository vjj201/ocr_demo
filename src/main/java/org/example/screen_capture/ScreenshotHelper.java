package org.example.screen_capture;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.example.ocr.OcrHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenshotHelper {
    private static final Logger LOGGER = Logger.getLogger(ScreenshotHelper.class.getName());
    private static final String FORMAT_NAME = "png";
    private static final String PATH_ROOT = "src/img/";
    private int seq = 0;

    private OcrHelper ocrHelper = new OcrHelper();

    private ScreenshotHelper() {
    }

    public static ScreenshotHelper buildHelper() {
        ScreenshotHelper screenshotHelper = new ScreenshotHelper();
        screenshotHelper.clearOldImg();
        return screenshotHelper;
    }

    private String imgNameBuilder() {
        ++seq;
        return "img_" + LocalDate.now() + "_" + seq + ".png";
    }

    public void clearOldImg() {
        File imgFolder = new File(PATH_ROOT);
        for (File file : Objects.requireNonNull(imgFolder.listFiles())) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
    }

    public void takeScreenshot(Stage stage) {
        saveSnapShot(stage.getScene(), (int) stage.getWidth(), (int) stage.getHeight());
    }

    public void takeScreenshot(Scene scene) {
        saveSnapShot(scene, (int) scene.getWidth(), (int) scene.getHeight());
    }

    public String takeScreenshotToText(Scene scene) {
        try {
            double width = scene.getWidth();
            double height = scene.getHeight();

            Robot robot = new Robot();
            Rectangle2D screenRect = new Rectangle2D(scene.getX(), scene.getY(), width, height);
            WritableImage writableImage = new WritableImage((int) width, (int) height);
            WritableImage robotScreenCapture = robot.getScreenCapture(writableImage, screenRect);

            BufferedImage bufferedImage = convertToBufferedImage(robotScreenCapture);
            return ocrHelper.imgToText(bufferedImage);

        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
        return "";
    }


    private void saveSnapShot(Scene scene, int width, int height) {
        // Create a writable image with the same dimensions as the stage
        WritableImage writableImage = new WritableImage(width, height);

        // Capture the screenshot
        scene.snapshot(writableImage);

        // Convert WritableImage to BufferedImage
        BufferedImage bufferedImage = convertToBufferedImage(writableImage);

        // Save the BufferedImage as a PNG file
        try {
            ImageIO.write(bufferedImage, FORMAT_NAME, new File(PATH_ROOT + imgNameBuilder()));
            LOGGER.log(Level.INFO, "截圖已保存！");
        } catch (IOException e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }

    private BufferedImage convertToBufferedImage(WritableImage writableImage) {
        int width = (int) writableImage.getWidth();
        int height = (int) writableImage.getHeight();

        // Create a new BufferedImage
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get the pixel reader from WritableImage
        PixelReader pixelReader = writableImage.getPixelReader();

        // Loop through each pixel and set it in the BufferedImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the ARGB color of the pixel
                javafx.scene.paint.Color color = pixelReader.getColor(x, y);

                // Convert the Color to an integer ARGB value
                int argb = ((int) (color.getOpacity() * 255) << 24) |
                        ((int) (color.getRed() * 255) << 16) |
                        ((int) (color.getGreen() * 255) << 8) |
                        ((int) (color.getBlue() * 255));

                // Set the pixel in the BufferedImage
                bufferedImage.setRGB(x, y, argb);
            }
        }

        return bufferedImage;
    }
}
