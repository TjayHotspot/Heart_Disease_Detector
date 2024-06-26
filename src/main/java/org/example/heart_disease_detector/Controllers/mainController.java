package org.example.heart_disease_detector.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.example.heart_disease_detector.HeartDiseaseApplication;

import java.io.IOException;

public class mainController {

    @FXML
    Pane update_page;   // Help screen overlay

    // Go to singlePatient scene
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

    // Go to multiplePatients scene
    @FXML
    protected void multiplePatients(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("multiplePatients.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check for update button handler
    @FXML
    protected void checkForUpdates() {
        update_page.setVisible(true);
    }

    // Close the 'update' overlay
    @FXML
    protected void close_update() {
        update_page.setVisible(false);
    }


}