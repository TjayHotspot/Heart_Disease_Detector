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
import org.example.heart_disease_detector.FileManager;
import org.example.heart_disease_detector.HeartDiseaseApplication;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class patientAnalysisController implements Initializable{
    @FXML
    Pane overlay_pane;                          // Toggle help overlay visible/not visible

// local variables
    private String first_name;                  // Patient First Name
    private String last_name;                   // Patient Last Name
    private String age;                         // Patient Age
    private String sex;                         // Patient Gender
    private String chest_pain;                  // Patient Chest Pain Type
    private String BP;                          // Patient Blood Pressure
    private String Chol;                        // Patient Cholesterol
    private String FBS_over_120;                // Patient Fasting Blood Sugar of 120 (Bool)
    private String EKG;                         // Patient EKG
    private String max_HR;                      // Patient Max Heart Rate
    private String exercise_angina;             // Patient Exercise angina
    private String ST_depression;               // Patient ST Depression
    private String ST_slope;                    // Patient ST Slope
    private String Fluro;                       // Patient Fluro
    private String thallium;                    // Patient Thallium

// Patient Info Variable    (scene)
    @FXML TextField first_name_text;
    @FXML TextField last_name_text;
    @FXML TextField age_text;
    @FXML TextField sex_text;
    @FXML TextField chest_pain_text;
    @FXML TextField BP_text;
    @FXML TextField Chol_text;
    @FXML TextField FBS_over_120_text;
    @FXML TextField EKG_text;
    @FXML TextField max_HR_text;
    @FXML TextField exercise_angina_text;
    @FXML TextField ST_depression_text;
    @FXML TextField ST_slope_text;
    @FXML TextField Fluro_text;
    @FXML TextField thallium_text;

// Patient Analysis Variable (scene)
    @FXML TextField chest_pain_A;
    @FXML TextField BP_A;
    @FXML TextField Chol_A;
    @FXML TextField FBS_over_120_A;
    @FXML TextField EKG_A;
    @FXML TextField max_HR_A;
    @FXML TextField exercise_angina_A;
    @FXML TextField ST_depression_A;
    @FXML TextField ST_slope_A;
    @FXML TextField Fluro_A;
    @FXML TextField thallium_A;

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

    // Go to evaluationRubric scene
    @FXML
    protected void rubric_btn(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("evaluationRubric.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            evaluationRubricController.backParent = "patientAnalysis";
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Go to previous scene
    @FXML
    protected void back_btn(ActionEvent event) throws IOException {
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

    // Load current patient info and set to local variables
    @FXML
    protected void loadPatientInfo(){
        String line = "";
        String csvDelimiter = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(FileManager.getInstance().get_currentPatient()))) {
            while ((line = br.readLine()) != null) {
                // Split the line by the delimiter
                String[] data = line.split(csvDelimiter);
                first_name = data[0];
                last_name = data[1];
                age = data[2];
                sex = data[3];
                chest_pain = data[4];
                BP = data[5];
                Chol = data[6];
                FBS_over_120 = data[7];
                EKG = data[8];
                max_HR = data[9];
                exercise_angina = data[10];
                ST_depression = data[11];
                ST_slope = data[12];
                Fluro = data[13];
                thallium = data[14];
                // Process each column of the CSV data
                System.out.println(); // Move to the next line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Set Patient Info text field
    @FXML
    protected void setPatientInfo(){
        first_name_text.setText(first_name);
        last_name_text.setText(last_name);
        age_text.setText(age);
        sex_text.setText(sex);
        chest_pain_text.setText(chest_pain);
        BP_text.setText(BP);
        Chol_text.setText(Chol);
        FBS_over_120_text.setText(FBS_over_120);
        EKG_text.setText(EKG);
        max_HR_text.setText(max_HR);
        exercise_angina_text.setText(exercise_angina);
        ST_depression_text.setText(ST_depression);
        ST_slope_text.setText(ST_slope);
        Fluro_text.setText(Fluro);
        thallium_text.setText(thallium);
        analyzePatientInfo();
    }

    // Analyze and set analysis fields
    @FXML
    protected void analyzePatientInfo(){
        // Check chest_pain
        switch (chest_pain){
            case "Typical angina":
                chest_pain_A.setText("Related");
                break;

            case "Atypical angina":
                chest_pain_A.setText("Chance of Related");
                break;

            case "Non-anginal pain":
                chest_pain_A.setText("Non Related");
                break;

            case "Asymptomatic":
                chest_pain_A.setText("Healthy");
                break;
        }

        // Check Blood Preassure
        if(Integer.parseInt(BP) < 90){
            BP_A.setText("Low BP");
        }
        else if(Integer.parseInt(BP) > 135){
            BP_A.setText("High BP");
        }
        else{ BP_A.setText("Average");}

        // Check Cholesteral
        if(Integer.parseInt(Chol) <= 70){
            Chol_A.setText("Healthy");
        }
        else if (Integer.parseInt(Chol) > 71 && Integer.parseInt(Chol) < 160){
            Chol_A.setText("Semi-High");
        }
        else {
            Chol_A.setText("Unhealthy");
        }

        // Check FBS Over 120
        switch (FBS_over_120){
            case "Yes":
                FBS_over_120_A.setText("High");
                break;
            default:
                FBS_over_120_A.setText("Healthy");
                break;
        }

        // Check EKG Results
        if(Integer.parseInt(EKG) == 0){
            EKG_A.setText("Healthy");
        } else if (Integer.parseInt(EKG) == 1) {
            EKG_A.setText("Mild");
        } else if (Integer.parseInt(EKG) == 2) {
            EKG_A.setText("Unhealthy");
        }
        else{
            EKG_A.setText("Severe");
        }

        // Check Max HR
        if(Integer.parseInt(age) <= 12){
            if(Integer.parseInt(max_HR) < 120){
                max_HR_A.setText("Low");
            }
            else if(Integer.parseInt(max_HR) >= 120 && Integer.parseInt(max_HR) < 160){
                max_HR_A.setText("Average");
            }
            else{
                max_HR_A.setText("High");
            }
        }
        else if(Integer.parseInt(age) > 12 && Integer.parseInt(age) < 19){
            if(Integer.parseInt(max_HR) < 110){
                max_HR_A.setText("Low");
            }
            else if(Integer.parseInt(max_HR) >= 110 && Integer.parseInt(max_HR) < 150){
                max_HR_A.setText("Average");
            }
            else{
                max_HR_A.setText("High");
            }
        }
        else if(Integer.parseInt(age) > 19 && Integer.parseInt(age) < 64){
            if(Integer.parseInt(max_HR) < 100){
                max_HR_A.setText("Low");
            }
            else if(Integer.parseInt(max_HR) >= 100 && Integer.parseInt(max_HR) < 150){
                max_HR_A.setText("Average");
            }
            else{
                max_HR_A.setText("High");
            }
        }
        else{
            if(Integer.parseInt(max_HR) < 90){
                max_HR_A.setText("Low");
            }
            else if(Integer.parseInt(max_HR) >= 90 && Integer.parseInt(max_HR) < 130){
                max_HR_A.setText("Average");
            }
            else{
                max_HR_A.setText("High");
            }
        }

        // Check Exercise angina
        switch (exercise_angina){
            case "Yes":
                exercise_angina_A.setText("Concern");
                break;
            case "No":
                exercise_angina_A.setText("Good");
                break;
        }

        // ST Depression
        if(Double.parseDouble(ST_depression) == 0){
            ST_depression_A.setText("Normal");
        }
        else if(Double.parseDouble(ST_depression) < 1){
            ST_depression_A.setText("Mild");
        }
        else if(Double.parseDouble(ST_depression) >= 1 && Double.parseDouble(ST_depression) <= 2){
            ST_depression_A.setText("Moderate");
        }
        else {
            ST_depression_A.setText("Severe");
        }

        // Check Slope of ST
        switch (ST_slope){
            case "Flat":
                ST_slope_A.setText("Normal");
                break;
            case "Upsloping":
                ST_slope_A.setText("Normal");
                break;
            case "Downsloping":
                ST_slope_A.setText("Unhealthy");
        }

        // Check Fluro
        if(Integer.parseInt(Fluro) == 1){
            Fluro_A.setText("Normal");
        }
        else{
            Fluro_A.setText("Unhealthy");
        }

        // Check Thallium
        switch (thallium){
            case "Normal":
                thallium_A.setText("Normal");
                break;
            case "Fixed defect":
                thallium_A.setText("Unhealthy");
                break;
            case "Reversable defect":
                thallium_A.setText("Unhealthy");
                break;
        }
    }
    // Run on the start of this scene call
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPatientInfo();
        setPatientInfo();
    }
}
