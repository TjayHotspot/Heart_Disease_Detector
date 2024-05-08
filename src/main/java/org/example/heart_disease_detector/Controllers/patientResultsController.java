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

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class patientResultsController implements Initializable {
    @FXML
    Pane overlay_pane;

    @FXML
    TextField patient_results_name;
    @FXML
    TextField patient_results_HD;
    @FXML
    TextField patient_results_probability;

    private static boolean scriptCalled = false;

    @FXML
    protected void home_btn(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scriptCalled = false;
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void patient_info_btn(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("patientInfo.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            patientInfoController.patientInfoBackParent = "patientResults";
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void analyze_info_btn(ActionEvent event) throws IOException {
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
    protected void set_results_table() {
        try {
            // Read the CSV file
            BufferedReader fileReader = new BufferedReader(new FileReader("src/Shared_CSV/patientResults.csv"));

            // Read the record
            String resultLine = fileReader.readLine();

            // Split the result line into values
            String[] values = resultLine.split(",");

            // Extract the name, prediction, and probability
            String Name = values[0] + " " + values[1];
            patient_results_name.setText(Name);         // Set Name
            if(values[2].equals("1")){
                patient_results_HD.setText("Present");  // Set If has heart disease
            }
            else if(values[2].equals("0")){
                patient_results_HD.setText("Non Present");// Set If not has heart disease
            }

            String probability = values[3] + "%";
            patient_results_probability.setText(probability); // Set Probability

            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!scriptCalled) {
            try {
                // Command to execute the Python script
                String[] command = {"python", "src/python/useModel.py"};

                // Create ProcessBuilder instance with the command
                ProcessBuilder pb = new ProcessBuilder(command);

                // Start the process
                Process process = pb.start();

                // Read the error stream of the process
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    System.err.println("Error: " + errorLine);
                }

                // Wait for the process to finish
                int exitCode = process.waitFor();
                System.out.println("Python script exited with code: " + exitCode);

                scriptCalled = true;

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        set_results_table();
    }
}