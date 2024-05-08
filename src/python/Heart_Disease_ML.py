# This .py was created in jupyter notebook and downloaded to this project for reference


# Data Rubric:

# Age in years
# Sex           (1 = male; 0 = female)
# CP chest pain type
#       *-- Value 1: typical angina
#       *-- Value 2: atypical angina
#       *-- Value 3: non-anginal pain
#       *-- Value 4: asymptomatic
# BP            (Resting blood pressure (in mm Hg on admission to the hospital))
# Cholestoral in mg/dl
# FBS           (fasting blood sugar > 120 mg/d1) (1=true; 0=false)
# EKG Results   (electrocardiographic results)
# Max HR        (maximum heart rate achieved)
# Exercise angina(induced angina (1 = yes; 0 = no))
# ST Depression (Depression induced by exercise relative to rest)
# ST slope      (Slope of the peak exercise ST segment)
# Fluro         (Number of major vessels (0-3) colored by flourosopy)
# Thallium      (3 = normal; 6 = fixed defect; 7 = reversable defect)
# target Presence or Absence



# Import libraries

# Regular EDA (exploratory data analysis
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# SciKit-Learn
from sklearn.linear_model import LogisticRegression
from sklearn.neighbors import KNeighborsClassifier
from sklearn.ensemble import RandomForestClassifier


# Evaluation
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.model_selection import RandomizedSearchCV, GridSearchCV
from sklearn.metrics import confusion_matrix, classification_report
from sklearn.metrics import precision_score, recall_score, f1_score
from sklearn.metrics import RocCurveDisplay


# In[71]:


# Load data into pandas dataframe
df = pd.read_csv("Heart_Disease_Prediction.csv")
df.shape


# ## Data exploration (exploratory data analysis or EDA)

# In[72]:


df["Heart Disease"].value_counts()


# In[73]:


df["Heart Disease"].value_counts().plot(kind = "bar", color = ["salmon", "lightblue"]);


# In[74]:


df.info();


# In[75]:


# Check for missing values
df.isna().sum()


# In[76]:


df.describe()


# In[77]:


df["Sex"].value_counts();


# In[78]:


# Compare Sex to target(Heart Disease)
pd.crosstab(df["Heart Disease"], df.Sex)

# Males seem to have relatively more Heart Disease presence than Females.
# Chance of heart disease if male:   ~55%
# Chance of heart disease if female: ~20%

# Average chance of heart disease:  ~37.5%


# In[79]:


# Create a plot of crosstabl
pd.crosstab(df["Heart Disease"], df.Sex).plot(kind="bar", figsize=(10,6), color=["salmon", "blue"])
plt.title("Heart Disease Frequency for Sex")
plt.ylabel("Amount")
plt.legend(["Female", "Male"]);
plt.xticks(rotation=0);


# In[80]:


df["Max HR"].value_counts()


# In[81]:


# Age vs. Max Heart Rate for Heart Disease
plt.figure(figsize=(10,6))
plt.scatter(df["Age"][df["Heart Disease"] == "Presence"], df["Max HR"][df["Heart Disease"] == "Presence"],
           c="salmon");

# Age vs. Max Heart Rate for Non Heart Disease
plt.scatter(df["Age"][df["Heart Disease"] == "Absence"], df["Max HR"][df["Heart Disease"] == "Absence"],
           c="lightblue");

# Add some info
plt.title("Heart Disease in function of Age and Max Heart Rate")
plt.legend(["Disease", "No Disease"])
plt.ylabel("Max Heart Rate")
plt.xlabel("Age")


# In[82]:


# Check Distribution of the age column with a histogram
df.Age.plot.hist();


# In[83]:


# Check if chest pain is related to heart disease

pd.crosstab(df["Chest pain type"], df["Heart Disease"])


# In[84]:


# Make crosstab visual
pd.crosstab(df["Chest pain type"], df["Heart Disease"]).plot(kind="bar", figsize=(10,6), color=(["salmon", "lightblue"]))

plt.title("Heart Disease Frequency Per Chest Pain Type")
plt.legend(["No Disease", "Disease"])
plt.ylabel("Amount")
plt.xlabel("Chest Pain Type")
plt.xticks(rotation=0);


# In[85]:


# Convert Target column to binary (Presence: 1, Absence: 0)
df["Heart Disease"][df["Heart Disease"] == "Presence"] = 1
df["Heart Disease"][df["Heart Disease"] == "Absence"] = 0


# In[86]:


df["Heart Disease"] = df["Heart Disease"].astype(int)


# In[87]:


# Make a correlation matrix
df.corr()


# In[88]:


corr_matrix = df.corr()
fig, ax = plt.subplots(figsize=(15,10))
ax = sns.heatmap(corr_matrix, annot=True, linewidth=0.5,
                 fmt=".2f", cmap="YlGnBu");


# ## Modeling

# In[89]:


df.head()


# In[90]:


# Split data into Features & Labels
X = df.drop("Heart Disease", axis = 1)  # Features
y = df["Heart Disease"]                 # Labels

np.random.seed(42)

# Create Train & Test Set
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2)


# In[ ]:





# In[42]:


# Machine learning models to test
# 1. Logistic Regression
# 2. K-Nearest Neighbor
# 3. Random Forest Classifier

# Put models in a dictionary
models = {"Logistic Regression": LogisticRegression(),
          "KNN": KNeighborsClassifier(),
          "Random Forest": RandomForestClassifier()}

# Create function to fit and score models
def fit_and_score(models, X_train, X_test, y_train, y_test):
    np.random.seed(42)

    model_scores = {}

    for name, model in models.items():
        # Fit data to the model
        model.fit(X_train, y_train)
        #Evaluate the model and append its score to model_scores
        model_scores[name] = model.score(X_test, y_test)
        
    return model_scores


# In[45]:


# Fit & Score Models
model_scores = fit_and_score(models=models, 
                             X_train=X_train, 
                             X_test=X_test, 
                             y_train=y_train, 
                             y_test=y_test)
model_scores


# In[46]:


# Model comparisons
model_compare = pd.DataFrame(model_scores, index=["accuracy"])
model_compare.T.plot.bar();


# In[47]:


'''
Evaluation/Tuning:
1. Hyperparameter tuning
2. Feature Importance
3. Confusion Matrix
4. Cross-validation
5. precision
6. Recall
7. F1 Score
8. Classification Report
9. ROC Curve
10. Area under the curve (AUC)
''';


# In[48]:


# HyperParameter Tuning

# Tune KNN
train_scores = []
test_scores = []

# Create list of different values for n_neighbors
neighbors = range(1, 21)

# Setup KNN instance
knn = KNeighborsClassifier()

# Looop through different n_neighbors
for i in neighbors:
    knn.set_params(n_neighbors = i)

    # Fit the algorithm
    knn.fit(X_train, y_train)

    # Update training scores list
    train_scores.append(knn.score(X_train, y_train))

    # Update test score list
    test_scores.append(knn.score(X_test, y_test))

    


# In[49]:


plt.plot(neighbors, train_scores, label="Train Score")

plt.plot(neighbors, test_scores, label="Test Score");
plt.title("KNN Model")
plt.xticks(np.arange(1,21))
plt.xlabel("Number of neighbors")
plt.ylabel("Model Score")
plt.legend();


# In[54]:


# HyperParameter tuning with RandomizedSearchCV

# Hyperparameter grid for LogisticRegression
log_reg_grid = {"C": np.logspace(-4, 4, 20),
                "solver": ['liblinear']}

# Create a hyperparameter grid for RandomForestClassifier
rf_grid = {"n_estimators": np.arange(10, 1000, 50),
           "max_depth": [None, 3, 5, 10],
           "min_samples_split": np.arange(2, 20, 2),
           "min_samples_leaf": np.arange(1, 20, 2)}


# In[55]:


# Tune LogisticRegression
np.random.seed(42)

# Setup random hyperparameter search for LogisticRegression
rs_log_reg = RandomizedSearchCV(LogisticRegression(), 
                                param_distributions=log_reg_grid,
                                cv=5,
                                n_iter=20,
                                verbose=True)

# Fit random hyperparameter search model for LogisticRegression
rs_log_reg.fit(X_train, y_train)


# In[56]:


rs_log_reg.best_params_


# In[57]:


rs_log_reg.score(X_test, y_test)


# In[59]:


# Tune RandomForestClassifier
np.random.seed(42)

# Setup random hyperparameter search for LogisticRegression
rs_rf = RandomizedSearchCV(RandomForestClassifier(), 
                           param_distributions=rf_grid,
                           cv=5,
                           n_iter=20,
                           verbose=True)

# Fit random hpyerparameter search model for RandomForestClassifier
rs_rf.fit(X_train, y_train)


# In[60]:


# Find best hyperparameter for RandomForest
rs_rf.best_params_


# In[61]:


# Score for the RandomForest
rs_rf.score(X_test, y_test)


# In[62]:


# Hyperparameter Tuning with GridSearchCV

# Hyperparameter grid for LogisticRegression
log_reg_grid = {"C": np.logspace(-4, 4, 30),
                "solver": ['liblinear']}

# Setup grid hyperparameter search for LogisticRegression
gs_log_reg = GridSearchCV(LogisticRegression(),
                          param_grid=log_reg_grid,
                          cv=5,
                          verbose=True)

# Fit grid hyperparameter search model
gs_log_reg.fit(X_train, y_train)


# In[63]:


# Find best hyperparameter for Logistic Regression
gs_log_reg.best_params_


# In[64]:


# Score for LogisticRegression
gs_log_reg.score(X_test, y_test)


# In[65]:


# Evaluating tuned machine learning classifier

# Make predictions
y_preds = gs_log_reg.predict(X_test)


# In[66]:


y_preds


# In[68]:


np.array(y_test)


# In[92]:


# Plot ROC curve and calculate AUC metric
RocCurveDisplay.from_estimator(gs_log_reg, X_test, y_test)


# In[93]:


# Confusion matrix
confusion_matrix(y_test, y_preds)


# In[98]:


sns.set(font_scale=1.5)
def plot_conf_mat(y_test, y_preds):
    fig, ax = plt.subplots(figsize=(3,3))
    ax = sns.heatmap(confusion_matrix(y_test, y_preds),
                        annot=True,
                        cbar=False)
    plt.xlabel("Predicted label")
    plt.ylabel("True label")
    


# In[99]:


plot_conf_mat(y_test, y_preds)


# In[101]:


# Classification Report
print(classification_report(y_test, y_preds))


# In[117]:


# Evaluate using cross-validation 
clf = LogisticRegression(C=0.20433597178569418, solver='liblinear')

# CV Accuracy
cv_acc = cross_val_score(clf, X, y, cv=5, scoring='accuracy')
cv_acc = np.mean(cv_acc)


# In[118]:


# CV Precision
cv_precision = cross_val_score(clf, X, y, cv=5, scoring='precision')
cv_precision = np.mean(cv_precision)


# In[119]:


# CV Recall
cv_recall = cross_val_score(clf, X, y, cv=5, scoring='recall')
cv_recall = np.mean(cv_recall)


# In[120]:


# CV F1
cv_f1 = cross_val_score(clf, X, y, cv=5, scoring='f1')
cv_f1 = np.mean(cv_f1)


# In[123]:


# Visualize cross-validated metrics
cv_metrics = pd.DataFrame({"Accuracy": cv_acc,
                           "Precision": cv_precision,
                           "Recall": cv_recall,
                           "F1": cv_f1},
                         index=[0])
cv_metrics.T.plot.bar(title="Cross-validated classification metrics", legend=False);


# In[124]:


# Features Importance
df.head()


# In[126]:


clf.fit(X_train, y_train);


# In[133]:


# Check coef_
clf.coef_


# In[136]:


# Match coefs features to columns
feature_dict = dict(zip(df.columns, list(clf.coef_[0])))
feature_dict


# In[138]:


feature_df = pd.DataFrame(feature_dict, index=[0])
feature_df.T.plot.bar(title="Feature Importance", legend=False);


# In[139]:


import joblib

# save
joblib.dump(clf, "Heart_Disease_model.pkl") 


# In[ ]:




