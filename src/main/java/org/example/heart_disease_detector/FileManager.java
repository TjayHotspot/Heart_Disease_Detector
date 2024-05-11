package org.example.heart_disease_detector;

import java.io.File;

// Singleton class to hold the reference to the file
public class FileManager {
    private static FileManager instance;
    private File patientData;
    private File currentPatient;
    private File patientResults;

    private FileManager() {
        // Initialize the file object here
        patientData = new File("patientData.csv");
        currentPatient = new File("currentPatient.csv");
        patientResults = new File("patientResults.csv");
    }

    // Return instance of FileManager
    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

// Accessors
    public File get_patientData() {
        return patientData;
    }
    public File get_currentPatient() {
        return currentPatient;
    }
    public File get_patientResults() {
        return patientResults;
    }
}

