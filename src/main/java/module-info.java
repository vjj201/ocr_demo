module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires tess4j;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.page_controller;
    opens org.example.page_controller to javafx.fxml;
}