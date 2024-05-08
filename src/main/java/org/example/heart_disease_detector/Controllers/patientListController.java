package org.example.heart_disease_detector.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.heart_disease_detector.HeartDiseaseApplication;
import org.example.heart_disease_detector.Patient;

import java.io.*;
import java.net.URL;
import java.util.*;

public class patientListController implements Initializable {
    // Scene Variables
    @FXML
    Pane overlay_pane;
    @FXML
    Text page_count;
    @FXML
    TextField search_field;
    @FXML
    Text patient_not_found;
    @FXML
    Text patient_prompt_text;

    // Text Boxes
    @FXML ToggleButton box1;
    @FXML ToggleButton box2;
    @FXML ToggleButton box3;
    @FXML ToggleButton box4;
    @FXML ToggleButton box5;
    @FXML ToggleButton box6;
    @FXML ToggleButton box7;
    @FXML ToggleButton box8;
    @FXML ToggleButton box9;
    @FXML ToggleButton box10;
    @FXML ToggleButton box11;
    @FXML ToggleButton box12;
    @FXML ToggleButton box13;
    @FXML ToggleButton box14;
    @FXML ToggleButton box15;
    @FXML ToggleButton box16;
    @FXML ToggleButton box17;
    @FXML ToggleButton box18;
    @FXML ToggleButton box19;
    @FXML ToggleButton box20;
    @FXML ToggleButton box21;
    @FXML ToggleButton box22;
    @FXML ToggleButton box23;
    @FXML ToggleButton box24;
    @FXML ToggleButton box25;
    @FXML ToggleButton box26;

    // global data set by calling classes
    public static String file_location;
    public static int record_count;

    // Keep track of pages
    private int page_tracker = 1;
    private int box_tracker = 0;
    private int pageCount = 1;

    private ToggleButton  currently_selected;




    // HashMap to store patient information
    private HashMap<String, Patient> patientMap = new HashMap<>();


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
    protected void patient_data_btn(ActionEvent event) throws IOException {
        if(currently_selected != null) {
            setCurrentPatient();
            patient_prompt_text.setVisible(false);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("patientInfo.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                patientInfoController.patientInfoBackParent = "patientList";
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            patient_prompt_text.setVisible(true);
        }
    }

    @FXML
    protected void patient_results_btn(ActionEvent event) throws IOException {
        if(currently_selected != null) {
            setCurrentPatient();
            patient_prompt_text.setVisible(false);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("patientResults.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            patient_prompt_text.setVisible(true);
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
    protected void displayNames(){
        String line = "";
        String csvDelimiter = ",";
        int currentLine = 0;
        load_patients();

        try (BufferedReader br = new BufferedReader(new FileReader(file_location))) {
            while ((line = br.readLine()) != null && box_tracker < 26) {
                if(currentLine < page_tracker * 26 && currentLine >= ((page_tracker - 1)  * 26) ) {
                    // Split the line by the delimiter
                    String[] data = line.split(csvDelimiter);
                    String name = (data[0] + " " + data[1]);
                    addName(name);
                }
                // Process each column of the CSV data
                System.out.println(); // Move to the next line
                currentLine++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void load_patients(){
        String line = "";
        String csvDelimiter = ",";

        // Calculate Page Count
        pageCount = record_count / 26;
        if(record_count % 26 != 0){
            pageCount += 1;
        }
        // Set Page Count
        page_count.setText("Page " + page_tracker + " of " + pageCount);
        try (BufferedReader br = new BufferedReader(new FileReader(file_location))) {
            while ((line = br.readLine()) != null) {
                // Split the line by the delimiter
                String[] data = line.split(csvDelimiter);
                String name = (data[0] + " " + data[1]);
                Patient patient = new Patient(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8],
                        data[9], data[10], data[11], data[12], data[13], data[14]);
                patientMap.put(name, patient);

                // Process each column of the CSV data
                System.out.println(); // Move to the next line

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void addName(String fullName){
        List<ToggleButton> boxes = Arrays.asList(box1, box2, box3, box4, box5, box6, box7, box8, box9, box10
        , box11, box12, box13, box14, box15, box16, box17, box18, box19, box20, box21, box22, box23, box24, box25, box26);

        boxes.get(box_tracker).setDisable(false);
        boxes.get(box_tracker).setText(fullName);
        box_tracker++;
    }

    @FXML
    protected void clearTable(){
        System.out.println(box_tracker);
        List<ToggleButton> boxes = Arrays.asList(box1, box2, box3, box4, box5, box6, box7, box8, box9, box10
                , box11, box12, box13, box14, box15, box16, box17, box18, box19, box20, box21, box22, box23, box24, box25, box26);

        while(box_tracker > 0){
            boxes.get(box_tracker-1).setDisable(true);
            boxes.get(box_tracker-1).setText("");
            box_tracker--;
        }
    }

    @FXML
    protected void patientClicked(ActionEvent event) throws IOException {
        List<ToggleButton> boxes = Arrays.asList(box1, box2, box3, box4, box5, box6, box7, box8, box9, box10
                , box11, box12, box13, box14, box15, box16, box17, box18, box19, box20, box21, box22, box23, box24, box25, box26);

        ToggleButton button = (ToggleButton) event.getSource();
        // Loop through all ToggleButtons
        for (ToggleButton box : boxes) {
            // If the current button is the clicked button, set it to selected
            if (box == button) {
                box.setSelected(true);
                box.setStyle("-fx-border-color: blue");
                currently_selected = box;
            } else {
                // Otherwise, deselect it and reset its style
                box.setSelected(false);
                box.setStyle("-fx-border-color: transparent");
                box.setStyle("-fx-background-color: transparent");
            }
        }
    }

    @FXML
    protected void nextPage(){
        if(page_tracker < pageCount){
            page_tracker++;
            // Set Page Count
            page_count.setText("Page " + page_tracker + " of " + pageCount);
            clearTable();
            displayNames();

            // Deselect all toggle buttons
            deselectToggleButtons();
        }
    }

    @FXML
    protected void prevPage(){
        if(page_tracker > 1){
            page_tracker--;
            // Set Page Count
            page_count.setText("Page " + page_tracker + " of " + pageCount);
            clearTable();
            displayNames();

            // Deselect all toggle buttons
            deselectToggleButtons();
        }
    }

    // Method to deselect all toggle buttons
    private void deselectToggleButtons() {
        List<ToggleButton> boxes = Arrays.asList(box1, box2, box3, box4, box5, box6, box7, box8, box9, box10
                , box11, box12, box13, box14, box15, box16, box17, box18, box19, box20, box21, box22, box23, box24, box25, box26);

        for (ToggleButton box : boxes) {
            box.setSelected(false);
            box.setStyle("-fx-border-color: transparent");
            box.setStyle("-fx-background-color: transparent");
        }
        currently_selected = null;
    }

    @FXML
    protected void searchBar(){
        if (patientMap.containsKey(search_field.getText())){
            clearTable();
            addName(search_field.getText());
            deselectToggleButtons();
        }
        else{
            deselectToggleButtons();
            patient_not_found.setVisible(true);
        }

    }
    @FXML
    protected void clear_search(){
        patient_not_found.setVisible(false);
        clearTable();
        search_field.clear();
        displayNames();
    }

    @FXML
    public void setCurrentPatient(){
        if(currently_selected != null){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Shared_CSV/currentPatient.csv"))) {
                // Append data to the CSV file
                writer.write("");
                Patient patient = patientMap.get(currently_selected.getText());
                writer.write(patient.getAllPatientData());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayNames();
    }

}
