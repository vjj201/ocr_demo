package org.example.page_controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Window;
import org.example.screen_capture.ScreenshotHelper;

public class ScreenshotWindowController {

    private final ScreenshotHelper screenshotHelper = ScreenshotHelper.buildHelper();
    private final TextAreaController textAreaController = new TextAreaController();

    @FXML
    private Button screenshotBtn;

    @FXML
    protected void takeScreenshot() {
        Scene scene = screenshotBtn.getScene();
        String resultText = screenshotHelper.takeScreenshotToText(scene);
        textAreaController.setText(resultText);
    }

}
