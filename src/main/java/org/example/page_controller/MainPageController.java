package org.example.page_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPageController {
    private static final String SCREENSHOT_PAGE = "screenshot_window.fxml";
    private static final String TEXT_AREA_PAGE = "text_area.fxml";
    private final List<Stage> stageList = new ArrayList<>();
    private double screenBoundsWidth;
    private double screenBoundsHeight;

    @FXML
    private Button startBtn;

    @FXML
    private Button stopBtn;

    @FXML
    private Button openScreenshotPageBtn;

    @FXML
    private Button openTextAreaBtn;

    @FXML
    private void openScreenshotPage() throws IOException {
        Stage stage = getNewPageStage(SCREENSHOT_PAGE);
        stage.setOpacity(0.6);

        stageList.add(stage);
        stopBtn.setDisable(false);
        openScreenshotPageBtn.setDisable(true);
        setStageLocation(stage, false);
        stage.show();
    }

    @FXML
    private void openTextArea() throws IOException {
        Stage stage = getNewPageStage(TEXT_AREA_PAGE);
        stageList.add(stage);
        stopBtn.setDisable(false);
        openTextAreaBtn.setDisable(true);

        setStageLocation(stage, true);
        stage.show();
    }

    private Stage getNewPageStage(String page) throws IOException {
        // 載入新視窗的 FXML 檔案
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(page));

        Parent root = fxmlLoader.load();

        // 創建新視窗
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
//        stage.initStyle(StageStyle.TRANSPARENT);

        return stage;
    }

    private void setStageLocation(Stage stage, boolean isRight) {

        double xPos;
        if (isRight) {
            xPos = screenBoundsWidth;
        } else {
            xPos = 0;
        }
        double yPos = screenBoundsHeight / 2;

        stage.setX(xPos);
        stage.setY(yPos);
    }

    @FXML
    private void start() throws IOException {
        // 按鈕操作
        startBtn.setDisable(true);
        stopBtn.setDisable(false);

        // 取系統視窗大小
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        screenBoundsWidth = screenBounds.getWidth();
        screenBoundsHeight = screenBounds.getHeight();

        // 開啟功能分頁
        openScreenshotPage();
        openTextArea();
    }

    @FXML
    private void stop() {
        if (stageList.isEmpty()) {
            return;
        }

        for (Stage stage : stageList) {
            if (stage != null) {
                stage.close();
            }
        }

        stageList.clear();
        openScreenshotPageBtn.setDisable(false);
        openTextAreaBtn.setDisable(false);
        startBtn.setDisable(false);
        stopBtn.setDisable(true);
    }

}
