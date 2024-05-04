package org.example.heart_disease_detector.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.example.heart_disease_detector.HeartDiseaseApplication;

import java.io.IOException;

public class mainController {
    @FXML
    private Label welcomeText;
    private Button singlePatient_btn;
    private Button multiPatient_btn;
    private Button checkForUpdate_btn;
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    protected void singlePatient(ActionEvent event ) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("singlePatient.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
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