package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Objects;

public class App extends Application {

    private static final String MAIN_PAGE = "main_page.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(MAIN_PAGE)));

        Scene scene = new Scene(root);

        primaryStage.setTitle("OCR APP");
        primaryStage.setScene(scene);
        primaryStage.setOpacity(0.8);
        primaryStage.show();
    }

}
