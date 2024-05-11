package org.example.heart_disease_detector.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.heart_disease_detector.HeartDiseaseApplication;

import java.io.EOFException;
import java.io.IOException;

public class evaluationRubricController {

    // Keeps track of what class called this scene
    public static String backParent;
    // "singlePatient"
    // "multiplePatients"
    // "patientInfo"
    // "patientAnalysis"

    @FXML
    Pane overlay_pane;  // Help screen overlay

    // Go to main scene
    @FXML
    protected void home_btn(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Go to page 1 scene from page 2
    @FXML
    protected void page_1_btn(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("evaluationRubric.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Go to page 2 scene from page 1
    @FXML
    protected void page_2_btn(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("evaluationRubric2.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Toggle help overlay visible/not visible
    @FXML
    protected void help_btn(){
        if(overlay_pane.isVisible()){
            overlay_pane.setVisible(false);
        }
        else{
            overlay_pane.setVisible(true);
        }
    }

    // Go back to previous scene
    @FXML
    protected void back_btn(ActionEvent event) throws IOException {
        // "singlePatient"
        // "multiplePatients"
        // "patientInfo"
        // "patientAnalysis"
        if(backParent.equals("singlePatient")){
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
        } else if (backParent.equals("multiplePatients")) {
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
        } else if (backParent.equals("patientInfo")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("patientInfo.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else if (backParent.equals("patientAnalysis")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("patientAnalysist.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Back parent is null " + backParent);
        }

    }
}
