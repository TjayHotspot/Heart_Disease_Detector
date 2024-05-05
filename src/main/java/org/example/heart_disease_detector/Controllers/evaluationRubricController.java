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
    public static String backParent;
    // "singlePatient"
    // "multiplePatients"
    // "patientInfo"
    // "patientAnalysis"
    @FXML
    Pane overlay_pane;
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

    @FXML
    protected void help_btn(){
        if(overlay_pane.isVisible()){
            overlay_pane.setVisible(false);
        }
        else{
            overlay_pane.setVisible(true);
        }
    }

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
