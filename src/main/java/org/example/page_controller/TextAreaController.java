package org.example.page_controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class TextAreaController {

    @FXML
    private TextArea textArea;

    public void setText(String context) {
        textArea.setText(context);
    }
}
