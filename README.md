# Heart Disease Prediction System

## Overview
This project aims to develop a user-friendly interface using JavaFX for predicting the presence of heart disease in patients. The underlying machine learning model, implemented in Python, utilizes an ONNX format model trained on medical data. Users can input patient information individually or in bulk via CSV files and obtain predictions regarding the presence of heart disease.

## Features
- User-friendly graphical interface built using JavaFX and FXML.
- Integration with an ONNX machine learning model for heart disease prediction.
- Support for both individual patient prediction and batch prediction from CSV files.
- Clear visualization of prediction results for easy interpretation.
- Detailed error handling to ensure robustness and reliability.

## Getting Started
1. **Clone the Repository:** 
    ```bash
    git clone https://github.com/TjayHotspot/Heart_Disease_Detector.git
    ```
2. **Setup Environment:**
    - Ensure Java Development Kit (JDK) 21 or higher is installed.
    - Install Maven if not already installed.
    - Python 3 with required libraries for machine learning model (refer to model documentation).
3. **Run the Application:**
    ```bash
    mvn javafx:run
    ```
4. **Input Patient Data:**
    - Enter patient information manually or import from a CSV file.
    - Click the "View Patient Results"/"View Results" button to obtain predictions.

## Project Structure
- **`src/main/java/controllers`:** Contains JavaFX controller classes responsible for UI logic and interaction.
- **`src/main/resources`:** Includes FXML files for defining the UI layout and other non-Java resources. Also houses the Python script for invoking the ONNX machine learning model.

## Dependencies
- JavaFX 62.0
- Maven
- Python 3
- ONNX Runtime (Python library)

## Contributing
Contributions to this project are welcome. For major changes, please open an issue first to discuss the proposed changes.


## Acknowledgements
- Scene Builder for FXML GUI design.
- ONNX for the interoperable AI model format.


## Contact
For questions or feedback regarding this project, please contact:
- Thomas Balestrieri - thomasjbalest@gmail.com
