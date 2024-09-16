package org.example.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OcrHelper {
    private static final Logger LOGGER = Logger.getLogger(OcrHelper.class.getName());

    public String imgToText(BufferedImage bufferedImage) {
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("en");
        tesseract.setOcrEngineMode(1);

        String result = "";
        try {
            result = tesseract.doOCR(bufferedImage);
        } catch (TesseractException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }

        return result;
    }
}
