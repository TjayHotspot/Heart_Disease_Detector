package org.example.heart_disease_detector.Controllers;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.heart_disease_detector.HeartDiseaseApplication;
import org.example.heart_disease_detector.Patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class patientInfoController implements Initializable{
    public static String patientInfoBackParent;
    // patientResults
    // patientList

    @FXML
    Pane overlay_pane;
    @FXML TextField first_name;
    @FXML TextField last_name;
    @FXML TextField age;
    @FXML TextField sex;
    @FXML TextField chest_pain;
    @FXML TextField BP;
    @FXML TextField Chol;
    @FXML TextField FBS_over_120;
    @FXML TextField EKG;
    @FXML TextField max_HR;
    @FXML TextField exercise_angina;
    @FXML TextField ST_depression;
    @FXML TextField ST_slope;
    @FXML TextField Fluro;
    @FXML TextField thallium;



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
    protected void rubric_btn(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("evaluationRubric.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            evaluationRubricController.backParent = "patientInfo";
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void back_btn(ActionEvent event) throws IOException {
        // patientResults
        // patientList
        if(patientInfoBackParent.equals("patientResults")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("patientResults.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else if(patientInfoBackParent.equals("patientList")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("patientList.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
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
    protected void loadPatientInfo(){
        String line = "";
        String csvDelimiter = ",";
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/org/example/heart_disease_detector/currentPatient.csv"))) {
            while ((line = br.readLine()) != null) {
                // Split the line by the delimiter
                String[] data = line.split(csvDelimiter);
                first_name.setText(data[0]);
                last_name.setText(data[1]);
                age.setText(data[2]);
                sex.setText(data[3]);
                chest_pain.setText(data[4]);
                BP.setText(data[5]);
                Chol.setText(data[6]);
                FBS_over_120.setText(data[7]);
                EKG.setText(data[8]);
                max_HR.setText(data[9]);
                exercise_angina.setText(data[10]);
                ST_depression.setText(data[11]);
                ST_slope.setText(data[12]);
                Fluro.setText(data[13]);
                thallium.setText(data[14]);
                // Process each column of the CSV data
                System.out.println(); // Move to the next line

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       loadPatientInfo();
    }
}
