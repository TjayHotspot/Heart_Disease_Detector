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
import org.example.heart_disease_detector.HeartDiseaseApplication;

import java.io.*;
import java.nio.Buffer;

public class multiplePatientsController {
    @FXML
    Pane overlay_pane;
    @FXML
    Label file_status;
    @FXML
    Button uploadCSV_btn;
    @FXML
    Rectangle prompt_upload_box;
    @FXML
    Text prompt_upload;
    @FXML Text csv_fail;

    boolean fileUploaded = false;
    int recordCount = 0;

    private String defaultCSV = "src/main/resources/org/example/heart_disease_detector/patientData.csv";

    @FXML
    protected void home_btn(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
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

    @FXML
    protected void rubric_btn(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("evaluationRubric.fxml"));
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

    @FXML
    protected void patientList_btn(ActionEvent event) throws IOException {
        if (fileUploaded) {
            try {
                patientListController.file_location = defaultCSV;
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
                patientListController.record_count = 0;
                recordCount = 0;
            }
        }
        else{
            prompt_upload.setVisible(true);
            prompt_upload_box.setVisible(true);
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

    // Method to handle the file upload action
    public void uploadFile_btn() {
        csv_fail.setVisible(false);
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
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(defaultCSV));) {
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
            // You can store the content of the CSV file in your project's CSV file here
        } catch (IOException e) {
            e.printStackTrace();
        }
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
