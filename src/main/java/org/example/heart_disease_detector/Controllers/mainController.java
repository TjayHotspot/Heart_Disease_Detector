package org.example.heart_disease_detector.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class mainController {
    @FXML
    private Label welcomeText;
    private Button singlePatient_btn;
    private Button multiPatient_btn;
    private Button checkForUpdate_btn;

    @FXML
    protected void singlePatient() {
        System.out.println("single patient was pressed");
    }

    @FXML
    protected void multiplePatients() {
        System.out.println("multiple patients was pressed");
    }

    @FXML
    protected void checkForUpdates() {
        System.out.println("Check for updates was pressed");
    }

}