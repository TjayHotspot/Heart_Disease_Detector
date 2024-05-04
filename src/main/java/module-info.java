module org.example.heart_disease_detector {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.heart_disease_detector to javafx.fxml;
    exports org.example.heart_disease_detector;
    exports org.example.heart_disease_detector.Controllers;
    opens org.example.heart_disease_detector.Controllers to javafx.fxml;
}