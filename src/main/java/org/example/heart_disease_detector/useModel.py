import csv
import joblib
import os
import numpy as np
import pandas as pd
import csv
import onnxruntime as rt  # Import ONNX runtime
from skl2onnx.common.data_types import FloatTensorType
from sklearn.linear_model import LogisticRegression


# Data Rubric:

# Age in years
# Sex           (1 = male; 0 = female)
# Chest pain type (chest pain type)
#       *-- Value 1: typical angina
#       *-- Value 2: atypical angina
#       *-- Value 3: non-anginal pain
#       *-- Value 4: asymptomatic
# BP            (Resting blood pressure (in mm Hg on admission to the hospital))
# Cholestoral in mg/dl
# FBS over 120  (fasting blood sugar > 120 mg/d1) (1=true; 0=false)
# EKG Results   (electrocardiographic results)
# Max HR        (maximum heart rate achieved)
# Exercise angina(induced angina (1 = yes; 0 = no))
# ST Depression (Depression induced by exercise relative to rest)
# ST slope      (Slope of the peak exercise ST segment)
# Fluro         (Number of major vessels (0-3) colored by fluoroscopy)
# Thallium      (3 = normal; 6 = fixed defect; 7 = reversible defect)
# target Presence or Absence

# Get the directory of the current Python script
script_dir = os.path.dirname(os.path.abspath(__file__))


# Load the ONNX model
onnx_model_path = "Heart_Disease_model.onnx"  # Path to your ONNX model
sess = rt.InferenceSession(onnx_model_path)


# Specify relative paths to the CSV files
csv_file_location = os.path.join(script_dir, "..", "Shared_CSV", "currentPatient.csv")
csv_results_location = os.path.join(script_dir, "..", "Shared_CSV", "patientResults.csv")


# Get Project CSV
df = pd.read_csv(csv_file_location, names=["FirstName", "LastName", "Age", "Sex", "Chest pain type", "BP", "Cholesterol", "FBS over 120", "EKG results", "Max HR", "Exercise angina",
                                           "ST depression", "Slope of ST", "Number of vessels fluro", "Thallium"])

# Save Names
firstName = df.FirstName
lastName = df.LastName

# Drop name fields
df_without_firstName = df.drop("FirstName", axis=1,)
fields = df_without_firstName.drop("LastName", axis=1)

#Convert DataTypes:

# Sex
fields.loc[fields["Sex"] == "Male", "Sex"] = 1
fields.loc[fields["Sex"] == "Female", "Sex"] = 0
fields["Sex"] = fields["Sex"].astype(int)

# Chest Pain
fields.loc[fields["Chest pain type"] == "Typical angina", "Chest pain type"] = 1
fields.loc[fields["Chest pain type"] == "Atypical angina", "Chest pain type"] = 2
fields.loc[fields["Chest pain type"] == "Non-anginal pain", "Chest pain type"] = 3
fields.loc[fields["Chest pain type"] == "Asymptomatic", "Chest pain type"] = 4
fields["Chest pain type"] = fields["Chest pain type"].astype(int)

# Fasting Blood Sugar > 120
fields.loc[fields["FBS over 120"] == "Yes", "FBS over 120"] = 1
fields.loc[fields["FBS over 120"] == "No", "FBS over 120"] = 0
fields["FBS over 120"] = fields["FBS over 120"].astype(int)

# Exercise_angina
fields.loc[fields["Exercise angina"] == "Yes", "Exercise angina"] = 1
fields.loc[fields["Exercise angina"] == "No", "Exercise angina"] = 0
fields["Exercise angina"] = fields["Exercise angina"].astype(int)

# ST Slope
fields.loc[fields["Slope of ST"] == "Upsloping", "Slope of ST"] = 1
fields.loc[fields["Slope of ST"] == "Flat", "Slope of ST"] = 2
fields.loc[fields["Slope of ST"] == "Downsloping", "Slope of ST"] = 3
fields["Slope of ST"] = fields["Slope of ST"].astype(int)

# Thallium
fields.loc[fields["Thallium"] == "Normal", "Thallium"] = 3
fields.loc[fields["Thallium"] == "Fixed Defect", "Thallium"] = 6
fields.loc[fields["Thallium"] == "Reversible Defect", "Thallium"] = 7
fields["Thallium"] = fields["Thallium"].astype(int)


X = fields.values.astype(np.float32)

# Run inference using the ONNX model
predictions_proba = sess.run(None, {'float_input': X})


# Get Prediction
predictions = predictions_proba[0][0]

# Extract prediction probabilities
probability = predictions_proba[1][0][predictions]



# Combine name, results, and probability
record = df.iloc[0]['FirstName'], df.iloc[0]['LastName'], predictions, f"{probability * 100:.0f}"

# Write first name, last name, prediction, probability, to csv
with open(csv_results_location, mode='w', newline='') as file:
    writer = csv.writer(file)
    # Write the record to the CSV file
    writer.writerow(record)