package org.example.heart_disease_detector;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HeartDiseaseController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("You clicked the button!");
    }
}