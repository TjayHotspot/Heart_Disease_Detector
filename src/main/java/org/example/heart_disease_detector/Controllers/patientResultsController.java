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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class patientResultsController implements Initializable {
    @FXML
    Pane overlay_pane;                                      // Help scene overlay

    @FXML
    TextField patient_results_name;                         // Patient Name scene text field
    @FXML
    TextField patient_results_HD;                           // Patient results scene text field (Has heart disease or not)
    @FXML
    TextField patient_results_probability;                  // Patient results probability scene text field (In percentage)

    private static boolean scriptCalled = false;            // Local variable to track when the python script is called

    // Go to main scene
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

    // View current Patient Info
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

    // View current patient info analysis
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

    // Set the results from python script to scene text field
    @FXML
    protected void set_results_table() {
        try {
            // Read the CSV file
            BufferedReader fileReader = new BufferedReader(new FileReader(FileManager.getInstance().get_patientResults()));

            // Read the record
            String resultLine = fileReader.readLine();

            // Split the result line into values
            String[] values = resultLine.split(", ");
            // Extract the name, prediction, and probability
            String Name = values[0].replace("('", "").replace("'", "") + " " +
                            values[1].replaceAll("'", "");
            patient_results_name.setText(Name);         // Set Name
            if(values[2].equals("1")){
                patient_results_HD.setText("Present");  // Set If has heart disease
            }
            else if(values[2].equals("0")){
                patient_results_HD.setText("Non Present");// Set If not has heart disease
            }
            patient_results_probability.setText(values[3].replaceAll("'", "").replace(")", "") + "% ");

            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  Run on the start of this scene call
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // If python script not called
        if (!scriptCalled) {
            try {
                // Read patient info
                BufferedReader br = new BufferedReader(new FileReader(FileManager.getInstance().get_currentPatient()));
                String patientInfo = br.readLine();
                br.close();

                // Load ONNX model from resources
                InputStream onnxInputStream = HeartDiseaseApplication.class.getResourceAsStream("/org/example/heart_disease_detector/Heart_Disease_model.onnx");
                File tempOnnxFile = File.createTempFile("Heart_Disease_model", ".onnx");
                Files.copy(onnxInputStream, tempOnnxFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                String onnxModelPath = tempOnnxFile.getAbsolutePath();

                // Get input stream for the Python script within the JAR
                InputStream scriptInputStream = HeartDiseaseApplication.class.getResourceAsStream("/org/example/heart_disease_detector/useModel.py");

                // Write script content to a temporary file
                File tempScriptFile = File.createTempFile("useModel", ".py");
                Files.copy(scriptInputStream, tempScriptFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Construct command to execute the Python script
                String[] command = {"python", tempScriptFile.getAbsolutePath(), onnxModelPath, patientInfo};

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

                // Read Output and write output to patientResults
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String readerLine;
                File patientResults = FileManager.getInstance().get_patientResults();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(patientResults));
                while ((readerLine = reader.readLine()) != null){
                    System.out.println(readerLine);
                    bufferedWriter.write(readerLine);
                }
                bufferedWriter.close();

                // Wait for the process to finish
                int exitCode = process.waitFor();
                System.out.println("Python script exited with code: " + exitCode);

                // Close streams
                reader.close();
                scriptCalled = true;

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        set_results_table();    // Set results table once complete with python script
    }
}