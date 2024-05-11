package org.example.heart_disease_detector.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.heart_disease_detector.FileManager;
import org.example.heart_disease_detector.HeartDiseaseApplication;

import java.io.*;

public class multiplePatientsController {
    @FXML
    Pane overlay_pane;                  // Help screen overlay
    @FXML
    Label file_status;                  // Displays to user if file is uploaded or not
    @FXML
    Button uploadCSV_btn;               // Button to add csv file
    @FXML
    Rectangle prompt_upload_box;        // Error box to prompt user to add file
    @FXML
    Text prompt_upload;                 // Error text to prompt user to add file
    @FXML
    Text csv_fail;                      // Error text for invalid csv file

    boolean fileUploaded = false;       // fileUploaded tracks if user uploaded file (default = false)
    int recordCount = 0;                // keeps track of rows in users csv file

    // Go to main scene
    @FXML
    protected void home_btn(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

            // Reset variables to default
            fileUploaded = false;
            recordCount = 0;
            file_status.setText("No File Added");

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
            System.out.print(HeartDiseaseApplication.class.getResource("evaluationRubric.fxml").getPath());
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            evaluationRubricController.backParent = "multiplePatients";
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // View patients in a list from the imported csv data
    @FXML
    protected void patientList_btn(ActionEvent event) throws IOException {
        if (fileUploaded) {
            try {
                // Set the 'patientListController' recordCount variable
                patientListController.record_count = recordCount;
                FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("patientList.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
                csv_fail.setVisible(true);
                recordCount = 0;
            }
        }
        else{
            // Prompt for csv file upload to continue
            prompt_upload.setVisible(true);
            prompt_upload_box.setVisible(true);
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

    // Method to handle the file upload action
    public void uploadFile_btn() {
        csv_fail.setVisible(false);
        // Create file chooser to allow user to select csv file from directory
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Process the selected CSV file
            processCSVFile(selectedFile);
        }
    }

    // Method to process the selected CSV file
    private void processCSVFile(File file) {
        boolean success = false;

        // Read user uploaded csv and write it to local file 'patientData'
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileManager.getInstance().get_patientData()));) {
                writer.write("");
                while ((line = br.readLine()) != null) {
                    // Process each line of the CSV file
                    writer.write(line);
                    writer.newLine();
                    recordCount++;
                }
            }
            catch (IOException e) {
                    e.printStackTrace();
            }

            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Display file status and set local variable
        if(success){
            fileUploaded = true;
            file_status.setText("File Added");
            file_status.setAlignment(Pos.CENTER);
            file_status.setStyle("-fx-background-color: lightgreen");
            prompt_upload_box.setVisible(false);
            prompt_upload.setVisible(false);
        }
        else{
            fileUploaded = false;
            file_status.setText("No File Added");
        }
    }
}
