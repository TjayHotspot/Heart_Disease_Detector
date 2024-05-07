package org.example.heart_disease_detector.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.heart_disease_detector.HeartDiseaseApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class singlePatientController {


    @FXML
    Pane overlay_pane;
    @FXML
    private TextField first_name_box;
    @FXML
    private TextField last_name_box;
    @FXML
    private TextField age_box;
    @FXML
    private TextField BP_box;
    @FXML
    private TextField Chol_box;
    @FXML
    private TextField EKG_box;
    @FXML
    private TextField max_HR_box;
    @FXML
    private TextField ST_Depression_box;
    @FXML
    private ChoiceBox sex_dropdown;
    @FXML
    private ChoiceBox chest_pain_dropdown;
    @FXML
    private ChoiceBox FBS_over_120_dropdown;
    @FXML
    private ChoiceBox Exercise_angina_dropdown;
    @FXML
    private ChoiceBox ST_slope_dropdown;
    @FXML
    private ChoiceBox Vessels_Fluro_dropdown;
    @FXML
    private ChoiceBox Thallium_dropdown;
    @FXML
    private Text prompt_numerical;
    @FXML
    private Text prompt_empty;

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
            evaluationRubricController.backParent = "singlePatient";
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void view_results_btn(ActionEvent event) throws IOException {
        if(forum_incomplete()){

        }
        else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("patientResults.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                evaluationRubricController.backParent = "singlePatient";
                stage.show();

            } catch (IOException e) {
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
    protected boolean forum_incomplete() throws IOException {
        boolean is_incomplete = false;
        boolean any_empty = false;
        boolean any_non_numbers = false;
        // Text Fields in array
        List<TextField> textFieldList = Arrays.asList(first_name_box, last_name_box, age_box, BP_box, Chol_box, EKG_box, max_HR_box, ST_Depression_box);
        List<String> textStringsList = new ArrayList<>();
        List<Integer> integersList = new ArrayList<>();
        Double ST_depression = 0.0;
        // Iterate Through all textFields
        for (TextField textField : textFieldList) {
            // Check For Empty TextFields
            if(!textField.getText().isEmpty()){
                // Check integer purpose text fields if correct data type
                if (textField == age_box || textField == BP_box || textField == Chol_box || textField == EKG_box ||
                        textField == max_HR_box) {
                    try {
                        integersList.add(Integer.parseInt(textField.getText()));
                        textField.setStyle("-fx-border-color: default;");

                    } catch (NumberFormatException e) {
                        // If any text cannot be converted to an integer, handle the exception
                        System.err.println(textField + " is not a number");
                        e.printStackTrace();
                        textField.setStyle("-fx-border-color: orange;");
                        any_non_numbers = true;
                        is_incomplete = true;
                    }
                } else if (textField == ST_Depression_box) {
                    try {
                        ST_depression = Double.parseDouble(textField.getText());
                        textField.setStyle("-fx-border-color: default;");

                    } catch (NumberFormatException e) {
                        // If any text cannot be converted to an integer, handle the exception
                        System.err.println(textField + " is not a double");
                        e.printStackTrace();
                        textField.setStyle("-fx-border-color: orange;");
                        textField.setText("");
                        textField.setPromptText("Needs to be in '1.23' Format");
                        is_incomplete = true;
                        any_non_numbers = true;
                        prompt_numerical.setVisible(true);
                    }
                    
                }
                else{
                    textField.setStyle("-fx-border-color: default;"); // Set UI Border to default
                    textStringsList.add(textField.getText());         // Add to string list
                }
            }
            else{
                textField.setStyle("-fx-border-color: red;");
                any_empty = true;
                is_incomplete = true;
            }
        }

        // List of all choice boxes in forum
        List<ChoiceBox> choiceBoxeList = Arrays.asList(sex_dropdown, chest_pain_dropdown, FBS_over_120_dropdown, Exercise_angina_dropdown,
                ST_slope_dropdown, Vessels_Fluro_dropdown, Thallium_dropdown);

        // Iterate Through all choiceBoxes
        for (ChoiceBox choiceBox : choiceBoxeList) {
            // Check For Empty choice boxes
            if(choiceBox.getValue() == null){
                is_incomplete = true;
                any_empty = true;
                choiceBox.setStyle("-fx-border-color: red;");
            }
            else{
                choiceBox.setStyle("-fx-border-color: default;"); // Set UI border to default
                textStringsList.add(choiceBox.getValue().toString());
            }

        }
        if(any_empty){
            prompt_empty.setVisible(true);
        }
        if(any_non_numbers){
            prompt_numerical.setVisible(true);
        }
        data_to_csv(textStringsList, integersList, ST_depression);
        return is_incomplete;
    }

    @FXML
    protected void data_to_csv(List<String> stringDataList, List<Integer> integerDataList, Double stDepression) throws IOException {
        // Path to your CSV file
        String csvFilePath = "src/main/resources/org/example/heart_disease_detector/patientData.csv";

        // Clear the CSV file by truncating it
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, false))) {
            // Truncate the file by opening it in write mode (false) and not appending data
            writer.write(""); // Write an empty string to clear the file
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


